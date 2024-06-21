package com.hugo.springapiapp.external.oauth.service;

import com.hugo.springapiapp.external.oauth.model.OAuthAttributes;
import org.springframework.stereotype.Service;

public interface SocialLoginApiService {

    OAuthAttributes getUserInfo(String accessToken);

}
