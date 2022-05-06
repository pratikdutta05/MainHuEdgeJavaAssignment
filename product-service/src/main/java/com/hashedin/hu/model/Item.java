package com.hashedin.hu.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name="item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;

    private String itemName;

    private Integer quantity;

    private Double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="categoryId", referencedColumnName = "categoryId")
    private Category category;

    public Item(String itemName, Integer quantity, Double price, Category category) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
    }
}
