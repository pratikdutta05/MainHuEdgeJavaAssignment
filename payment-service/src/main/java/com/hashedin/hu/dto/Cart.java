package com.hashedin.hu.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
public class Cart {

    private Integer cartId;

    private Integer userId;

    private Integer itemId;

    private Integer quantity;

    private Status status;

}
