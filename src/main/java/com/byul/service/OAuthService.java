package com.byul.service;

import com.byul.auth.GoogleOAuth;
import com.byul.config.jwt.JwtToken;
import com.byul.config.jwt.JwtTokenProvider;
import com.byul.domain.Member;
import com.byul.domain.PlatformType;
import com.byul.domain.repository.MemberRepository;
import com.byul.exception.NotPermittedTokenException;
import com.byul.web.dto.auth.GoogleOAuthToken;
import com.byul.web.dto.auth.GoogleUser;
import com.byul.web.dto.request.LoginRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService {

    private final GoogleOAuth googleOAuth;

    private final HttpServletResponse response;

    private final MemberRepository memberRepository;

    private final BCryptPasswordEncoder encoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final JwtTokenProvider jwtTokenProvider;

    private final RedisTemplate redisTemplate;

    /**
     * 각 소셜 로그인 페이지로 이동한다.
     */
    public String requestRedirectURL(PlatformType platformType) throws IOException {
        String redirectURL;

        switch (platformType) {
            case GOOGLE -> {
                redirectURL = googleOAuth.getOauthRedirectURL();
                break;
            }
            default -> throw new IllegalArgumentException("알 수 없는 로그인 형식입니다.");
        }

//        response.sendRedirect(redirectURL);

        return redirectURL;
    }

    /**
     * 사용자 정보를 받아 토큰을 발급한다. (로그인)
     */
    public JwtToken oAuthLogin(PlatformType platformType, String code) throws JsonProcessingException {
        switch (platformType) {
            case GOOGLE -> {
                //일회성 코드를 보내 액세스 토큰이 담긴 객체를 받는다.
                ResponseEntity<String> accessTokenResponse = googleOAuth.requestAccessToken(code);

                //JSON 형식의 응답을 GoogleOAuthToken 객체에 담는다.
                GoogleOAuthToken googleOAuthToken = googleOAuth.getAccessToken(accessTokenResponse);

                //액세스 토큰을 보내 사용자 정보를 받는다.
                ResponseEntity<String> userInfoResponse = googleOAuth.requestUserInfo(googleOAuthToken);

                //다시 JSON 형식의 응답을 GoogleUser 에 담는다.
                GoogleUser googleUser = googleOAuth.getUserInfo(userInfoResponse);

                log.info("googleUser >>> " + googleUser);

                //==DB 대조==//
                //Member 에 해당 이메일을 가진 유저가 존재하는지 확인한다.
                //존재하지 않으면 DB에 저장한다.
                Member member = memberRepository.findByEmail(googleUser.getEmail())
                        .orElse(memberRepository.save(googleUser.toMember()));

                log.info("member >>> " + member);

                //JWT 토큰을 발급한다.
                Authentication authentication = jwtTokenProvider.requestAuthentication(member);
                JwtToken token = jwtTokenProvider.generateToken(authentication);

                log.info("token >>> " + token);

                //RefreshToken 을 Redis 에 저장한다. (expirationTime 설정을 통해 자동으로 삭제)
//                redisTemplate.opsForValue()
//                        .set("RT:" + authentication.getName(), token.getRefreshToken(), token.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

                return token;
            }
            default -> throw new IllegalArgumentException("알 수 없는 소셜 로그인 형식입니다.");
        }
    }

    /**
     * 토큰을 재발급 한다.
     */
    public JwtToken reissue(LoginRequestDto.Reissue reissue) {
        if(!jwtTokenProvider.validateToken(reissue.getAccessToken())) {
            throw new NotPermittedTokenException("유효하지 않은 토큰입니다.");
        }

        //회원정보 조회
        Long id = jwtTokenProvider.getUserId(reissue.getAccessToken());
        Member member = memberRepository.findById(id).stream()
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(String.format("회원을 찾을 수 없습니다. member_id = [%s]", id)));

        Authentication authentication = jwtTokenProvider.requestAuthentication(member);
        JwtToken token = jwtTokenProvider.generateToken(authentication);

        //redis 저장
//        redisTemplate.opsForValue()
//                .set("RT:" + authentication.getName(), token.getRefreshToken(), token.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return token;
    }

    /**
     * 로그아웃 한다.
     */
    public String logout(LoginRequestDto.Logout logout) {
        String message = "로그아웃 되었습니다.";

        if(!jwtTokenProvider.validateToken(logout.getAccessToken())) {
            throw new NotPermittedTokenException("유효하지 않은 토큰입니다.");
        }

        //회원정보 조회
        Long id = jwtTokenProvider.getUserId(logout.getAccessToken());
        Member member = memberRepository.findById(id).stream()
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(String.format("회원을 찾을 수 없습니다. member_id = [%s]", id)));

        Authentication authentication = jwtTokenProvider.requestAuthentication(member);
        JwtToken token = jwtTokenProvider.generateToken(authentication);

        //Redis 에 저장된 refresh 토큰 삭제
//        if(redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
//            redisTemplate.delete("RT:" + authentication.getName());
//        }

        Long expiration = jwtTokenProvider.getExpiration(logout.getAccessToken());
//        redisTemplate.opsForValue()
//                .set(logout.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);

        return message;
    }
}
