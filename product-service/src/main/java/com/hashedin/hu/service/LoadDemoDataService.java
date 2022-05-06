package com.hashedin.hu.service;

import com.hashedin.hu.model.Category;
import com.hashedin.hu.model.Item;
import com.hashedin.hu.repository.CategoryRepository;
import com.hashedin.hu.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoadDemoDataService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @EventListener
    public void dataLoad(ApplicationReadyEvent event) {

        List<Category> data=new ArrayList<>();
        data.add(new Category("mobile"));
        data.add(new Category("laptop"));

        categoryRepository.saveAll(data);

        List<Item> loadItem=new ArrayList<>();
        loadItem.add(new Item("samsung",100,10000.0,categoryRepository.getById(1)));
        loadItem.add(new Item("apple",10,50000.0,categoryRepository.getById(1)));
        loadItem.add(new Item("dell",50,40000.0,categoryRepository.getById(2)));

        itemRepository.saveAll(loadItem);
    }
}
