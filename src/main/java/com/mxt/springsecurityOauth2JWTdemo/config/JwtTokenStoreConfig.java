package com.mxt.springsecurityOauth2JWTdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


/**
 * @author Simple
 * @date 2021/2/20 16:45
 */

@Configuration
public class JwtTokenStoreConfig {


    @Bean
    public TokenStore jwtTokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter jwtAccessTokenConverter=new JwtAccessTokenConverter();
        //设置jwt密钥
        jwtAccessTokenConverter.setSigningKey("test_key");
        return  jwtAccessTokenConverter;
    }

    @Bean
    public JwtTokenEnhancer jwtTokenEnhancer(){
        return new JwtTokenEnhancer() ;
    }
}
