package com.cardanorecyclingdapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String name;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "identitytype_id", referencedColumnName = "id")
    public IdentityType identityType;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customertype_id", referencedColumnName = "id")
    public CustomerType customerType;
    public String identityNumber;
    public String digitalAddress;
    public String email;
    public Integer phonenumber;
    public String imageUrl;
    public String password;

}
