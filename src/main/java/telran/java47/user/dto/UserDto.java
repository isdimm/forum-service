package telran.java47.user.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
	String login;
	String firstName;
	String lastName;
	Set<String> roles;
}
