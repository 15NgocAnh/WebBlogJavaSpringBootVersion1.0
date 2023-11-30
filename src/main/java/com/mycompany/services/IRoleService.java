package com.mycompany.services;

import com.mycompany.entity.CategoryEntity;
import com.mycompany.entity.NewEntity;
import com.mycompany.entity.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IRoleService{

    List<RoleEntity> findAll();
    RoleEntity create(RoleEntity role);
    RoleEntity findById(Long id);
    RoleEntity update(Long id, RoleEntity role);
    Boolean delete(Long id);
}
