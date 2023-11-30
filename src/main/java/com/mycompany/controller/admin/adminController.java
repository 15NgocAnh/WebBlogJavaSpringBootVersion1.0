package com.mycompany.controller.admin;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.entity.CustomUserDetails;


@Controller
@RequestMapping("/admin")
public class adminController {

	@GetMapping
	public String index() {
		return "redirect:/admin/";
	}

	@RequestMapping("/")
	public String admin(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
		model.addAttribute("userDetails", userDetails.getUser());
		return "admin/index";
	}
}
