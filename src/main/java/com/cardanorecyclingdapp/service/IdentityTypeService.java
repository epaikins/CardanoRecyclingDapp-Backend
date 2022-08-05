package com.cardanorecyclingdapp.service;

import com.cardanorecyclingdapp.entity.IdentityType;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IdentityTypeService {
    public IdentityType saveIdentityType(IdentityType identityType);
    IdentityType getIdentityType(Long id);
    Page<IdentityType> getIdentityTypes(boolean isActive, int page, int size);
    List<IdentityType> getIdentityTypes(Boolean isActive);
    IdentityType editIdentityType(Long id, IdentityType identityType);
}
