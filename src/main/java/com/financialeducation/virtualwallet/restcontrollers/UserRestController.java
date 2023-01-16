package com.financialeducation.virtualwallet.restcontrollers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financialeducation.virtualwallet.dto.RolePersistentObjectDto;
import com.financialeducation.virtualwallet.dto.UserPersistentObjectDto;
import com.financialeducation.virtualwallet.dto.UserViewDto;
import com.financialeducation.virtualwallet.entities.Role;
import com.financialeducation.virtualwallet.exceptions.BadRequestException;
import com.financialeducation.virtualwallet.exceptions.ResourceNotFoundException;
import com.financialeducation.virtualwallet.services.IRoleService;
import com.financialeducation.virtualwallet.services.IUserService;

@RestController
@RequestMapping(value = "/api/user/v1")
@CrossOrigin(origins = "http://localhost:4200/")
public class UserRestController {

	@Autowired
	private IUserService userService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	@Qualifier("modelMapperUser")
	private ModelMapper modelMapper;
	@Value("${sizePageUser}")
	private int sizePageUser;

	@GetMapping(value = "/all")
	public ResponseEntity<?> getAllUsers() {
		List<UserPersistentObjectDto> listUsers = userService.listAllUsers();
		if (listUsers.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("There are no users.");
		}
		return ResponseEntity.ok(listUsers);
	}

	@GetMapping(value = { "/page/{page}", "/page" })
	public ResponseEntity<?> getUsersByPage(@PathVariable(name = "page", required = false) Integer page) {
		if (page == null) {
			page = 1;
		}
		Page<UserPersistentObjectDto> pageUsers = userService.pageUsers(PageRequest.of(page - 1, sizePageUser));
		if (pageUsers.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("There are no users in the page: " + page);
		}
		return ResponseEntity.ok(pageUsers);
	}

	@GetMapping(value = "/{userID}")
	public ResponseEntity<?> getUserById(@PathVariable("userID") Long idUser) {
		try {
			UserPersistentObjectDto userDto = userService.getUserById(idUser);
			return ResponseEntity.ok(userDto);
		} catch (ResourceNotFoundException resourceNotFoundException) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resourceNotFoundException.getMessage());
		}
	}

	@PostMapping(value = "/register")
	public ResponseEntity<UserPersistentObjectDto> registerUser(
			@RequestBody UserPersistentObjectDto userPersistentObjectDto) {
		RolePersistentObjectDto roleInvited = roleService.getRoleById(Role.getROLE_INVITADO());
		Set<RolePersistentObjectDto> roleSet = new HashSet<>();
		roleSet.add(roleInvited);
		userPersistentObjectDto.setRoles(roleSet);
		return ResponseEntity.ok(userService.createUser(userPersistentObjectDto));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long idUser) {
		try {
			userService.deleteUserById(idUser);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (ResourceNotFoundException resourceNotFoundException) {
			return new ResponseEntity<>(resourceNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateUser(@RequestBody UserPersistentObjectDto userPersistentObjectDto) {
		try {
			UserPersistentObjectDto updateUserDto = userService.updateUser(userPersistentObjectDto);
			return new ResponseEntity<>(updateUserDto, HttpStatus.OK);
		} catch (ResourceNotFoundException resourceNotFoundException) {
			return new ResponseEntity<>(resourceNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
		} catch (BadRequestException badRequestException) {
			return new ResponseEntity<>(badRequestException.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/allUsers")
	public ResponseEntity<?> getAllUsersView() {
		List<UserPersistentObjectDto> listUsers = userService.listAllUsers();
		List<UserViewDto> listUserViewDto = listUsers.stream().map(user -> modelMapper.map(user, UserViewDto.class))
				.collect(Collectors.toList());
		if (listUserViewDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("There are no users.");
		}
		return ResponseEntity.ok(listUserViewDto);
	}
}
