package com.safeish.safebox.service;

import org.springframework.http.ResponseEntity;

import com.safeish.safebox.jpa.entity.Safebox;

public interface ISafeboxService {
	public ResponseEntity<String> safeboxRegister( Safebox safebox) throws Exception;
}
