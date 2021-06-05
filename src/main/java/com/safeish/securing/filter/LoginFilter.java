package com.safeish.securing.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safeish.model.JwtRequest;
import com.safeish.securing.JwtTokenUtil;
import com.safeish.securing.SecurityConstants;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {
	
	public LoginFilter(String requiresAuthenticationRequestMatcher, AuthenticationManager manager ) {
		super(requiresAuthenticationRequestMatcher);
		
		setAuthenticationManager(manager);
		
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		
		JwtRequest jwtRequest = new ObjectMapper().readValue(request.getInputStream(), JwtRequest.class);
		UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),jwtRequest.getPassword());
		
		return getAuthenticationManager().authenticate(user);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		
		String jwt = JwtTokenUtil.getInstance().generateToken(authResult.getName());		
		response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + jwt );

		
	}
		
}
