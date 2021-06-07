package com.safeish.safebox.service;

import java.util.UUID;

import com.safeish.safebox.entity.Safebox;
import com.safeish.safebox.service.impl.SafeboxNotFoundException;
import com.safeish.safebox.service.impl.SafeboxblockedExeption;
import com.safeish.safebox.service.impl.UnauthorizedException;

public interface ISafeboxService {
	public UUID safeboxRegister( Safebox safebox) throws Exception;

	String openSafebox(String username, String password) throws SafeboxNotFoundException, SafeboxblockedExeption, UnauthorizedException;
}
