package com.adbt.adbtproject.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }


    //@Value("${jwt.secret}")
    private String secret = "secretkey";

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader("Authorization");

        if(header == null || !header.startsWith("Bearer ")){
            chain.doFilter(req,res);
            return;
        }
        String token = header.substring(7);
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        if(Jwts.parser().setSigningKey(secret).isSigned(token)){
            req.setAttribute("claims",claims);

            chain.doFilter(req,res);
        }
        else{
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");
        }
    }
}