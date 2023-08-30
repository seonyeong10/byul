package com.byul.config.jwt;

import com.byul.domain.Member;
import com.byul.exception.NotPermittedTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "auth";

    private static final String BEARER_TYPE = "Bearer";

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 30 * 60 * 1000L; //30분

    private static final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L; //7일

    private final Key key;

    public JwtTokenProvider (@Value("${jwt.secret}") String secretKey) {
        byte[] secretByteKey = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(secretByteKey);
    }

    /**
     * 유저 정보를 가지고 AccessToken, RefreshToken 을 만든다.
     */
    public JwtToken generateToken(Authentication authentication) {
        //권한을 가져온다.
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        String realname = ((CustomUser) authentication.getPrincipal()).getRealname();
        long now = new Date().getTime();

        //access token 을 만든다.
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .claim("name", realname)
                .setExpiration(new Date(now + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        //refresh token 을 만든다.
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return JwtToken.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .refreshTokenExpirationTime(REFRESH_TOKEN_EXPIRE_TIME)
                .build();
    }

    /**
     * accessToken 으로 사용자 권한을 조회한다.
     */
    public Authentication getAuthentication(String accessToken) {
        //토큰을 복호화 한다.
        Claims claims = parseClaims(accessToken);

        if(claims.get("auth") == null) {
            throw new NotPermittedTokenException("권한 정보가 없는 토큰입니다.");
        }

        //클레임에서 권한 정보를 가져온다.
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        //UserDetails 객체를 만들어 Authentication 을 만든다.
        UserDetails principal = new CustomUser(claims.getSubject(), authorities, claims.get("name").toString());

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    /**
     * accessToken 을 복호화 한다.
     */
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            log.info("유효기간이 만료된 토큰입니다.", e);
            return e.getClaims();
        }
    }

    /**
     * accessToken 의 남은 유효시간을 조회한다.
     */
    public Long getExpiration(String accessToken) {
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(accessToken)
                .getBody()
                .getExpiration();
        Long now = new Date().getTime();

        return (expiration.getTime() - now);
    }

    /**
     * Member Pk 를 조회한다.
     */
    public Long getUserId(String accessToken) {
        return Long.parseLong(parseClaims(accessToken).getSubject());
    }

    /**
     * 토큰을 검증한다.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty", e);
        } catch (Exception e) {
            log.info("Unexpected Exception", e);
        }
        return false;
    }

    public Authentication requestAuthentication(Member member) {
        String name = member.getNickname() == null ? member.getName() : member.getNickname();
//        UserDetails principal = new CustomUser(member.getEmail(), member.getAuthorities(), name);
        UserDetails principal = new CustomUser(member.getId() + "", member.getAuthorities(), name);

        return new UsernamePasswordAuthenticationToken(principal, "", member.getAuthorities());
    }
}
