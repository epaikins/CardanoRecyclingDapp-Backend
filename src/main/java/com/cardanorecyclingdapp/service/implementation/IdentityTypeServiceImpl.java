package com.cardanorecyclingdapp.service.implementation;

import com.cardanorecyclingdapp.entity.CustomerType;
import com.cardanorecyclingdapp.entity.IdentityType;
import com.cardanorecyclingdapp.repository.IdentityTypeRepo;
import com.cardanorecyclingdapp.service.IdentityTypeService;
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
public class IdentityTypeServiceImpl implements IdentityTypeService {
    private final IdentityTypeRepo identityTypeRepo;
    @Override
    public IdentityType saveIdentityType(IdentityType identityType) {
        return identityTypeRepo.save(identityType);
    }

    @Override
    public IdentityType getIdentityType(Long id) {
        return identityTypeRepo.findById(id).orElse(null);
    }

    @Override
    public Page<IdentityType> getIdentityTypes(boolean isActive, int page, int size) {
        return identityTypeRepo.findAllByIsActive(isActive, PageRequest.of(page, size));
    }

    @Override
    public List<IdentityType> getIdentityTypes(Boolean isActive) {
        return identityTypeRepo.findAllByIsActive(isActive);
    }

    @Override
    public IdentityType editIdentityType(Long id, IdentityType identityType) {
        IdentityType updateIdentityType = identityTypeRepo.findById(id).orElse(null);
        assert updateIdentityType != null;
        updateIdentityType.setName(identityType.getName());
        return identityTypeRepo.save(updateIdentityType);
    }
}
