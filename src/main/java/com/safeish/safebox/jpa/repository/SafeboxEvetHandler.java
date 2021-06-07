package com.safeish.safebox.jpa.repository;

import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import com.safeish.safebox.jpa.entity.Safebox;

@RepositoryEventHandler(SafeboxRepository.class)
public class SafeboxEvetHandler {
	
    @HandleBeforeCreate
    public void handleAuthorBeforeCreate(Safebox author){
        String name = author.getName();
    }

	
	
}
