package com.cardanorecyclingdapp.service;

import com.cardanorecyclingdapp.entity.Role;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {
    Role saveRole(Role role);
    Role getRole(Long id);
    Page<Role> getRoles(boolean isActive, int page, int size);
    List<Role> getRoles(Boolean isActive);
    Role editRole(Long id, Role role);
}
