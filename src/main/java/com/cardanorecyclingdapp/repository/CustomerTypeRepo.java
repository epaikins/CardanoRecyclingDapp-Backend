package com.cardanorecyclingdapp.repository;

import com.cardanorecyclingdapp.entity.CustomerType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerTypeRepo extends PagingAndSortingRepository<CustomerType, Long>{
    Page<CustomerType> findAllByIsActive(Boolean isActive, PageRequest pageRequest);
    List<CustomerType> findAllByIsActive(Boolean isActive);
}
