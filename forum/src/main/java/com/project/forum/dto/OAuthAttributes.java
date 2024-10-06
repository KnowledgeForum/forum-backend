package com.project.forum.dto;

import com.project.forum.entity.AppUser;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

public interface  OAuthAttributes {

    String getProvider();
    String getProviderId();
    String getEmail();
    String getName();
}
