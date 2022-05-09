package com.hashedin.hu.model;

import com.hashedin.hu.dto.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name="payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentId;

    private Integer userId;

    private String cartIdList;

    private Status status;

    private Double finalPrice;

}
