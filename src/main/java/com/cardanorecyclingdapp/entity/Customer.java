package com.cardanorecyclingdapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "identitytype_id", referencedColumnName = "id")
    private IdentityType identityType;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customertype_id", referencedColumnName = "id")
    private CustomerType customerType;
    @ManyToMany(fetch= FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "role_customer",
            joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",
                    referencedColumnName = "id"))
    private Collection<Role> roles = new ArrayList<Role>();
    private String identityNumber;
    private String digitalAddress;
    private String email;
    private String phonenumber;
    private String imageUrl;
    private String password;

}
