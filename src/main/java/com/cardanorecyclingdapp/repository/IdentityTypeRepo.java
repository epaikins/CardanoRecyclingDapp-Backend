package com.cardanorecyclingdapp.repository;

import com.cardanorecyclingdapp.entity.IdentityType;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentityTypeRepo extends PagingAndSortingRepository<IdentityType, Long> {
}
