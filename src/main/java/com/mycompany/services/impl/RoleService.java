package com.mycompany.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.mycompany.entity.RoleEntity;
import com.mycompany.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.mycompany.services.IRoleService;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;
    
    @Override
    public List<RoleEntity> findAll() {
        return this.roleRepository.findAll();
    }

    @Override
    public RoleEntity create(RoleEntity role) {
        // Thực hiện lưu thông tin ngày tạo và người tạo
        role.setCreatedDate(LocalDateTime.now());
        role.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        return this.roleRepository.save(role);
            
    }

    @Override
    public RoleEntity findById(Long id) {
        return this.roleRepository.findById(id).orElse(null);
    }

    @Override
    public RoleEntity update(Long id, RoleEntity updatedRole) {
        RoleEntity existingRole = roleRepository.findById(id).orElse(null);

        if (existingRole != null) {
            // Cập nhật thông tin ngày sửa đổi và người sửa đổi
            existingRole.setModifiedDate(LocalDateTime.now());
            existingRole.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());

            existingRole.setName(updatedRole.getName());

            // Lưu đối tượng cập nhật
            return roleRepository.save(existingRole);
        } else {
            return null;
        }
    }

	@Override
	public Boolean delete(Long id) {
        try {
            this.roleRepository.deleteById(id);
            return true;
        } catch (Exception e) {
        	 e.printStackTrace();
        }
        return false;
    }


}
