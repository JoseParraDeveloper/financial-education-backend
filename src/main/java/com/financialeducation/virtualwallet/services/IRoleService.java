package com.financialeducation.virtualwallet.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.financialeducation.virtualwallet.dto.RolePersistentObjectDto;
import com.financialeducation.virtualwallet.exceptions.BadRequestException;
import com.financialeducation.virtualwallet.exceptions.ResourceNotFoundException;

public interface IRoleService {

	public List<RolePersistentObjectDto> listAllRole();

	public Page<RolePersistentObjectDto> pageRole(Pageable pageable);

	public RolePersistentObjectDto getRoleById(Long idRole) throws ResourceNotFoundException;

	public RolePersistentObjectDto createRole(RolePersistentObjectDto rolePersistentObjectDto);

	public RolePersistentObjectDto updateRole(RolePersistentObjectDto rolePersistentObjectDto)
			throws ResourceNotFoundException, BadRequestException;

	public void deleteRoleById(Long idRole) throws ResourceNotFoundException;

}
