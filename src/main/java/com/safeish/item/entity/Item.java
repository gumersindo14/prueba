package com.safeish.item.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.safeish.safebox.entity.Safebox;

@Entity
public class Item {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID itemId;
	
    @ManyToOne
    @JoinColumn(name="safebox_id")
    private Safebox safebox;
       
    //read = "pgp_sym_decrypt(content, 'password')", 
	//@ColumnTransformer(write = "pgp_sym_encrypt(?, 'password')")
	private String name;

	public UUID getItemId() {
		return itemId;
	}

	public void setItemId(UUID itemId) {
		this.itemId = itemId;
	}

	public Safebox getSafebox() {
		return safebox;
	}

	public void setSafebox(Safebox safebox) {
		this.safebox = safebox;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
