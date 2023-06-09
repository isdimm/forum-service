package telran.java47.user.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import telran.java47.user.dao.UserRepository;
import telran.java47.user.dto.AddRoleDto;
import telran.java47.user.dto.RegisterUserDto;
import telran.java47.user.dto.UpdateUserDto;
import telran.java47.user.dto.UserDto;
import telran.java47.user.model.User;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	final ModelMapper modelMapperlMapper;
	final UserRepository userRepository;

	@Override
	public UserDto userRegister(RegisterUserDto registerUserDto) {
		if (userRepository.findByLogin(registerUserDto.getLogin()) != null) {
			return null;
		}
		User newUser = new User(registerUserDto.getLogin(), registerUserDto.getPassword(),
				registerUserDto.getFirstName(), registerUserDto.getLastName());
		userRepository.save(newUser);
		return modelMapperlMapper.map(newUser, UserDto.class);
	}

	@Override
	public UserDto userLogin(String authorizationHeader) {
		String[] values = userAuthorizationHeader(authorizationHeader);
		String login = values[0];
		String password = values[1];
		User user = userRepository.findByLogin(login);
		if (user.matchPassword(password)) {
			return modelMapperlMapper.map(user, UserDto.class);
		}

		return null;
	}

	@Override
	public UserDto deleteUser(String userName, String authorizationHeader) {
		String[] values = userAuthorizationHeader(authorizationHeader);
		String login = values[0];
		String password = values[1];
		if (!userName.equals(login)) {
			return null;
		}
		User user = userRepository.findByLogin(login);
		if (user.matchPassword(password)) {
			userRepository.delete(user);
			return modelMapperlMapper.map(user, UserDto.class);
		}

		return null;
	}

	@Override
	public UserDto updateUser(String userName, UpdateUserDto updateUserDto, String authorizationHeader) {
		String[] values = userAuthorizationHeader(authorizationHeader);
		String login = values[0];
		String password = values[1];
		if (!userName.equals(login)) {
			return null;
		}
		User user = userRepository.findByLogin(login);
		if (user.matchPassword(password)) {
			user.setFirstName(updateUserDto.getFirstName());
			user.setLastName(updateUserDto.getLastName());
			userRepository.save(user);
			return modelMapperlMapper.map(user, UserDto.class);
		}

		return null;
	}

	@Override
	public AddRoleDto addRole(String userName, String userRole, String authorizationHeader) {
		String[] values = userAuthorizationHeader(authorizationHeader);
		String login = values[0];
		String password = values[1];
		if (!userName.equals(login)) {
			return null;
		}
		User user = userRepository.findByLogin(login);
		if (user.matchPassword(password)) {
			user.getRoles().add(userRole.toUpperCase());
			userRepository.save(user);
			return modelMapperlMapper.map(user, AddRoleDto.class);
		}

		return null;
	}
	
	@Override
	public AddRoleDto removeRole(String userName, String userRole, String authorizationHeader) {
		String[] values = userAuthorizationHeader(authorizationHeader);
		String login = values[0];
		String password = values[1];
		if (!userName.equals(login)) {
			return null;
		}
		User user = userRepository.findByLogin(login);
		if (user.matchPassword(password)) {
			user.getRoles().remove(userRole.toUpperCase());
			userRepository.save(user);
			return modelMapperlMapper.map(user, AddRoleDto.class);
		}

		return null;
	}
	
	@Override
	public boolean changeUserPassword(String authorizationHeader, String newPassword) {
		String[] values = userAuthorizationHeader(authorizationHeader);
		String login = values[0];
		String password = values[1];
		
		User user = userRepository.findByLogin(login);
		if (user.matchPassword(password)) {
			user.setPassword(newPassword);
			userRepository.save(user);
			return true;
		}

		return false;
	}
	
	private String[] userAuthorizationHeader(String authorizationHeader) {
		String base64Credentials = authorizationHeader.substring("Basic".length()).trim();
		byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
		String credentials = new String(credDecoded, StandardCharsets.UTF_8);
		return credentials.split(":", 2);
	}


}
