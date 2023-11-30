package com.mycompany.controller.admin;

import java.util.List;

import com.mycompany.entity.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.annotation.Secured;
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
public class categoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/category")
    @PreAuthorize("hasRole('ADMIN')")
    public String index(Model model, @Param("keyword") String keyword, @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        model.addAttribute("userDetails", userDetails.getUser());
        Page<CategoryEntity> category = this.categoryService.findAll(page);
        if (keyword != null) {
            category = this.categoryService.search(keyword, page);
            model.addAttribute("keyword", keyword);
        }
        model.addAttribute("totalPages", category.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("category", category);
        // Xử lý cho URL "/admin/category"
        return "admin/category/index";
    }

    @GetMapping("/add-category")
    @PreAuthorize("hasRole('ADMIN')")
    public String add(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        model.addAttribute("userDetails", userDetails.getUser());
        CategoryEntity category = new CategoryEntity();
        category.setStatus(true);
        model.addAttribute("category", category);
        // Xử lý cho URL "/admin/add-category"
        return "admin/category/add";
    }

    @PostMapping("/add-category")
    public String save(@ModelAttribute("category") CategoryEntity category) {
        if (this.categoryService.create(category)!=null) {
            return "redirect:/admin/category";
        } else {
            return "admin/category/add";
        }
    }

    @GetMapping("/edit-category/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String edit(Model model, @PathVariable("id") Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        model.addAttribute("userDetails", userDetails.getUser());
        CategoryEntity category = this.categoryService.findById(id);
        model.addAttribute("category", category);
        return "admin/category/edit";
    }

    @PostMapping("/edit-category")
    @PreAuthorize("hasRole('ADMIN')")
    public String update(@ModelAttribute("category") CategoryEntity category) {
    	if (this.categoryService.update(category.getId(), category)!=null) {
            return "redirect:/admin/category";
        } else {
            return "admin/category/edit";
        }
    }

    @GetMapping("/delete-category/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable("id") Long id) {
        if (this.categoryService.delete(id)) {
            return "redirect:/admin/category";
        }else {
            return "redirect:/admin/category";
        }
    }

}

 