package com.mycompany.services;

import com.mycompany.entity.RoleEntity;
import org.springframework.stereotype.Service;

import com.mycompany.entity.UserEntity;

import java.util.List;


public interface IUserService {
	
    UserEntity findByUsername(String username);
    List<UserEntity> findAll();
    UserEntity findById(Long id);
    UserEntity create(UserEntity user);
    UserEntity update(Long id, UserEntity user);

    Boolean changePassword(Long id, String oldPassword, String newPassword);
    Boolean delete(Long id);
    Boolean checkPassword(String oldPassword, UserEntity user);
    List<Object[]> findAllUsers();
    Boolean changePassword(String username, String oldPassword, String newPassword);
}
