package com.mycompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.entity.RoleEntity;



public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
	
}
