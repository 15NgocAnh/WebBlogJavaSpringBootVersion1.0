package com.mycompany.controller;

import com.mycompany.entity.CategoryEntity;
import com.mycompany.entity.NewEntity;
import com.mycompany.services.ICategoryService;
import com.mycompany.services.INewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private INewService newService;

    @GetMapping("")
    public String index(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
        List<NewEntity> listnews;
        if (keyword != null && !keyword.isEmpty()) {
        	listnews = newService.search(keyword);
        } else {
            listnews = newService.findAllByOrderByCreateDateDesc();
        }
        model.addAttribute("news", listnews);
        model.addAttribute("category", categoryService.findAll());
        model.addAttribute("top5news", newService.findTop5ByOrderByCreateDateDesc());
        model.addAttribute("top3news", newService.findTop3ByOrderByCreateDateDesc());
        model.addAttribute("top7categories", categoryService.findTop7CategoriesWithPostCount());
        return "index";
    }
    
    @GetMapping("/{slug}")
    public String index(@PathVariable String slug, Model model) {
    	model.addAttribute("category", categoryService.findAll());
        CategoryEntity categoryItem = categoryService.findBySlug(slug);
        if (categoryItem != null) {
            model.addAttribute("categoryItem", categoryItem);
            model.addAttribute("titleWeb", categoryItem.getName());
        }
        model.addAttribute("news", categoryService.findNewsByCategorySlug(slug));
        model.addAttribute("top3news", newService.findTop3ByOrderByCreateDateDesc());
        model.addAttribute("top7categories", categoryService.findTop7CategoriesWithPostCount());
        return "category";
    }

	@GetMapping("/post/{newsSlug}")
	public String single(@PathVariable String newsSlug, Model model) {
        NewEntity news = newService.findBySlug(newsSlug);
        if (news != null) {
            model.addAttribute("titleWeb", news.getTitle());
            model.addAttribute("news", news);
        }
    	model.addAttribute("category", categoryService.findAll());
        model.addAttribute("top3news", newService.findTop3ByOrderByCreateDateDesc());
        model.addAttribute("top7categories", categoryService.findTop7CategoriesWithPostCount());
		return "single";
	}
}
