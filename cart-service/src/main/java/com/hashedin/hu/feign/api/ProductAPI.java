package com.hashedin.hu.feign.api;

import com.hashedin.hu.dto.Item;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductAPI {

    @GetMapping("/product/item/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable("id") long id);

}
