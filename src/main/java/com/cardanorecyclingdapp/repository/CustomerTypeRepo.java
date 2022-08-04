package com.cardanorecyclingdapp.repository;

import com.cardanorecyclingdapp.entity.CustomerType;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerTypeRepo extends PagingAndSortingRepository<CustomerType, Long>{
}
