package com.jarviz.webstore.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.AbstractQueue;

@Entity
@Data
@NoArgsConstructor
@ToString(exclude = "basket")
public class BasketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Basket basket;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Product product;
    private Integer quantity;



    public BasketEntity(Product product, Integer quantity,Basket basket) {
        this.product = product;
        this.quantity = quantity;
        this.basket = basket;
    }
}
