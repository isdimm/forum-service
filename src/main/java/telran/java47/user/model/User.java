package telran.java47.user.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(of = "id")
@Document(collection = "users")
public class User {
	@Getter
	String id;
	@Getter
	String login;
	String password;
	@Getter
	@Setter
	String firstName;
	@Getter
	@Setter
	String lastName;
	@Getter
	Set<String> roles;
	@Getter
	LocalDateTime dateCreated;

	public User() {
		dateCreated = LocalDateTime.now();
		roles = new HashSet<>();
	}

	public User(String login, String password, String firstName, String lastName) {
		this();
		this.login = login;
		this.password = passwordEncode(password);
		this.firstName = firstName;
		this.lastName = lastName;
		this.roles.add("USER");
	}

	public static String passwordEncode(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(password);
	}

	public boolean matchPassword(String passwordToMath) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(passwordToMath, this.password);

	}

	public void setPassword(String password) {
		this.password = passwordEncode(password);
	}
	
	

}
