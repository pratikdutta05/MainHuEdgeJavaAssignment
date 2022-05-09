package com.hashedin.hu.controller;


import com.hashedin.hu.model.Category;
import com.hashedin.hu.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("product/category")
public class CategoryController {

    private static Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    CategoryRepository categoryService;

    @GetMapping("/all")
    public List<Category> getAllCategory(){
        logger.info("Request to see all Product Category");
        return categoryService.findAll();
    }


}
