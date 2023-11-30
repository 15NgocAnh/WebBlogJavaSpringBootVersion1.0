package com.mycompany.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mycompany.entity.CustomUserDetails;
import com.mycompany.entity.RoleEntity;
import com.mycompany.entity.UserEntity;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private  IUserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        Collection<GrantedAuthority> grantedAuthoritySet = new ArrayList<>();
     // Lấy danh sách các vai trò của người dùng
        List<RoleEntity> roles = user.getRoles();

        // Chuyển đổi danh sách vai trò thành danh sách GrantedAuthority
        for (RoleEntity role : roles) {
        	grantedAuthoritySet.add(new SimpleGrantedAuthority(role.getName()));
        }

        // Trả về UserDetails
        return new CustomUserDetails( user, grantedAuthoritySet);
    }
}
