package com.safeish.securing;

public final class SecurityConstants {

	public static final String AUTH_LOGIN_URL = "/api/login";

	// Signing key for HS512 algorithm
	public static final String JWT_SECRET = "2rVT6wFtObLegSxNYCCLpy/gRmkcGQUkXp2s5v2dvD8uChJAOmL1pG+KbPeSztZd";
	public static final String ENCRYPTOR_PASSWORD = "qBLBpzggxpOQPnoc";


	// JWT token defaults
	public static final String TOKEN_HEADER = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String TOKEN_TYPE = "JWT";
	public static final String TOKEN_ISSUER = "safe-box";
	public static final String TOKEN_AUDIENCE = "safe-box";

	
	private SecurityConstants() {
		throw new IllegalStateException("Cannot create instance of static util class");
	}
}
