package com.mycompany.controller;

import com.mycompany.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/test-upload-file")
public class TestUploadFile {

    @Autowired
    private StorageService storageService;

    @GetMapping
    public String test() {
        return "test-upload-file";
    }

    @PostMapping
    public String saveFile(@PathVariable ("file") MultipartFile file) {
        this.storageService.store(file);
        return "test-upload-file";
    }
}
