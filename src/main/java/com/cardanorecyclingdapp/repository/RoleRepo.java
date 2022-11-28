package com.cardanorecyclingdapp.repository;

import com.cardanorecyclingdapp.entity.IdentityType;
import com.cardanorecyclingdapp.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RoleRepo extends PagingAndSortingRepository<Role, Long> {
    Role findByName(String name);
    Page<Role> findByNameContaining(String roleName, PageRequest pageRequest);
    Page<Role> findAllByIsActive(Boolean isActive, PageRequest pageRequest);
    List<Role> findAllByIsActive(Boolean isActive);
}
