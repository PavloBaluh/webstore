package com.jarviz.webstore.Models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jarviz.webstore.Enums.OrderStatus;
import com.jarviz.webstore.Enums.PayType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@ToString(exclude = {"order"})
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private PayType payType;
    private LocalDateTime localDateTime = LocalDateTime.now();
    private Boolean payed;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Orders order;
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "orderEntity")
    private List<OrderProduct> orderProducts = new ArrayList<>();
}

