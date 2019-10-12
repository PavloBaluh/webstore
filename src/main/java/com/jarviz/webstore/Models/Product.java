package com.jarviz.webstore.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@ToString(exclude = {"cart", "group","order"})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    private int availableNumber;
    private int warrantyMonths;
    private String picture;
    private float price;
    @Column(name = "addDate")
    private LocalDateTime data = LocalDateTime.now();
    @Column(name = "rating")
    private float rate;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Group group;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PropertyValue> propertyValues = new ArrayList<>();
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product")
    private List<BasketEntity> cart = new ArrayList<>();
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product")
    private List<OrderProduct> order = new ArrayList<>();
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "wishes")
    private List<User> users = new ArrayList<>();
}
