package com.hashedin.hu.dto;

import com.hashedin.hu.model.Cart;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ShowCartDto {

    private Integer userId;
    private String username;
    private String userEmail;
    private double totalPrice;
    List<Item> itemList;
    List<Cart> cartList;

}
