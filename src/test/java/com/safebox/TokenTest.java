package com.safebox;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.safebox.securing.JwtTokenUtil;


class TokenTest {
	
	
	@Test
	public void testGenerateTokenAndGetUser() {
		
		JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
		UserDetails daniUser = new User("daniel", "", new ArrayList<>());
		String token = jwtTokenUtil.generateToken(daniUser);		
		
		String nombre = jwtTokenUtil.getUsernameFromToken(token);		
		assertEquals(nombre, "daniel");
		
	}
		
}
