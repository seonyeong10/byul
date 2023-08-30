package com.byul.web.dto.auth;

import com.byul.domain.Member;

public interface OAuthUser {
    /**
     * 신규 등록 회원을 만든다.
     */
    Member toMember();
}
