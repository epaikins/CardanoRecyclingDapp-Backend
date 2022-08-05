package com.cardanorecyclingdapp.repository;

import com.cardanorecyclingdapp.entity.IdentityType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdentityTypeRepo extends PagingAndSortingRepository<IdentityType, Long> {
    Page<IdentityType> findAllByIsActive(Boolean isActive, PageRequest pageRequest);
    List<IdentityType> findAllByIsActive(Boolean isActive);
}
