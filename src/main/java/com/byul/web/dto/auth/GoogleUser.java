package com.byul.web.dto.auth;

import com.byul.domain.Member;
import com.byul.domain.PlatformType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@AllArgsConstructor
@ToString // 디버깅용
public class GoogleUser implements OAuthUser {

    private String id;

    private String email;

    @JsonProperty("verified_email")
    private String verifiedEmail;

    private String name;

    @JsonProperty("given_name")
    private String givenName;

    @JsonProperty("family_name")
    private String familyName;

    private String picture;

    private String locale;

    @Override
    public Member toMember() {
        return Member.builder()
                .email(email)
                .name(name)
                .picture(picture)
                .platformType(PlatformType.GOOGLE)
                .build();
    }
}
