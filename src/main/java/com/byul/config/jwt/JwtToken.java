package com.byul.config.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor
@ToString
public class JwtToken {
    private String grantType;

    private String accessToken;

    private String refreshToken;

    private Long refreshTokenExpirationTime;
}
