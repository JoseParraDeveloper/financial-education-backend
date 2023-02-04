package com.financialeducation.virtualwallet.utiles;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.financialeducation.virtualwallet.dto.RolePersistentObjectDto;
import com.financialeducation.virtualwallet.dto.UserPersistentObjectDto;
import com.financialeducation.virtualwallet.entities.Role;
import com.financialeducation.virtualwallet.entities.User;
import com.financialeducation.virtualwallet.enums.RoleEnum;
import com.financialeducation.virtualwallet.exceptions.ResourceNotFoundException;
import com.financialeducation.virtualwallet.services.IRoleService;
import com.financialeducation.virtualwallet.services.IUserService;

@Component
public class CreateRolesUserAdmin implements CommandLineRunner {

	@Autowired
	private IRoleService roleService;
	@Autowired
	private IUserService userService;
	@Autowired
	@Qualifier("modelMapper")
	private ModelMapper modelMapper;
	@Autowired
	@Qualifier("modelMapperUser")
	private ModelMapper modelMapperUser;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		try {
			RolePersistentObjectDto roleAdminDB = roleService.getRoleById(RoleEnum.ROLE_ADMIN.getId());
		} catch (ResourceNotFoundException resourceNotFoundException) {
			Role roleAdmin = new Role();
			roleAdmin.setRoleEnum(RoleEnum.ROLE_ADMIN);
			roleAdmin.setDescription("ROLE ADMIN:");
			RolePersistentObjectDto roleAdminDto = modelMapper.map(roleAdmin, RolePersistentObjectDto.class);
			roleService.createRole(roleAdminDto);
		}
		try {
			RolePersistentObjectDto roleUserDB = roleService.getRoleById(RoleEnum.ROLE_USER.getId());
		} catch (ResourceNotFoundException resourceNotFoundException) {
			Role roleUser = new Role();
			roleUser.setRoleEnum(RoleEnum.ROLE_USER);
			roleUser.setDescription("ROLE USER:");
			RolePersistentObjectDto roleUserDto = modelMapper.map(roleUser, RolePersistentObjectDto.class);
			roleService.createRole(roleUserDto);
		}
		try {
			RolePersistentObjectDto roleGuestDB = roleService.getRoleById(RoleEnum.ROLE_GUEST.getId());
		} catch (ResourceNotFoundException resourceNotFoundException) {
			Role roleGuest = new Role();
			roleGuest.setRoleEnum(RoleEnum.ROLE_GUEST);
			roleGuest.setDescription("ROLE INVITADO:");
			RolePersistentObjectDto roleGuestDto = modelMapper.map(roleGuest, RolePersistentObjectDto.class);
			roleService.createRole(roleGuestDto);
		}
		try {
			UserPersistentObjectDto userAdminDB = userService.getUserById(User.ID_USER_ADMIN);
		} catch (ResourceNotFoundException resourceNotFoundException) {
			User userAdmin = new User();
			userAdmin.setName("Administrador de la aplicación");
			userAdmin.setEmail("financial.education.and.virtual.wallet@fevw.com");
			userAdmin.setTelephone("+58 412 8243338");
			userAdmin.setSurname("Administrador de la aplicación");
			userAdmin.setUsername("AdminFEVW");
			userAdmin.setPassword(passwordEncoder.encode("admin"));
			userAdmin.setEnabled(true);
			Set<Role> roles = new HashSet<>();
			Role role = modelMapper.map(roleService.getRoleById(1L), Role.class);
			roles.add(role);
			userAdmin.setRoles(roles);
			UserPersistentObjectDto userDto = modelMapperUser.map(userAdmin, UserPersistentObjectDto.class);
			userService.createUser(userDto);
		}
	}

}
