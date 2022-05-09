package com.hashedin.hu.service;

import com.hashedin.hu.dto.*;
import com.hashedin.hu.feign.api.CartAPI;
import com.hashedin.hu.feign.api.ProductAPI;
import com.hashedin.hu.model.Payment;
import com.hashedin.hu.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    CartAPI cartAPI;

    @Autowired
    ProductAPI productAPI;

    @Autowired
    PaymentRepository paymentRepository;

    @Transactional
    public void makePayment(Payment payment) {

        if(payment.getPaymentId()==null) {

        }
        String [] cartIds=payment.getCartIdList().split(",");
        ShowCartDto showCartDto=cartAPI.getCartItemForUser(payment.getUserId(),payment.getStatus().toString()).getBody();

        //One Discount Example 10% discount
        if(showCartDto.getTotalPrice()>20000){
            payment.setFinalPrice(showCartDto.getTotalPrice()*0.9);
        }

        List<Cart> cartList = showCartDto.getCartList();
        List<ItemCountDto> itemsCount=new ArrayList<>();

        for(Cart c:cartList){
            ItemCountDto itemCountDto=new ItemCountDto();
            itemCountDto.setItemCount(c.getQuantity());
            itemCountDto.setItemId(c.getItemId());

            itemsCount.add(itemCountDto);

        }
        //Calling Product service to update the quantity
        productAPI.reduceItemCount(itemsCount);

        //Update the Status of Cart
        cartAPI.changeStatus(showCartDto.getCartList(),"COMPLETED");

        payment.setStatus(Status.COMPLETED);

        paymentRepository.save(payment);

    }
}
