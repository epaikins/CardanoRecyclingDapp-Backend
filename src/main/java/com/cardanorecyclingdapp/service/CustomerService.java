package com.cardanorecyclingdapp.service;

import com.cardanorecyclingdapp.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface CustomerService {
    Map<Object, Object> saveCustomer(MultipartFile file, String json);
    Map<Object, Object> signinCustomer(String email, String password);
    Customer getCustomer(Long id);
    Page<Customer> getCustomers(boolean isActive, int page, int size);
    List<Customer> getCustomers(Boolean isActive);
    Customer editCustomer(Long id, Customer customer);
}
