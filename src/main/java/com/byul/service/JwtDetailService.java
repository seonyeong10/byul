package com.byul.service;

import com.byul.config.jwt.CustomUser;
import com.byul.domain.Member;
import com.byul.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByEmail(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("회원을 찾을 수 없습니다. email = [%s]", username)));
    }

    /**
     * 회원이 존재하면 UserDetails 객체로 만든다.
     */
    private UserDetails createUserDetails(Member member) {
        String name = member.getNickname() == null ? member.getName() : member.getNickname();
        return new CustomUser(member.getEmail(), member.getAuthorities(), name);
    }
}
