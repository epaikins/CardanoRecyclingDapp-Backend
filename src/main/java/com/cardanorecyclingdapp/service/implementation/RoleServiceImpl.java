package com.cardanorecyclingdapp.service.implementation;

import com.cardanorecyclingdapp.entity.Role;
import com.cardanorecyclingdapp.entity.Role;
import com.cardanorecyclingdapp.repository.RoleRepo;
import com.cardanorecyclingdapp.repository.RoleRepo;
import com.cardanorecyclingdapp.service.RoleService;
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
public class RoleServiceImpl implements RoleService {
    private final RoleRepo roleRepo;
    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public Role getRole(Long id) {
        return roleRepo.findById(id).orElse(null);
    }

    @Override
    public Page<Role> getRoles(boolean isActive, int page, int size) {
        return roleRepo.findAllByIsActive(isActive, PageRequest.of(page, size));
    }

    @Override
    public List<Role> getRoles(Boolean isActive) {
        return roleRepo.findAllByIsActive(isActive);
    }

    @Override
    public Role editRole(Long id, Role role) {
        Role updateRole = roleRepo.findById(id).orElse(null);
        assert updateRole != null;
        updateRole.setName(role.getName());
        return roleRepo.save(updateRole);
    }
}
