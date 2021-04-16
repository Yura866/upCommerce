package com.bcn.startupers.upcommerce.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bcn.startupers.upcommerce.security.UserDetailsServiceImpl;

public class JwtTokenFilter extends OncePerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(JwtTokenFilter.class);
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        try {
            String token = getToken(req);       
            if (token !=null && jwtProvider.validateToken(token)) {				
			String email = jwtProvider.getEmailFromToken(token);
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);            
        }
        }catch (Exception e){
        	LOG.error("Fail in the doFilterInternal method  :"+ e);
        }
        chain.doFilter(req, res);
    }

    private String getToken(HttpServletRequest req){
        String authReq = req.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(authReq) && authReq.startsWith("Bearer ")) {
            return authReq.substring(7, authReq.length());
        }
        return null;
    }
	
}
