package com.hashedin.hu.controller;

import com.hashedin.hu.dto.Item;
import com.hashedin.hu.dto.ShowCartDto;
import com.hashedin.hu.dto.SuccessDto;
import com.hashedin.hu.feign.api.ProductAPI;
import com.hashedin.hu.model.Cart;
import com.hashedin.hu.model.Status;
import com.hashedin.hu.repository.CartRepository;
import com.hashedin.hu.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hashedin.hu.model.Status;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    ProductAPI productAPI;


    //Adding Item to Cart First Time
    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestBody Cart cart) {

        cart.setStatus(Status.INPROGRESS);

        Cart response= cartService.addToCart(cart);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    //Get All the Details present in Cart for a Specific User
    @GetMapping("/user/{userId}")
    public ResponseEntity<ShowCartDto> getCartItemForUser(@PathVariable Integer userId, @RequestParam String status){

        int flag= status.equalsIgnoreCase("INPROGRESS")?0:1;

        ShowCartDto result =cartService.searchByUserID(flag,userId);

        return new ResponseEntity<>(result,HttpStatus.OK);

    }

    @DeleteMapping("/remove/{cartId}")
    public ResponseEntity<SuccessDto> removeItemFromCart(@PathVariable Integer cartId, @RequestParam Integer userId)
    {

        cartService.removeItemFromCart(cartId,userId);

        SuccessDto response=new SuccessDto();
        response.setMessage("Remove Item from Cart Successfull !");

        return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
    }

    @PostMapping("/status-change")
    public ResponseEntity<SuccessDto> changeStatus(@RequestBody List<Cart> carts,@RequestParam String status) {

        cartService.changeStatus(carts,status);

        SuccessDto response=new SuccessDto();
        response.setMessage("Cart List status changed !");

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
