package com.safeish.safebox.jpa.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.safeish.safebox.jpa.entity.Safebox;

@Repository
public interface SafeboxRepository extends CrudRepository<Safebox, UUID> {
	
	public Safebox findByName(@Param("name")String name);
	
	
}