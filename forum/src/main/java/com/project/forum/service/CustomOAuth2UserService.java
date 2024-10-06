package com.project.forum.service;

import com.project.forum.dto.CustomOAuth2UserDetails;
import com.project.forum.dto.GoogleUserDetails;
import com.project.forum.dto.OAuthAttributes;
import com.project.forum.entity.AppUser;
import com.project.forum.entity.Role;
import com.project.forum.repository.AppUserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final AppUserRepository appUserRepository;
    private final HttpSession session;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("getTokenValue : {}", userRequest.getAccessToken().getTokenValue());
        log.info("getClientRegistration : {}", userRequest.getClientRegistration());

        // 세션에 AccessToken 값 저장
        session.setAttribute("accessToken", userRequest.getAccessToken().getTokenValue());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("getAttributes : {}", oAuth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getRegistrationId();

        OAuthAttributes oAuthAttributes = null;

        if (provider.equals("google")) {
            log.info("구글 로그인");
            oAuthAttributes = new GoogleUserDetails(oAuth2User.getAttributes());
        }

        String providerId = oAuthAttributes.getProviderId();
        String email = oAuthAttributes.getEmail();
        String loginId = provider + "_" + providerId;
        String name = oAuthAttributes.getName();

        Optional<AppUser> findUser = appUserRepository.findByEmail(email);
        AppUser appUser;

        if(findUser.isEmpty()){ // 해당 유저가 없으면
            appUser = AppUser.builder()
                    .userPw(loginId)
                    .nickname(name)
                    .provider(provider) //google
                    .providerId(providerId) //연동 seq 같은
                    .email(email)
                    .role(Role.USER)
                    .build();
            appUserRepository.save(appUser);
        } else {
            appUser = findUser.get(); // 해당 유저가 있으면
        }
        return new CustomOAuth2UserDetails(appUser, oAuth2User.getAttributes());
    }
}
