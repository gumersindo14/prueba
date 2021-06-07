package com.safeish.item.service;

import org.springframework.http.ResponseEntity;

public interface IItemService {

	ResponseEntity<String> putItem(String safeboxId, String itemName);

	ResponseEntity<Object> getItems(String token);

}
