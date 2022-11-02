package com.ll.finalProject.week2.service;

import com.ll.finalProject.week2.domain.Member;
import com.ll.finalProject.week2.enumType.MemberRole;
import com.ll.finalProject.week2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberSecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberName) throws UsernameNotFoundException {
        return memberRepository.findByUserName(memberName)
                .orElseThrow(() -> new UsernameNotFoundException((memberName)));
    }
}
