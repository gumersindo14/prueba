package com.safeish.safebox.api;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.safeish.safebox.Customer;

@RepositoryRestResource(path = "customer")
public interface CustomerRepository extends CrudRepository<Customer, UUID> {}
