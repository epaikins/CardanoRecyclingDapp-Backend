package com.cardanorecyclingdapp.repository;

import com.cardanorecyclingdapp.entity.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends PagingAndSortingRepository<Customer, Long> {
}
