package com.mxt.springsecurityOauth2JWTdemo.controller;



import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * @author Simple
 * @date 2021/2/20 13:30
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/getCurrentUser")
    public Object getCurrentUser(Authentication authentication){
        return authentication.getPrincipal();
    }
    @RequestMapping("/getCurrentUser2")
    public Object getCurrentUser2(Authentication authentication, HttpServletRequest request){
        String header=request.getHeader("Authorization");
        String token=header.substring(header.lastIndexOf("bearer")+7);
        return Jwts.parser()
                .setSigningKey("test_key".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }
}
