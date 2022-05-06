package com.hashedin.hu.controller;


import com.hashedin.hu.model.Category;
import com.hashedin.hu.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    CategoryRepository categoryService;

    @GetMapping("/all")
    public List<Category> getAllCategory(){
        return categoryService.findAll();
    }


}
