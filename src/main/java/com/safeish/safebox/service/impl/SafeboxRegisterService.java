package com.safeish.safebox.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import com.safeish.safebox.jpa.entity.Safebox;
import com.safeish.safebox.jpa.repository.SafeboxRepository;
import com.safeish.safebox.service.ISafeboxService;

@Service
public class SafeboxRegisterService implements ISafeboxService, UserDetailsService {
	
	@Autowired
	SafeboxRepository safeboxRepository;
	
	@Override
	public ResponseEntity<String> safeboxRegister(@Valid Safebox safebox) {
		try
		{
			validatePassword(safebox);
			if (existSafebox(safebox.getName()))
				return new ResponseEntity<String>("Safebox already exists", HttpStatus.CONFLICT);
			else
			{
				var safeboxSaved = saveSafeBox(safebox);
				return ResponseEntity.ok(safeboxSaved.getId().toString());
			}
		} catch (ValidatePasswordExceptions ex)
		{
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception e)
		{
			Throwable cause = ((TransactionSystemException) e).getRootCause();
			if (cause instanceof ConstraintViolationException)
			{
				return parserExceptionValidationToResponse(cause);
			}
		}
		
		return new ResponseEntity<String>("Unexpected API error", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	private Boolean existSafebox(String name) {
		var safebox = safeboxRepository.findByName(name);
		return safebox != null;
	}
	
	private Safebox saveSafeBox(Safebox safebox) {
		PasswordEncoder codificador = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		safebox.setPassword(codificador.encode(safebox.getPassword()));
		return safeboxRepository.save(safebox);
	}
	
	private void validatePassword(Safebox safebox) throws Exception {
		PasswordValidator validator = new PasswordValidator(Arrays.asList(				
				new LengthRule(8, 30),				
				new CharacterRule(EnglishCharacterData.UpperCase, 1),				
				new CharacterRule(EnglishCharacterData.LowerCase, 1),				
				new CharacterRule(EnglishCharacterData.Digit, 1),				
				new CharacterRule(EnglishCharacterData.Special, 1),				
				new WhitespaceRule()		
		));
		RuleResult result = validator.validate(new PasswordData(safebox.getPassword()));
		
		if (!result.isValid())
		{			
			List<String> messages = validator.getMessages(result);
			String messageTemplate = messages.stream().collect(Collectors.joining(","));
			
			throw new ValidatePasswordExceptions(messageTemplate);
		}
	}
	
	private ResponseEntity<String> parserExceptionValidationToResponse(Throwable cause) {
		
		Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) cause).getConstraintViolations();
		String violationsMessage = "";
		for (ConstraintViolation<?> constraintViolation : constraintViolations)
		{
			constraintViolation.getInvalidValue().toString();
			violationsMessage += constraintViolation.getMessage() + " ";
		}
		return new ResponseEntity<String>(violationsMessage, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Safebox safebox = safeboxRepository.findById(UUID.fromString(username)).get();
		PasswordEncoder codificador = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		if (safebox == null)
			throw new UsernameNotFoundException("User" + username + " was not found");
		if (safebox.getAttempts() > 2)
		{
			System.out.println("user blocked");
			return new User(safebox.getName(), codificador.encode(safebox.getPassword()), false, false, false, false, Arrays.asList(new SimpleGrantedAuthority("ADMIN")));
		}
		return new User(safebox.getName(), codificador.encode(safebox.getPassword()), Arrays.asList(new SimpleGrantedAuthority("ADMIN")));
	}
	
}
