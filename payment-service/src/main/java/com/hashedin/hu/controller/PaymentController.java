package com.hashedin.hu.controller;


import com.hashedin.hu.dto.SuccessDto;
import com.hashedin.hu.model.Payment;
import com.hashedin.hu.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping
    public ResponseEntity<SuccessDto> makePayment(@RequestBody Payment payment) {

        paymentService.makePayment(payment);

        SuccessDto response=new SuccessDto();
        response.setMessage("Remove Item from Cart Successfull !");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
