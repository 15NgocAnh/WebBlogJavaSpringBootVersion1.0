package com.mycompany.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "role")
public class RoleEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles")
	private List<UserEntity> users = new ArrayList<>();
	
    
	public RoleEntity() {
		super();
	}


	public RoleEntity(String name, List<UserEntity> users) {
		super();
		this.name = name;
		this.users = users;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public List<UserEntity> getUsers() {
		return users;
	}


	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}

    
}
