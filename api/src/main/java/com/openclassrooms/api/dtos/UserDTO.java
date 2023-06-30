package com.openclassrooms.api.dtos;


import com.openclassrooms.api.entities.User;
import org.springframework.http.HttpStatusCode;
import org.springframework.lang.Nullable;

import java.util.Set;

public class UserDTO {

	private Integer id;
	private String name;
	private String email;
	private String password;

	public UserDTO(User user) {
		this.setId(user.getId());
		this.setName(user.getName());
		this.setEmail(user.getEmail());
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
