package com.hashedin.hu.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "cart")
@Data
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "itemId")
    private Integer itemId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "status")
    private Status status;

    public Cart(Integer userId, Integer itemId, Integer quantity, Status status) {
        this.userId = userId;
        this.itemId = itemId;
        this.quantity=quantity;
        this.status = status;
    }
}
