package com.safeish.securing;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import com.safeish.safebox.jpa.entity.Safebox;
import com.safeish.safebox.jpa.repository.SafeboxRepository;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Autowired
    private SafeboxRepository safeboxRepository;
        
    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {    	
    	String id = e.getAuthentication().getName();
    	
    	if(id != null) {
    		Safebox safebox = safeboxRepository.findById(UUID.fromString(id)).get();    		
    		if (safebox != null && safebox.getAttempts() != null && safebox.getAttempts() < AuthenticationSuccessListener.MAX_ATTEMPTS) {      			
    			safebox.setAttempts(safebox.getAttempts()+1);
    			safeboxRepository.save(safebox);    			
    		}    		
    	}
    	
    }
}