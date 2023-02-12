package com.financialeducation.virtualwallet.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.financialeducation.virtualwallet.dto.RolePersistentObjectDto;
import com.financialeducation.virtualwallet.entities.Role;
import com.financialeducation.virtualwallet.enums.RoleEnum;
import com.financialeducation.virtualwallet.exceptions.BadRequestException;
import com.financialeducation.virtualwallet.exceptions.ResourceNotFoundException;
import com.financialeducation.virtualwallet.repositories.IRoleRepository;

@Service
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private IRoleRepository roleRepository;
	@Autowired
	@Qualifier("modelMapper")
	private ModelMapper modelMapper;

	@Override
	public List<RolePersistentObjectDto> listAllRole() {
		List<Role> listRoles = roleRepository.findAll();
		return listRoles.stream().map(role -> modelMapper.map(role, RolePersistentObjectDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Page<RolePersistentObjectDto> pageRole(Pageable pageable) {
		return null;
	}

	@Override
	public RolePersistentObjectDto getRoleById(Long idRole) throws ResourceNotFoundException {
		Optional<Role> optionalRole = roleRepository.findById(idRole);
		return modelMapper.map(
				optionalRole.orElseThrow(() -> new ResourceNotFoundException("Role", "ID", idRole.toString())),
				RolePersistentObjectDto.class);
	}

	@Override
	public RolePersistentObjectDto createRole(RolePersistentObjectDto rolePersistentObjectDto) {
		Role newRole = modelMapper.map(rolePersistentObjectDto, Role.class);
		newRole = roleRepository.save(newRole);
		return modelMapper.map(newRole, RolePersistentObjectDto.class);
	}

	@Override
	public RolePersistentObjectDto updateRole(RolePersistentObjectDto rolePersistentObjectDto)
			throws ResourceNotFoundException, BadRequestException {
		Long idRoleUpdate = rolePersistentObjectDto.getId();
		if (idRoleUpdate != null) {
			Optional<Role> optionalRole = roleRepository.findById(idRoleUpdate);
			if (!optionalRole.isPresent()) {
				throw new ResourceNotFoundException("Role", "ID", idRoleUpdate.toString());
			}
		} else {
			throw new BadRequestException("id " + idRoleUpdate);
		}
		return this.createRole(rolePersistentObjectDto);
	}

	@Override
	public void deleteRoleById(Long idRole) throws ResourceNotFoundException {
		Optional<Role> optionalRole = roleRepository.findById(idRole);
		optionalRole.ifPresentOrElse(role -> roleRepository.deleteById(role.getId()), () -> {
			throw new ResourceNotFoundException("Role", "ID", idRole.toString());
		});
	}

	@Override
	public RolePersistentObjectDto findByRoleEnum(RoleEnum roleEnum) {
		Optional<Role> optionalRole = roleRepository.findByRoleEnum(roleEnum);
		return modelMapper.map(
				optionalRole.orElseThrow(
						() -> new ResourceNotFoundException("Role", "Role Enum", roleEnum.name().toUpperCase())),
				RolePersistentObjectDto.class);
	}

}
