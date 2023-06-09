package telran.java47.post.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import telran.java47.post.dto.PostDto;
import telran.java47.post.model.Post;


public interface PostRepository extends MongoRepository<Post, String> {
	@Query("{ 'dateCreated': {$gte: ?0, $lt: ?1} }")
	Iterable<PostDto> findPostsByPeriod(LocalDate dateFrom, LocalDate dateTo);

	Iterable<PostDto> findByAuthorIgnoreCase(String author);
	
	Iterable<PostDto> findByTagsInIgnoreCase(List<String> tags);

}
