package com.byul.web;

import com.byul.config.jwt.JwtToken;
import com.byul.domain.PlatformType;
import com.byul.service.OAuthService;
import com.byul.web.dto.request.LoginRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService oAuthService;

    /**
     * 소셜 로그인 이전
     */
    @GetMapping("/api/v1/login/oauth2/code/{platform}")
    public String socialLoginRedirect(
            @PathVariable(name = "platform") String platform
    ) throws IOException {
        PlatformType platformType = PlatformType.valueOf(platform.toUpperCase());

        return oAuthService.requestRedirectURL(platformType);
    }

    /**
     * 소셜 로그인 이후
     * Social Login API Server 요청에 의한 callback 을 처리한다.
     */
    @GetMapping("/api/v1/login/oauth2/code/{platform}/callback")
    public JwtToken callback(
            @PathVariable(name = "platform") String platform,
            @RequestParam(name = "code") String code
    ) throws JsonProcessingException {
        PlatformType platformType = PlatformType.valueOf(platform.toUpperCase());

        return oAuthService.oAuthLogin(platformType, code);
    }

    @PostMapping("/api/v1/login/reissue")
    public JwtToken reissue(
            @RequestBody LoginRequestDto.Reissue reissue
    ) {
        return oAuthService.reissue(reissue);
    }

    @PostMapping("/api/v1/logout")
    public String logout(
            @RequestBody LoginRequestDto.Logout logout
    ) {
        return oAuthService.logout(logout);
    }
}
