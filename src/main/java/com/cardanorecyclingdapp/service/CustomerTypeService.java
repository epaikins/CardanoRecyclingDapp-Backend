package com.cardanorecyclingdapp.service;

import com.cardanorecyclingdapp.entity.CustomerType;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerTypeService {
    public CustomerType saveCustomerType(CustomerType customerType);
    CustomerType getCustomerType(Long id);
    Page<CustomerType> getCustomerTypes(boolean isActive, int page, int size);
    List<CustomerType> getCustomerTypes(Boolean isActive);
    CustomerType editCustomerType(Long id, CustomerType customerType) ;
}
