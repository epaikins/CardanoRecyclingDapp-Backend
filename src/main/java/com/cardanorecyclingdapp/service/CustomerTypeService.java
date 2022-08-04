package com.cardanorecyclingdapp.service;

import com.cardanorecyclingdapp.entity.CustomerType;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerTypeService {
    public CustomerType saveCustomerType(String name);
    CustomerType getCustomerType(String name);
    Page<CustomerType> getCustomerTypes(String name, int page, int size);
    List<CustomerType> getCustomerTypes(Boolean isActive);
    CustomerType editCustomerType(String name, CustomerType customerType);
}
