package com.safeish.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safeish.safebox.jpa.entity.Safebox;
import com.safeish.safebox.service.ISafeboxService;

@RestController
@RequestMapping("")
public class SafeboxRegisterController {

	@Autowired
	ISafeboxService safeboxService;

	@PostMapping("/safebox")
	ResponseEntity<String> addSafebox(@RequestBody Safebox safebox) throws Exception {

		try {
			return safeboxService.safeboxRegister(safebox);
		} catch (Exception e) {			
			return new ResponseEntity<String>("Unexpected API error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}


}
