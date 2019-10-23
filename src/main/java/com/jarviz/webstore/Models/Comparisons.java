package com.jarviz.webstore.Models;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Comparisons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Product> compare = new ArrayList<>();
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private User user;

    public Comparisons(User user) {
        this.user = user;
    }

    public Comparisons() {
    }
}
