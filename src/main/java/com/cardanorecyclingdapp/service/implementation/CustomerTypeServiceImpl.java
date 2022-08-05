package com.cardanorecyclingdapp.service.implementation;

import com.cardanorecyclingdapp.entity.CustomerType;
import com.cardanorecyclingdapp.repository.CustomerTypeRepo;
import com.cardanorecyclingdapp.service.CustomerTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomerTypeServiceImpl implements CustomerTypeService {
    private final CustomerTypeRepo customerTypeRepo;
    @Override
    public CustomerType saveCustomerType(CustomerType customerType) {
        return customerTypeRepo.save(customerType);
    }

    @Override
    public CustomerType getCustomerType(Long id) {
        return customerTypeRepo.findById(id).orElse(null);
    }

    @Override
    public Page<CustomerType> getCustomerTypes(boolean isActive, int page, int size) {
        return customerTypeRepo.findAllByIsActive(isActive, PageRequest.of(page, size));
    }

    @Override
    public List<CustomerType> getCustomerTypes(Boolean isActive) {
        return customerTypeRepo.findAllByIsActive(isActive);
    }

    @Override
    public CustomerType editCustomerType(Long id, CustomerType customerType) {
        CustomerType updateCustomerType = customerTypeRepo.findById(id).orElse(null);
        assert updateCustomerType != null;
        updateCustomerType.setName(customerType.getName());
        return customerTypeRepo.save(updateCustomerType);
    }
}
