package telran.java47.user.service;

import telran.java47.user.dto.AddRoleDto;
import telran.java47.user.dto.RegisterUserDto;
import telran.java47.user.dto.UpdateUserDto;
import telran.java47.user.dto.UserDto;

public interface UserService {
	UserDto userRegister(RegisterUserDto registerUserDto);

	UserDto userLogin(String authorizationHeader);

	UserDto deleteUser(String userName, String authorizationHeader);

	UserDto updateUser(String user, UpdateUserDto updateUserDto, String authorizationHeader);

	AddRoleDto addRole(String userName, String userRole, String authorizationHeader);

	AddRoleDto removeRole(String user, String role, String authorizationHeader);

	boolean changeUserPassword(String authorizationHeader, String newPassword);


}
