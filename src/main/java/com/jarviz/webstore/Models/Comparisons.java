package com.jarviz.webstore.Models;

import lombok.Data;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Comparisons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Product> compare = new HashSet<>();
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private User user;

    public Comparisons(User user) {
        this.user = user;
    }

    public Comparisons() {
    }
}
