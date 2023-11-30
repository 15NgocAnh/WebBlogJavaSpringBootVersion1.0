package com.mycompany.controller.admin;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mycompany.entity.CustomUserDetails;
import com.mycompany.services.IUserService;

@Controller
@RequestMapping("/admin")
public class profileController {
	
	@Autowired
	private IUserService userService;

	@GetMapping("/profile")
	public String index(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        model.addAttribute("userDetails", userDetails.getUser());
		return "admin/profile";
	}

	@PostMapping("/change-password")
	public String changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, Principal principal) {
		if (userService.changePassword(principal.getName(), oldPassword, newPassword)) {
			return "redirect:/admin/profile?message=success";
		} else {
			return "redirect:/admin/profile";
		}
	}
}
