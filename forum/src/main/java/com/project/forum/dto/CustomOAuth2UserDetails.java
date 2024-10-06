package com.project.forum.dto;

import com.project.forum.entity.AppUser;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class CustomOAuth2UserDetails implements UserDetails, OAuth2User {

    private final AppUser appUser;
    private Map<String, Object> attributes; // 구글에서 받아온 정보들

    public CustomOAuth2UserDetails(AppUser appUser, Map<String, Object> attributes) {
        this.appUser = appUser;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return String.valueOf(appUser.getRole());
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return appUser.getUserPw();
    }

    @Override
    public String getUsername() {
        return appUser.getNickname();
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
