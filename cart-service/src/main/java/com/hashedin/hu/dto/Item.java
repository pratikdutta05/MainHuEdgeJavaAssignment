package com.hashedin.hu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private Integer itemId;

    private String itemName;

    private Integer quantity;

    private Double price;

    private Category category;

}
