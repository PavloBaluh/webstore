package com.jarviz.webstore.Configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jarviz.webstore.Models.AccountCredentials;
import com.jarviz.webstore.Models.User;
import com.jarviz.webstore.Service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public LoginFilter(String url, AuthenticationManager authManager,UserService userService,PasswordEncoder passwordEncoder) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
         }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws  IOException {
        AccountCredentials creds = new ObjectMapper().readValue(httpServletRequest.getInputStream(), AccountCredentials.class);
        User user = userService.getByUsernameOrEmail(creds.getUsernameEmail());
        if (user != null && passwordEncoder.matches(creds.getPassword(), user.getPassword()) && user.isEnabled()) {
            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), creds.getPassword(), user.getAuthorities()));
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res, FilterChain chain,
            Authentication auth) {
        String jwtoken = Jwts.builder()
                .setSubject(auth.getName())
                .signWith(SignatureAlgorithm.HS512, "candies".getBytes())
                .compact();
        res.addHeader("Authorization", "Bearer " + jwtoken);
    }
}
