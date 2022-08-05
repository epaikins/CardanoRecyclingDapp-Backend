package com.cardanorecyclingdapp.dao;

import lombok.Data;

@Data
public class CustomerDAO {
    private String name;
    private Long identityTypeId;
    private Long customerTypeId;
    private String identityNumber;
    private String digitalAddress;
    private String email;
    private String phonenumber;
    private String password;
    private String confirmPassword;
}
