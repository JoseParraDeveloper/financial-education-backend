package com.financialeducation.virtualwallet.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.financialeducation.virtualwallet.dto.UserPersistentObjectDto;
import com.financialeducation.virtualwallet.exceptions.BadRequestException;
import com.financialeducation.virtualwallet.exceptions.ResourceNotFoundException;

public interface IUserService {

	public List<UserPersistentObjectDto> listAllUsers();

	public Page<UserPersistentObjectDto> pageUsers(Pageable pageable);

	public UserPersistentObjectDto getUserById(Long idUser) throws ResourceNotFoundException;

	public UserPersistentObjectDto updateUser(UserPersistentObjectDto userPersistentObjectDto)
			throws ResourceNotFoundException, BadRequestException;

	public UserPersistentObjectDto createUser(UserPersistentObjectDto userPersistentObjectDto);

	public void deleteUserById(Long idUser) throws ResourceNotFoundException;

	public UserPersistentObjectDto findByUsername(String username);

}
