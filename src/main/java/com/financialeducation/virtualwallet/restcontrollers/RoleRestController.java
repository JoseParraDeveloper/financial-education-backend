package com.financialeducation.virtualwallet.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.financialeducation.virtualwallet.exceptions.BadRequestException;
import com.financialeducation.virtualwallet.exceptions.ResourceNotFoundException;
import com.financialeducation.virtualwallet.services.IRoleService;

@RestController
@RequestMapping(value = "/api/role/v1")
@CrossOrigin(origins = "http://localhost:4200/")
public class RoleRestController {

	@Autowired
	private IRoleService roleService;

	@PostMapping(value = "/create")
	public ResponseEntity<RolePersistentObjectDto> createRole(
			@RequestBody RolePersistentObjectDto rolePersistentObjectDto) {
		return new ResponseEntity<>(roleService.createRole(rolePersistentObjectDto), HttpStatus.CREATED);
	}

	@GetMapping(value = "/all")
	public ResponseEntity<?> getAllRoles() {
		List<RolePersistentObjectDto> listAllRoles = roleService.listAllRole();
		if (listAllRoles.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("There are no roles.");
		} else {
			return new ResponseEntity<>(listAllRoles, HttpStatus.OK);
		}
	}

	@GetMapping(value = "/{roleID}")
	public ResponseEntity<?> getRoleById(@PathVariable("roleID") Long idRole) {
		try {
			RolePersistentObjectDto roleDto = roleService.getRoleById(idRole);
			return ResponseEntity.ok(roleDto);
		} catch (ResourceNotFoundException resourceNotFoundException) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resourceNotFoundException.getMessage());
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateRole(@RequestBody RolePersistentObjectDto rolePersistentObjectDto) {
		try {
			RolePersistentObjectDto updateRoleDto = roleService.updateRole(rolePersistentObjectDto);
			return new ResponseEntity<>(updateRoleDto, HttpStatus.OK);
		} catch (ResourceNotFoundException resourceNotFoundException) {
			return new ResponseEntity<>(resourceNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
		} catch (BadRequestException badRequestException) {
			return new ResponseEntity<>(badRequestException.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteRole(@PathVariable("id") Long idRole) {
		try {
			roleService.deleteRoleById(idRole);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (ResourceNotFoundException resourceNotFoundException) {
			return new ResponseEntity<>(resourceNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
