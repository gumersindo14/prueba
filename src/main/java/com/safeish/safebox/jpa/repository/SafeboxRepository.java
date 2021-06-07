package com.safeish.safebox.jpa.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.safeish.safebox.jpa.entity.Safebox;

@RepositoryRestResource(path = "safebox", collectionResourceRel = "safebox")
public interface SafeboxRepository extends PagingAndSortingRepository<Safebox, UUID> {
	
	@Query(value = "select s from Safebox s where s.name = :name")
	Safebox findByName(@Param("name")String name);
	
	Optional<Safebox> findById(UUID id);
	
	@Override
	<S extends Safebox> S save(S safebox);	

	
}