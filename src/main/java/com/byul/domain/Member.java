package com.byul.domain;

import com.byul.domain.converter.MembershipConverter;
import com.byul.domain.staff.Staff;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String name;

    private String nickname;

    private LocalDate birth;

    private String picture;

    @Convert(converter = MembershipConverter.class)
    private Membership membership = Membership.WELCOME;

    @OneToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @Enumerated(value = EnumType.STRING)
    private PlatformType platformType;

    @Builder
    public Member(Long id, String email, String name, String nickname, LocalDate birth, String picture, PlatformType platformType) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
        this.picture = picture;
        this.platformType = platformType;
    }

    public void addMembership(Membership membership) {
        this.membership = membership;
    }


    //==UserDetails 오버라이딩 메소드==//
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of(Role.USER.getValue())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
