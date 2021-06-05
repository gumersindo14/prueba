package com.safeish.safebox.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.safeish.safebox.jpa.entity.Safebox;

public interface ISafeboxService {
	public ResponseEntity<String> safeboxRegister(@Valid Safebox safebox) throws Exception;
}
