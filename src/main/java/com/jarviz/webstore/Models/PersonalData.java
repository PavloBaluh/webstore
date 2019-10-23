package com.jarviz.webstore.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString(exclude = "user")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonalData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String picture;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Address address;
    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, mappedBy = "personalData")
    private User user;
}
