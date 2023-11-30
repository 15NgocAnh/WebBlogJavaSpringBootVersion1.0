package com.mycompany.controller;

import com.mycompany.services.INewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.services.ICategoryService;

@Controller
public class PageController {
	
	@Autowired 
	private ICategoryService categoryService;
    @Autowired
    private INewService newService;

    @RequestMapping(value = "/page")
    public String GetPage(Model model) {
    	model.addAttribute("category", categoryService.findAll());
        model.addAttribute("top3news", newService.findTop3ByOrderByCreateDateDesc());
        model.addAttribute("top7categories", categoryService.findTop7CategoriesWithPostCount());

        return "page";
    }
}
