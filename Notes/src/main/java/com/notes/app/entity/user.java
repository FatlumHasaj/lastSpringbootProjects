package com.notes.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class user {
	@Id
    private Long id;
	
	private String email;
	
	private String username;
	
	private String password;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	
    // Optionally add getters/setters to avoid issues later
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
