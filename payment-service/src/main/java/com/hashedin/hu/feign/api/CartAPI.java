package com.hashedin.hu.feign.api;

import com.hashedin.hu.dto.Cart;
import com.hashedin.hu.dto.Item;
import com.hashedin.hu.dto.ShowCartDto;
import com.hashedin.hu.dto.SuccessDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "cart-service")
public interface CartAPI {

    @GetMapping("/cart/user/{userId}")
    public ResponseEntity<ShowCartDto> getCartItemForUser(@PathVariable Integer userId, @RequestParam String status);

    @PostMapping("/cart/status-change")
    public ResponseEntity<SuccessDto> changeStatus(@RequestBody List<Cart> carts, @RequestParam String status);
}
