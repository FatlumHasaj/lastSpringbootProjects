package com.notes.app.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import com.notes.app.entity.user;


@Entity
@Table(name="notes")
public class notes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="title")
	private String title;

	@Column(length = 10000,name="content") 
	private String content;

	@Column(name="createdat")
	private LocalDateTime createdAt;

	@Column(name="updatedat")
	private LocalDateTime updatedAt;
	
	@ManyToOne(fetch = FetchType.LAZY) // Lazy fetch is more efficient
	@JoinColumn(name = "user_id") // Correct column name to match DB
    @JsonIgnore // Prevents infinite loops when serializing to JSON
	private user users;

	// Constructors
	public notes() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	public notes(String title, String content) {
		this.title = title;
		this.content = content;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public user getUser() {
		return users;
	}

	public void setUser(user user) {
		this.users = user;
	}
}