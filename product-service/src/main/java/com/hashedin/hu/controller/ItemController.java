package com.hashedin.hu.controller;

import com.hashedin.hu.dto.ItemCountDto;
import com.hashedin.hu.model.Item;
import com.hashedin.hu.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("item")
public class ItemController {

    @Autowired
    ItemRepository itemService;

    @GetMapping("/all")
    public List<Item> getAllCategory(){
        return itemService.findAll();
    }

    @PutMapping("/reduce")
    public ResponseEntity<String> reduceItemCount( @RequestBody List<ItemCountDto> itemList){
        List<Item> changedItem=new ArrayList<>();
        for(ItemCountDto item : itemList) {
            Item dbItem=itemService.getById(item.getItemId());
            int newQuantity= dbItem.getQuantity()-item.getItemCount();
            dbItem.setQuantity(newQuantity);

            changedItem.add(dbItem);
        }
        itemService.saveAll(changedItem);
        return new ResponseEntity<>("Item quantity updated successfully", HttpStatus.OK);
    }
}
