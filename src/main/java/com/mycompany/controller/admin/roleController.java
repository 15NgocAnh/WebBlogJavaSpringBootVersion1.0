package com.mycompany.controller.admin;

import java.util.List;

import com.mycompany.entity.CustomUserDetails;
import com.mycompany.entity.RoleEntity;
import com.mycompany.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mycompany.entity.CategoryEntity;
import com.mycompany.services.ICategoryService;

@Controller
@RequestMapping("/admin") // Đặt đường dẫn cơ sở cho toàn bộ controller
public class roleController {

    @Autowired
    private IRoleService roleService;

    @GetMapping("/role")
    @PreAuthorize("hasRole('ADMIN')")
    public String index(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        model.addAttribute("userDetails", userDetails.getUser());
        model.addAttribute("roles", roleService.findAll());
        return "admin/role/index";
    }

    @GetMapping("/add-role")
    @PreAuthorize("hasRole('ADMIN')")
    public String add(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        model.addAttribute("userDetails", userDetails.getUser());
        RoleEntity role = new RoleEntity();
        model.addAttribute("role", role);
        return "admin/role/add";
    }

    @PostMapping("/add-role")
    @PreAuthorize("hasRole('ADMIN')")
    public String save(@ModelAttribute("role") RoleEntity role) {
        if (roleService.create(role)!=null) {
            return "redirect:/admin/role";
        } else {
            return "admin/role/add";
        }
    }

    @GetMapping("/edit-role/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String edit(Model model, @PathVariable("id") Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        model.addAttribute("userDetails", userDetails.getUser());
        RoleEntity role = roleService.findById(id);
        model.addAttribute("role", role);
        return "admin/role/edit";
    }

    @PostMapping("/edit-role")
    @PreAuthorize("hasRole('ADMIN')")
    public String update(@ModelAttribute("role") RoleEntity role) {
        if (roleService.update(role.getId(),role)!=null) {
            return "redirect:/admin/role";
        } else {
            return "admin/role/edit";
        }
    }

    @GetMapping("/delete-role/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(Model model, @PathVariable("id") Long id) {
        if (roleService.delete(id)!=null) {
            return "redirect:/admin/role";
        } else {
            return "redirect:/admin/role";
        }
    }

}

 