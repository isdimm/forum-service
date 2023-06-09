package telran.java47.user.dto;

import java.util.Set;

import lombok.Getter;

@Getter
public class AddRoleDto {
	String login;
	Set<String> roles;

}
