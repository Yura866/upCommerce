package com.bcn.startupers.upcommerce.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.bcn.startupers.upcommerce.model.Role;
import com.bcn.startupers.upcommerce.model.RoleName;

@Service
public interface RolService {	
  Optional<Role> getByRolName(RoleName rolName);
  boolean existsRolName(RoleName rolName);

}
