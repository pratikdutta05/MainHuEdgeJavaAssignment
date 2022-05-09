package com.hashedin.hu.service;

import com.hashedin.hu.controller.CartController;
import com.hashedin.hu.dto.Item;
import com.hashedin.hu.dto.ShowCartDto;
import com.hashedin.hu.dto.User;
import com.hashedin.hu.exceptions.ApplicationException;
import com.hashedin.hu.exceptions.ResourceNotFoundException;
import com.hashedin.hu.feign.api.ProductAPI;
import com.hashedin.hu.feign.api.UserAPI;
import com.hashedin.hu.model.Cart;
import com.hashedin.hu.model.Status;
import com.hashedin.hu.repository.CartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private static Logger logger = LoggerFactory.getLogger(CartService.class);

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserAPI userAPI;

    @Autowired
    ProductAPI productAPI;


    public Cart addToCart(Cart cart) {

        logger.debug("Checking the user is registered or not");

        User user=userAPI.getUserById(cart.getUserId()).getBody();

        if(!user.getIsLogIn()){
            logger.error("User is not logged in !");
            throw new ApplicationException("Please Log in to continue !");
        }

        Item item=productAPI.getItemById(cart.getItemId()).getBody();

        if(item.getQuantity()<cart.getQuantity()) {
            throw new ApplicationException("Invalid Data !! Ordered item is more that thresold");
        }

        logger.debug("Saving the cart details into database");



        return cartRepository.save(cart);
    }

    public ShowCartDto searchByUserID(int flag, Integer userId) {

        logger.debug("Checking the user is registered or not");

        User user=userAPI.getUserById(userId).getBody();

        if(!user.getIsLogIn()){
            logger.error("User is not logged in !");
            throw new ApplicationException("Please Log in to continue !");
        }

        List<Cart> dbCartList=cartRepository.searchByUserID(flag,userId);

        if(dbCartList.isEmpty()){
            logger.error("There is no item for the user {} ",userId);
            throw new ResourceNotFoundException("There is no item in cart");
        }

        ShowCartDto showCartDto=new ShowCartDto();

        showCartDto.setUsername(user.getName());
        showCartDto.setUserEmail(user.getEmailId());
        showCartDto.setUserId(userId);

        double totalPrice=0.0;

        List<Item> itemList=new ArrayList<>();
        List<Cart> cartlist=new ArrayList<>();
        for(Cart cart: dbCartList){
            Item item=productAPI.getItemById(cart.getItemId()).getBody();
            totalPrice+= item.getPrice()*cart.getQuantity();
            itemList.add(item);
            cartlist.add(cart);
        }

        showCartDto.setItemList(itemList);
        showCartDto.setCartList(cartlist);
        showCartDto.setTotalPrice(totalPrice);

        return  showCartDto;
    }

    public void removeItemFromCart(Integer cartId, Integer userId) {

        User user=userAPI.getUserById(userId).getBody();

        if(!user.getIsLogIn()){
            logger.error("User is not logged in !");
            throw new ApplicationException("Please Log in to continue !");
        }

        if(cartRepository.existsById(cartId)){
            logger.debug("Cart Service to remove the item from cart cardId: {}",cartId);
            cartRepository.deleteById(cartId);
        }
        else {
            logger.error("Cart Service cardId: {} doesnot exist",cartId);
            throw new ResourceNotFoundException("Cart Id does not exist");
        }

    }

    public void changeStatus(List<Cart> carts, String status) {

        if(!status.equalsIgnoreCase("COMPLETED")){
            throw new IllegalArgumentException("Wrong Status");
        }

        List<Cart> updatedList=new ArrayList<>();
        for(Cart cart:carts){
            if(cartRepository.existsById(cart.getCartId())) {
                cart.setStatus(Status.COMPLETED);
                updatedList.add(cart);
            }
            else {
                logger.error("Cart Service cardId: {} doesnot exist",cart.getCartId());
                throw new ResourceNotFoundException("Cart Id does not exist");

            }
        }

        cartRepository.saveAll(updatedList);
    }
}
