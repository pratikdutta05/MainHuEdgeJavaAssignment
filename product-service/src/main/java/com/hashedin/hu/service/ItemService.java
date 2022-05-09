package com.hashedin.hu.service;

import com.hashedin.hu.controller.ItemController;
import com.hashedin.hu.dto.ItemCountDto;
import com.hashedin.hu.exceptions.ApplicationException;
import com.hashedin.hu.exceptions.ResourceNotFoundException;
import com.hashedin.hu.model.Item;
import com.hashedin.hu.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private static Logger logger = LoggerFactory.getLogger(ItemService.class);

    @Autowired
    ItemRepository itemRepository;

    public List<Item> findAll() {
        logger.debug("All item from Item Service Class");
        List<Item>  itemList=itemRepository.findAll();

        if(itemList.isEmpty()) {
            logger.error("getItemList {} item list not Found!", this.getClass().getName());
            throw new ResourceNotFoundException("Item not found");
        }
        else {
            return itemList;
        }
    }

    public void updateQuantity(List<ItemCountDto> itemList){
        logger.info("Update the count of Listed Item");

        if(itemList.isEmpty()) {
            logger.error("Invalid Argument !!", this.getClass().getName());
            throw new ApplicationException("Invalid Request Body");
        }

        List<Item> changedItem=new ArrayList<>();
        for(ItemCountDto item : itemList) {
            Item dbItem=itemRepository.getById(item.getItemId());
            int newQuantity= dbItem.getQuantity()-item.getItemCount();
            dbItem.setQuantity(newQuantity);

            changedItem.add(dbItem);
        }
        itemRepository.saveAll(changedItem);
    }

    public Item findById(Integer id) {

       Optional<Item> itemOptional=itemRepository.findById(id);

       if(itemOptional.isPresent()) {
           logger.info("getItem by id {} ", id);
           return itemOptional.get();
       }
       else {
           logger.error("getItem {} item not Found!", this.getClass().getName());
           throw new ResourceNotFoundException("Item not found");
       }
    }
}
