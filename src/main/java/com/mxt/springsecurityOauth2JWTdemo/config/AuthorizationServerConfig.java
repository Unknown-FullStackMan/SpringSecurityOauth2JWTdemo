package com.mxt.springsecurityOauth2JWTdemo.config;


import com.mxt.springsecurityOauth2JWTdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;
    //密码模式用到的Beanb
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    @Qualifier("jwtTokenStore")
    private TokenStore tokenStore;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    private JwtTokenEnhancer jwtTokenEnhancer;


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                //客户端ID
                .withClient("client")
                //密钥
                .secret(passwordEncoder.encode("112233"))
                //重定向地址
                .redirectUris("https://www.baidu.com/")
                //授权范围
                .scopes("all")
                /**
                 * 授权类型
                 * authorization_code:授权码模式
                 * password:密码模式
                 * refresh_token:刷新令牌
                 */
                //设置token失效时间
                .accessTokenValiditySeconds(60)
                //设置刷新令牌失效时间
                .refreshTokenValiditySeconds(6000)
                .authorizedGrantTypes("authorization_code","password","refresh_token");
    }


    //这是密码模式需要的方法 上面是授权码模式
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //设置jwt增强内容
        TokenEnhancerChain chain=new TokenEnhancerChain();
        List<TokenEnhancer> delegates=new ArrayList<>();
        delegates.add(jwtTokenEnhancer);
        delegates.add(jwtAccessTokenConverter);
        chain.setTokenEnhancers(delegates);
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userService)
                //access转成Jwttoken
                .tokenStore(tokenStore)
                .accessTokenConverter(jwtAccessTokenConverter)
                .tokenEnhancer(chain);
    }
}
