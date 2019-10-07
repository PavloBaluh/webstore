package com.jarviz.webstore.Models;
import com.jarviz.webstore.tools.OrderStatus;
import com.jarviz.webstore.tools.PayType;
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
@ToString(exclude = {"order","entities"})
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
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "orderEntity")
    private List<BasketEntity> entities = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Orders order;
}

