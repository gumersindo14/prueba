//package com.safeish.web.controller;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.InitBinder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.safeish.safebox.jpa.entity.Safebox;
//import com.safeish.safebox.jpa.repository.BeforeCreateSafeboxValidator;
//import com.safeish.safebox.jpa.repository.SafeboxRepository;
//
//@RestController
//@RequestMapping("")
//public class SafeboxRegisterController {
//	
//	@Autowired
//	SafeboxRepository safeboxRepository;
//	
//    @InitBinder("safebox")
//    protected void bidValidator(WebDataBinder binder) {
//        binder.addValidators(new BeforeCreateSafeboxValidator());
//    }
//
//	
//	@PostMapping("/safebox")
//	public ResponseEntity<String> addSafebox( @RequestBody @Valid Safebox safebox) {		
//		var newSafebox = safeboxRepository.save(safebox);
//		return ResponseEntity.ok(newSafebox.getId().toString());		
//	}
//	
//}
