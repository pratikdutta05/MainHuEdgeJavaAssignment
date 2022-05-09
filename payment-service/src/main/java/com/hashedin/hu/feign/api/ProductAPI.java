package com.hashedin.hu.feign.api;

import com.hashedin.hu.dto.Item;
import com.hashedin.hu.dto.ItemCountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductAPI {

    @PutMapping("/product/item/reduce")
    public ResponseEntity<String> reduceItemCount( @RequestBody List<ItemCountDto> itemList);

}
