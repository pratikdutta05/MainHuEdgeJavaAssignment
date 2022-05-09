package com.hashedin.hu.service;

import com.hashedin.hu.controller.PaymentController;
import com.hashedin.hu.dto.*;
import com.hashedin.hu.exceptions.ApplicationException;
import com.hashedin.hu.feign.api.CartAPI;
import com.hashedin.hu.feign.api.ProductAPI;
import com.hashedin.hu.model.Payment;
import com.hashedin.hu.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private static Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    CartAPI cartAPI;

    @Autowired
    ProductAPI productAPI;

    @Autowired
    PaymentRepository paymentRepository;

    @Transactional
    public void makePayment(Payment payment) {

        if(payment.getPaymentId()!=null) {

           if( !paymentRepository.existsById(payment.getPaymentId())){
                 logger.error("Payment Id {} does not exist !",payment.getPaymentId());
                 throw new ApplicationException("Invalid Data ! Payment Id is not correct");
           }

        }
        List<String> cartIds=Arrays.asList(payment.getCartIdList().split(","));
        ShowCartDto showCartDto=cartAPI.getCartItemForUser(payment.getUserId(),payment.getStatus().toString()).getBody();

        //One Discount Example 10% discount or 5% based on condition
        if(showCartDto.getTotalPrice()>50000){
            payment.setFinalPrice(showCartDto.getTotalPrice()*0.9);
        }
        else{
            payment.setFinalPrice(showCartDto.getTotalPrice()*0.95);
        }

        List<Cart> cartList = showCartDto.getCartList();
        List<ItemCountDto> itemsCount=new ArrayList<>();

        for(Cart c:cartList){
            if(!cartIds.contains(c.getCartId().toString())){
                logger.error("Invalid Cart ids in request body !");
                throw new ApplicationException("Invalid Data ! Cart Id is not correct");
            }
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
