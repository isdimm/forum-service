package telran.java47.user.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java47.user.dto.AddRoleDto;
import telran.java47.user.dto.RegisterUserDto;
import telran.java47.user.dto.UpdateUserDto;
import telran.java47.user.dto.UserDto;
import telran.java47.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class UserController {
	final UserService userService;

	@PostMapping("/register")
	public UserDto userRegister(@RequestBody RegisterUserDto registerUserDto) {
		return userService.userRegister(registerUserDto);
	}

	@PostMapping("/login")
	public UserDto userLogin(@RequestHeader("Authorization") String authorizationHeader) {
		return userService.userLogin(authorizationHeader);
	}

	@DeleteMapping("/user/{user}")
	public UserDto deleteUser(@PathVariable String userName,
			@RequestHeader("Authorization") String authorizationHeader) {
		return userService.deleteUser(userName, authorizationHeader);
	}

	@PutMapping("/user/{user}")
	public UserDto updateUser(@PathVariable String userName, @RequestBody UpdateUserDto updateUserDto,
			@RequestHeader("Authorization") String authorizationHeader) {
		return userService.updateUser(userName, updateUserDto, authorizationHeader);
	}

	@PutMapping("/user/{user}/role/{role}")
	public AddRoleDto addRole(@PathVariable String user, @PathVariable String role,
			@RequestHeader("Authorization") String authorizationHeader) {
		return userService.addRole(user, role, authorizationHeader);
	}

	@DeleteMapping("/user/{user}/role/{role}")
	public AddRoleDto removeRole(@PathVariable String user, @PathVariable String role,
			@RequestHeader("Authorization") String authorizationHeader) {
		return userService.removeRole(user, role, authorizationHeader);
	}
	
	@PutMapping("/user/password")
	public void changeUserPassword(@RequestHeader("Authorization") String authorizationHeader, @RequestHeader("X-Password") String newPassword) {
		userService.changeUserPassword(authorizationHeader, newPassword);
		
	}

}
