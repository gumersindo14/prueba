package com.safeish.safebox.service.impl;

import java.lang.reflect.MalformedParametersException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.safeish.safebox.entity.Safebox;
import com.safeish.safebox.repository.SafeboxRepository;
import com.safeish.safebox.service.ISafeboxService;
import com.safeish.safebox.service.impl.exceptions.RepeatedNameSafebox;
import com.safeish.safebox.service.impl.exceptions.ValidatePasswordExceptions;
import com.safeish.securing.JwtTokenUtil;

@Service
public class SafeboxRegisterService implements ISafeboxService {

	@Autowired
	SafeboxRepository safeboxRepository;

	@Override
	public UUID safeboxRegister(@Valid Safebox safebox) throws Exception {

		validateSafeBox(safebox);
		validatePassword(safebox);

		var safeboxSaved = saveSafeBox(safebox);
		return safeboxSaved.getId();

	}

	private void validatePassword(Safebox safebox) throws Exception {
		PasswordValidator validator = new PasswordValidator(Arrays.asList(new LengthRule(8, 30),
				new CharacterRule(EnglishCharacterData.UpperCase, 1),
				new CharacterRule(EnglishCharacterData.LowerCase, 1), new CharacterRule(EnglishCharacterData.Digit, 1),
				new CharacterRule(EnglishCharacterData.Special, 1), new WhitespaceRule()));

		RuleResult result = validator.validate(new PasswordData(safebox.getPassword()));

		if (!result.isValid()) {
			List<String> messages = validator.getMessages(result);
			String messageTemplate = messages.stream().collect(Collectors.joining(","));

			throw new ValidatePasswordExceptions(messageTemplate);
		}
	}

	private void validateSafeBox(@Valid Safebox safebox) throws RepeatedNameSafebox {

		if (safebox.getName() == null || safebox.getName() == null || safebox.getName().equals("")
				|| safebox.getPassword() == null)
			throw new MalformedParametersException();

		if (existSafebox(safebox.getName()))
			throw new RepeatedNameSafebox("Safebox already exists");

	}

	private Boolean existSafebox(String name) {
		var safebox = safeboxRepository.findByName(name);
		return safebox != null;
	}

	private Safebox saveSafeBox(Safebox safebox) {
		return safeboxRepository.save(safebox);
	}

	@Override
	public String openSafebox(String safeboxId, String password)throws SafeboxNotFoundException, SafeboxblockedExeption, UnauthorizedException {

		if (safeboxId == null || "".equals(safeboxId))
			throw new MalformedParametersException("User" + safeboxId + " was not found");

		Safebox safebox = safeboxRepository.findById(UUID.fromString(safeboxId)).get();
		if (safebox == null)
			throw new SafeboxNotFoundException();

		if (safebox.getAttempts() > 2)
			throw new SafeboxblockedExeption("Safebox: " + safebox.getId() + " blocked");

		PasswordEncoder codificador = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		if (!codificador.matches(password, safebox.getPassword()))
			throw new UnauthorizedException();

		return JwtTokenUtil.getInstance().generateToken(safebox.getId().toString());

	}

}
