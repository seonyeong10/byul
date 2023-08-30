package com.byul.config.jwt;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter @Setter
@ToString
public class CustomUser extends User {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private String realname;

    public CustomUser(String username, Collection<? extends GrantedAuthority> authorities, String realname) {
        super(username, "", authorities);
        this.realname = realname;
    }
}
