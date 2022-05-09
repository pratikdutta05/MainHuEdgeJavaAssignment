package com.hashedin.hu.service;

import com.hashedin.hu.dto.Item;
import com.hashedin.hu.dto.ShowCartDto;
import com.hashedin.hu.dto.User;
import com.hashedin.hu.feign.api.ProductAPI;
import com.hashedin.hu.feign.api.UserAPI;
import com.hashedin.hu.model.Cart;
import com.hashedin.hu.model.Status;
import com.hashedin.hu.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserAPI userAPI;

    @Autowired
    ProductAPI productAPI;


    public Cart addToCart(Cart cart) {

        return cartRepository.save(cart);
    }

    public ShowCartDto searchByUserID(int flag, Integer userId) {

        List<Cart> dbCartList=cartRepository.searchByUserID(flag,userId);
        ShowCartDto showCartDto=new ShowCartDto();

        User user=userAPI.getUserById(userId).getBody();

        showCartDto.setUsername(user.getName());
        showCartDto.setUserEmail(user.getEmailId());
        showCartDto.setUserId(userId);

        double total=0.0;

        List<Item> itemList=new ArrayList<>();
        List<Cart> cartlist=new ArrayList<>();
        for(Cart cart: dbCartList){
            Item item=productAPI.getItemById(cart.getItemId()).getBody();
            total+= item.getPrice()*cart.getQuantity();
            itemList.add(item);
            cartlist.add(cart);
        }

        showCartDto.setItemList(itemList);
        showCartDto.setCartList(cartlist);
        showCartDto.setTotalPrice(total);
        return  showCartDto;
    }

    public void removeItemFromCart(Integer cartId, Integer userId) {
        cartRepository.deleteById(cartId);
    }

    public void changeStatus(List<Cart> carts, String status) {

        if(!status.equalsIgnoreCase("COMPLETED")){
            throw new IllegalArgumentException("Wrong Status");
        }

        List<Cart> updatedList=new ArrayList<>();
        for(Cart cart:carts){
            cart.setStatus(Status.COMPLETED);
            updatedList.add(cart);
        }

        cartRepository.saveAll(updatedList);
    }
}
