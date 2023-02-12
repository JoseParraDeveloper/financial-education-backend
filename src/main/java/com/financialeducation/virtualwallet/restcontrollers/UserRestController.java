package com.financialeducation.virtualwallet.restcontrollers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
import com.financialeducation.virtualwallet.enums.RoleEnum;
import com.financialeducation.virtualwallet.exceptions.BadRequestException;
import com.financialeducation.virtualwallet.exceptions.ResourceNotFoundException;
import com.financialeducation.virtualwallet.services.IRoleService;
import com.financialeducation.virtualwallet.services.IUserService;

@RestController
@RequestMapping(value = "/api/user/v1")
@CrossOrigin
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
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserPersistentObjectDto userPersistentObjectDto,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			StringBuilder messageErrors = new StringBuilder(
					"You can't register. It has " + bindingResult.getFieldErrorCount() + " errors. ");
			List<ObjectError> listErrors = bindingResult.getAllErrors();
			int numberMessage = 1;
			for (ObjectError objectError : listErrors) {
				if (numberMessage < bindingResult.getFieldErrorCount()) {
					messageErrors.append(objectError.getDefaultMessage() + ", ");
				} else {
					messageErrors.append(objectError.getDefaultMessage() + ".");
				}
				numberMessage += 1;
			}
			return new ResponseEntity<>(messageErrors, HttpStatus.BAD_REQUEST);
		}
		RolePersistentObjectDto roleGuest = roleService.getRoleById(RoleEnum.ROLE_GUEST.getId());
		Set<RolePersistentObjectDto> roleSet = new HashSet<>();
		roleSet.add(roleGuest);
		userPersistentObjectDto.setRoles(roleSet);
		try {
			userPersistentObjectDto = userService.createUser(userPersistentObjectDto);
			return ResponseEntity.ok(userPersistentObjectDto);
		} catch (BadRequestException badRequestException) {
			return new ResponseEntity<>(badRequestException.getMessage(), HttpStatus.BAD_REQUEST);
		}

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

}
