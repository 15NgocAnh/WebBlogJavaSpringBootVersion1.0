package com.mycompany.controller.admin;

import com.mycompany.entity.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.entity.UserEntity;
import com.mycompany.services.IRoleService;
import com.mycompany.services.IUserService;


@Controller
@RequestMapping("/admin")
public class userController {

	@Autowired
	private IUserService userService;
	@Autowired
	private IRoleService roleService;

	@GetMapping("/user")
	@PreAuthorize("hasRole('ADMIN')")
	public String index(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
		model.addAttribute("userDetails", userDetails.getUser());
		model.addAttribute("users", userService.findAll());
		return "admin/user/index";
	}

	@GetMapping("/add-user")
	@PreAuthorize("hasRole('ADMIN')")
    public String add(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
		model.addAttribute("userDetails", userDetails.getUser());
		model.addAttribute("roles", roleService.findAll());
		UserEntity user = new UserEntity();
		user.setStatus(true);
		user.setGender(true);
		model.addAttribute("user", user);
        return "admin/user/add";
    }

	@PostMapping("/add-user")
	@PreAuthorize("hasRole('ADMIN')")
    public String save(@ModelAttribute("user") UserEntity user){
        if (userService.create(user) != null) {
            return "redirect:/admin/user";
        } else {
            return "admin/user/add";
        }
    }


	@GetMapping("/edit-user/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public String edit(Model model, @PathVariable("id") Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
		model.addAttribute("userDetails", userDetails.getUser());
		model.addAttribute("listRoles", roleService.findAll());
		UserEntity user = userService.findById(id);
		model.addAttribute("user", user);
		return "admin/user/edit";
	}

	@PostMapping("/edit-user")
	@PreAuthorize("hasRole('ADMIN')")
	public String update(@ModelAttribute("user") UserEntity user) {
		if (userService.update(user.getId(),user)!=null) {
			return "redirect:/admin/user";
		} else {
			return "admin/user/edit";
		}
	}

	@GetMapping("/delete-user/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public String delete(Model model, @PathVariable("id") Long id) {
		if (userService.delete(id)!=null) {
			return "redirect:/admin/user";
		} else {
			return "redirect:/admin/user";
		}
	}
}
