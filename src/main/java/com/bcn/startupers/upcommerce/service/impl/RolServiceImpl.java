package com.bcn.startupers.upcommerce.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bcn.startupers.upcommerce.model.Role;
import com.bcn.startupers.upcommerce.model.RoleName;
import com.bcn.startupers.upcommerce.repository.RoleRepository;
import com.bcn.startupers.upcommerce.service.RolService;

@Service
@Transactional
public class RolServiceImpl implements RolService{
	
    @Autowired
    RoleRepository rolRepository;

	@Override
	public Optional<Role> getByRolName(RoleName rolName) {
		return rolRepository.findByRoleName(rolName);		
	}

	@Override
	public boolean existsRolName(RoleName rolName) {
		return rolRepository.existsByRoleName(rolName);
	}
	
	 public void save(Role rol){
	     rolRepository.save(rol);
	 }
}
