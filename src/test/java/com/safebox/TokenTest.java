package com.safebox;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.safebox.securing.JwtTokenUtil;


class TokenTest {
	
	
	@Test
	public void testGenerateTokenAndGetUser() {
		
		String token = JwtTokenUtil.getInstance().generateToken("daniel");		
		
		String nombre = JwtTokenUtil.getInstance().getUsernameFromToken(token);		
		assertEquals(nombre, "daniel");
		
	}
		
}
