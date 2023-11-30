package com.mycompany.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mycompany.entity.UserEntity;
import com.mycompany.repository.UserRepository;
import com.mycompany.services.IUserService;

@Service
public class UserService implements IUserService {

	private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserEntity> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public UserEntity findById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public UserEntity create(UserEntity user) {
        // Thực hiện lưu thông tin ngày tạo và người tạo
        user.setCreatedDate(LocalDateTime.now());
        user.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        user.setPassword(passwordEncoder.encode("123456"));
        return this.userRepository.save(user);
    }

    @Override
    public UserEntity update(Long id, UserEntity updatedUser) {
        UserEntity existingUser = this.userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            // Cập nhật thông tin ngày sửa đổi và người sửa đổi
            existingUser.setModifiedDate(LocalDateTime.now());
            existingUser.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            // Cập nhật thông tin
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setFirstname(updatedUser.getFirstname());
            existingUser.setLastname(updatedUser.getLastname());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhone(updatedUser.getPhone());
            existingUser.setAddress(updatedUser.getAddress());
            existingUser.setAvatar(updatedUser.getAvatar());
            existingUser.setBirthday(updatedUser.getBirthday());
            existingUser.setRoles(updatedUser.getRoles());
            existingUser.setGender(updatedUser.getGender());
            existingUser.setStatus(updatedUser.getStatus());
            return this.userRepository.save(existingUser);
        }
        return null;
    }

    @Override
    public Boolean changePassword(Long id, String oldPassword, String newPassword) {
        // Tìm kiếm người dùng theo ID
        UserEntity user = this.userRepository.findById(id).orElse(null);

        if (user != null) {
            // Kiểm tra mật khẩu cũ
            if (checkPassword(oldPassword, user)) {
                // Mã hóa và cập nhật mật khẩu mới
                user.setPassword(passwordEncoder.encode(newPassword));

                // Lưu thay đổi vào cơ sở dữ liệu
                this.userRepository.save(user);

                return true;
            }
        }
        return false;
    }


    @Override
    public Boolean delete(Long id) {
        try {
            this.userRepository.deleteById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean checkPassword(String oldPassword, UserEntity user) {
        return passwordEncoder.matches(oldPassword, user.getPassword()); // Check old password is correct or not
    }

    @Override
    public List<Object[]> findAllUsers() {
        return this.userRepository.findAllUsers();
    }

    @Override
    public Boolean changePassword(String username, String oldPassword, String newPassword) {
        UserEntity user = this.userRepository.findByUsername(username);
        if (checkPassword(oldPassword, user)) {
            user.setPassword(passwordEncoder.encode(newPassword));
            this.userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }
}
