package com.safebox.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("safebox")
public class HelloWorldRestController {


	@PostMapping("/hello")
	public String hola() {

		return "Hello World";
	}


}