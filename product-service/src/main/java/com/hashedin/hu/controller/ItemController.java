package com.hashedin.hu.controller;

import com.hashedin.hu.dto.ItemCountDto;
import com.hashedin.hu.model.Item;
import com.hashedin.hu.repository.ItemRepository;
import com.hashedin.hu.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product/item")
public class ItemController {

    private static Logger logger = LoggerFactory.getLogger(ItemController.class);


    @Autowired
    ItemService itemService;

    @GetMapping("/all")
    public List<Item> getAllItem(){
        logger.info("Request to show all Item");
        return itemService.findAll();
    }

    @PutMapping("/reduce")
    public ResponseEntity<String> reduceItemCount( @RequestBody List<ItemCountDto> itemList){

        itemService.updateQuantity(itemList);
        return new ResponseEntity<>("Item quantity updated successfully", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable("id") Integer id){

        logger.info("Get item details by Id");
        return new ResponseEntity<>(itemService.findById(id),HttpStatus.OK);

    }
}
