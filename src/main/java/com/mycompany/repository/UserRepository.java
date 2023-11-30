package com.mycompany.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.mycompany.entity.UserEntity;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByUsername(String username);

	@Query("SELECT u, r FROM UserEntity u JOIN u.roles r")
	List<Object[]> findAllUsers();

	@Modifying
	@Query("UPDATE UserEntity u SET u.password = :newPassword WHERE u.username = :username")
	void updatePassword(@Param("username") String username, @Param("newPassword") String newPassword);
}
