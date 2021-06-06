package com.safeish.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safeish.item.entity.IItemService;

@RestController
@RequestMapping("")
public class ItemController {
	
	@Autowired
	IItemService itemServoice;

	@GetMapping(path = "/item/put")
	public ResponseEntity<String> item(@RequestHeader("Authorization") String token,  @RequestParam("name") String name) {

		try {
			return itemServoice.putItem(token,name);
		} catch (Exception e) {			
			e.printStackTrace();
			return new ResponseEntity<String>("Unexpected API error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	@GetMapping(path = "/items")
	public ResponseEntity<Object> getItems(@RequestHeader("Authorization") String token) {

		try {
			return itemServoice.getItems(token);
		} catch (Exception e) {			
			e.printStackTrace();
			return new ResponseEntity<>("Unexpected API error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}


}
