package com.cardanorecyclingdapp.service.implementation;

import com.cardanorecyclingdapp.entity.CustomerType;
import com.cardanorecyclingdapp.repository.CustomerTypeRepo;
import com.cardanorecyclingdapp.service.CustomerTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomerTypeServiceImpl implements CustomerTypeService {
    private CustomerTypeRepo customerTypeRepo;
    @Override
    public CustomerType saveCustomerType(String name) {
        return null;
    }

    @Override
    public CustomerType getCustomerType(String name) {
        return null;
    }

    @Override
    public Page<CustomerType> getCustomerTypes(String name, int page, int size) {
        return null;
    }

    @Override
    public List<CustomerType> getCustomerTypes(Boolean isActive) {
        return null;
    }

    @Override
    public CustomerType editCustomerType(String name, CustomerType customerType) {
        return null;
    }
}
