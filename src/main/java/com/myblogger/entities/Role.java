package com.myblogger.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role {

	@Id
	private int roleId;
	
	private String roleName;

	@OneToMany(mappedBy = "role", cascade =  CascadeType.ALL)
	private Set<User> users = new HashSet<>();
}
