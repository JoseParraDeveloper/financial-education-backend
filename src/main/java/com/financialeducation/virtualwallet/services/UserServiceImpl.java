package com.financialeducation.virtualwallet.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.financialeducation.virtualwallet.dto.UserPersistentObjectDto;
import com.financialeducation.virtualwallet.entities.User;
import com.financialeducation.virtualwallet.exceptions.BadRequestException;
import com.financialeducation.virtualwallet.exceptions.ResourceNotFoundException;
import com.financialeducation.virtualwallet.repositories.IUserRepository;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	@Qualifier("modelMapperUser")
	private ModelMapper modelMapper;

	@Override
	public UserPersistentObjectDto createUser(UserPersistentObjectDto userPersistentObjectDto) {
		User newUser = modelMapper.map(userPersistentObjectDto, User.class);
		newUser = userRepository.save(newUser);
		return modelMapper.map(newUser, UserPersistentObjectDto.class);
	}

	@Override
	public List<UserPersistentObjectDto> listAllUsers() {
		List<User> listUser = userRepository.findAll();
		return listUser.stream().map(user -> modelMapper.map(user, UserPersistentObjectDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Page<UserPersistentObjectDto> pageUsers(Pageable pageable) {
		Page<User> pageUser = userRepository.findAll(pageable);
		List<UserPersistentObjectDto> listUsersDto = pageUser.stream()
				.map(user -> modelMapper.map(user, UserPersistentObjectDto.class)).collect(Collectors.toList());
		return new PageImpl<>(listUsersDto);

	}

	@Override
	public UserPersistentObjectDto getUserById(Long idUser) throws ResourceNotFoundException {
		Optional<User> optionalUser = userRepository.findById(idUser);
		return modelMapper.map(
				optionalUser.orElseThrow(() -> new ResourceNotFoundException("User", "ID", idUser.toString())),
				UserPersistentObjectDto.class);

	}

	@Override
	public UserPersistentObjectDto updateUser(UserPersistentObjectDto userPersistentObjectDto)
			throws ResourceNotFoundException, BadRequestException {
		Long idUserUpdate = userPersistentObjectDto.getId();
		if (idUserUpdate != null) {
			Optional<User> optionalUser = userRepository.findById(idUserUpdate);
			if (!optionalUser.isPresent()) {
				throw new ResourceNotFoundException("User", "ID", idUserUpdate.toString());
			}
		} else {
			throw new BadRequestException("id " + idUserUpdate);
		}
		return this.createUser(userPersistentObjectDto);

	}

	@Override
	public void deleteUserById(Long idUser) throws ResourceNotFoundException {
		Optional<User> optionalUser = userRepository.findById(idUser);
		optionalUser.ifPresentOrElse(user -> userRepository.deleteById(user.getId()), () -> {
			throw new ResourceNotFoundException("User", "ID", idUser.toString());
		});
	}

	@Override
	public UserPersistentObjectDto findByUsername(String username) {

		return null;
	}

}
