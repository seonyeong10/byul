package com.byul.web.dto.response.pay.kakao;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class KakaoPrePayResponseDto {
    private String tid; //결제 고유 번호(20자)

    private String next_redirect_app_url; //모바일 앱

    private String next_redirect_mobile_url; //모바일 웹

    private String next_redirect_pc_url; //PC 웹

    private String android_app_scheme; //안드로이드 앱 스킴

    private String ios_app_scheme; //IOS 앱 스킴

    private String created_at; //결제 준비 요청 시간
}
