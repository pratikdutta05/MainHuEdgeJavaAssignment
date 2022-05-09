package com.hashedin.hu.controller;


import com.hashedin.hu.dto.SuccessDto;
import com.hashedin.hu.model.Payment;
import com.hashedin.hu.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payment")
public class PaymentController {

    private static Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    PaymentService paymentService;

    @PostMapping
    public ResponseEntity<SuccessDto> makePayment(@RequestBody Payment payment) {

        logger.info("Request for make payment");

        paymentService.makePayment(payment);

        SuccessDto response=new SuccessDto();
        response.setMessage("Congratulations ! Payment done successfull !");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
