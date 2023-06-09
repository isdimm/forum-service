package telran.java47.user.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import telran.java47.user.dto.UserDto;
import telran.java47.user.model.User;

public interface UserRepository extends MongoRepository<User, String> {

	User findByLogin(String login);

	@Query("{ 'field1': ?0, 'field2': ?1 }")
	UserDto findByLoginAndPassword(String string, String password);

}
