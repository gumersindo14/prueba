package com.safeish.item.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.safeish.item.repository.ItemRepository;
import com.safeish.safebox.jpa.entity.Safebox;
import com.safeish.safebox.jpa.repository.SafeboxRepository;
import com.safeish.securing.AuthenticationSuccessListener;
import com.safeish.securing.JwtTokenUtil;
import com.safeish.securing.SecurityConstants;

@Service
public class ItemService implements IItemService{
	
	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	SafeboxRepository safeboxRepository;
	
	@Override
	public ResponseEntity<String> putItem(String token, String itemName) {
		
		Item item = new Item();		
		
		String realToken = token.replace(SecurityConstants.TOKEN_PREFIX, "");
		String name = JwtTokenUtil.getInstance().getUsernameFromToken(realToken);
		Safebox safebox = safeboxRepository.findByName(name);
		
		if(itemName == null)
			return new ResponseEntity<>("Malformed expected data",HttpStatus.UNPROCESSABLE_ENTITY );		
		if(safebox == null )
			return new ResponseEntity<>("Requested safebox does not exist",HttpStatus.NOT_FOUND );
		
		if(AuthenticationSuccessListener.MAX_ATTEMPTS <= safebox.getAttempts())
			return new ResponseEntity<>("Requested safebox is locked", HttpStatus.LOCKED);		

		item.setName(itemName);
		item.setSafebox(safebox);		
		itemRepository.save(item);
		
		return ResponseEntity.ok(item.getName());
	}
	
	@Override
	public ResponseEntity<Object> getItems(String token) {
		
		String realToken = token.replace(SecurityConstants.TOKEN_PREFIX, "");
		String name = JwtTokenUtil.getInstance().getUsernameFromToken(realToken);		
		if(name == null)
			return new ResponseEntity<>("Malformed expected data", HttpStatus.NOT_FOUND);		

		Safebox safebox = safeboxRepository.findByName(name);
		if(safebox == null )
			return new ResponseEntity<>("Requested safebox does not exist", HttpStatus.NOT_FOUND);		
		if(AuthenticationSuccessListener.MAX_ATTEMPTS <= safebox.getAttempts())
			return new ResponseEntity<>("Requested safebox is locked", HttpStatus.LOCKED);
				
		return ResponseEntity.ok(itemRepository.findAll());
	}


	
}
