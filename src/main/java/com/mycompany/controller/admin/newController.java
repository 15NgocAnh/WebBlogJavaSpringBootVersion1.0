package com.mycompany.controller.admin;

import com.mycompany.entity.CustomUserDetails;
import com.mycompany.entity.NewEntity;
import com.mycompany.services.ICategoryService;
import com.mycompany.services.INewService;
import com.mycompany.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
public class newController {

    @Autowired
    private StorageService storageService;

    @Autowired
    private INewService newService;

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/news")
    public String index(Model model, @Param("keyword") String keyword, @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        model.addAttribute("userDetails", userDetails.getUser());
        Page<NewEntity> listnews = this.newService.findAll(page);
        if (keyword != null) {
            listnews = this.newService.search(keyword, page);
            model.addAttribute("keyword", keyword);
        }
        model.addAttribute("totalPages", listnews.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("listnews", listnews);
        return "admin/news/index";
    }

    @GetMapping("/add-news")
    public String add(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        model.addAttribute("userDetails", userDetails.getUser());
        model.addAttribute("categories", this.categoryService.findAll());
        NewEntity NewEntity = new NewEntity();
        model.addAttribute("news", NewEntity);
        return "admin/news/add";
    }

    @PostMapping("/add-news")
    public String save(@ModelAttribute("news") NewEntity newEntity, @RequestParam("fileImage") MultipartFile file) {
        this.storageService.store(file);
        String fileName = file.getOriginalFilename();
        Boolean isEmpty = fileName == null || fileName.trim().length() == 0;
        if (!isEmpty) {
            newEntity.setThumbnail(fileName);
        }
        else newEntity.setThumbnail("");
        //save
        if (this.newService.create(newEntity)!=null) {
            return "redirect:/admin/news";
        } else {
            return "admin/news/add";
        }
    }

    @GetMapping("/edit-news/{id}")
    public String edit(Model model, @PathVariable("id") Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        model.addAttribute("userDetails", userDetails.getUser());
        model.addAttribute("categories", this.categoryService.findAll());
        model.addAttribute("news", this.newService.findById(id));
        return "admin/news/edit";
    }

    @PostMapping("/edit-news")
    public String update(@ModelAttribute("news") NewEntity newEntity, @RequestParam("fileImage") MultipartFile file) {    	
    	// Kiểm tra xem có file mới được chọn hay không
        if (!file.isEmpty()) {
            this.storageService.store(file);
            String fileName = file.getOriginalFilename();
            newEntity.setThumbnail(fileName);
        } else {
            // Nếu không có file mới, giữ nguyên thumbnail cũ
            NewEntity existingNew = this.newService.findById(newEntity.getId());
            newEntity.setThumbnail(existingNew.getThumbnail());
        }
        // Sử dụng ID từ đối tượng newEntity để cập nhật
        if (this.newService.update(newEntity.getId(), newEntity) != null) {
            return "redirect:/admin/news";
        } else {
            return "admin/news/edit";
        }
    }

    @GetMapping("/delete-news/{id}")
    public String delete(Model model, @PathVariable("id") Long id) {
        if (this.newService.delete(id)) {
            return "redirect:/admin/news";
        }else {
            return "redirect:/admin/news";
        }
    }

}
