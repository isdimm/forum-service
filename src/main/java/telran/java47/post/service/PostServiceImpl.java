package telran.java47.post.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import telran.java47.post.dao.PostRepository;
import telran.java47.post.dao.exceptions.PostNotFoundExceptions;
import telran.java47.post.dto.DatePeriodDto;
import telran.java47.post.dto.NewCommentDto;
import telran.java47.post.dto.NewPostDto;
import telran.java47.post.dto.PostDto;
import telran.java47.post.model.Comment;
import telran.java47.post.model.Post;

@Component
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
	final PostRepository postRepository;
	final ModelMapper modelMapper;

	@Override
	public PostDto addNewPost(String author, NewPostDto newPostDto) {
		if(author == null || newPostDto.getContent() == null || newPostDto.getTags() == null) return null;
		Post post = new Post(newPostDto.getTitle(), newPostDto.getContent(), author, newPostDto.getTags());
		postRepository.save(post);
		return postToPostDto(post);
	}

	@Override
	public PostDto findPostById(String id) {
		Post post = getPostById(id);
		return postToPostDto(post);
	}

	@Override
	public PostDto removePost(String id) {
		Post post = getPostById(id);
		postRepository.delete(post);
		return postToPostDto(post);
	}

	@Override
	public PostDto updatePost(String id, NewPostDto newPostDto) {
		Post post = getPostById(id);
		if(!newPostDto.getTitle().isEmpty()) {
			post.setTitle(newPostDto.getTitle());
		}
		if(!newPostDto.getContent().isEmpty()) {
			post.setContent(newPostDto.getContent());
		}
		if(!newPostDto.getTags().isEmpty()) {
			post.getTags().addAll(newPostDto.getTags());
		}
		postRepository.save(post);
		return postToPostDto(post);
	}

	@Override
	public PostDto addComment(String id, String author, NewCommentDto newCommentDto) {
		Post post = getPostById(id);
		if(newCommentDto.getMessage().isEmpty()) {
			return null;
		}
		Comment comment = new Comment(author, newCommentDto.getMessage());
		post.getComments().add(comment);
		postRepository.save(post);
		return postToPostDto(post);
	}

	@Override
	public void addLike(String id) {
		Post post = getPostById(id);
		post.addLike();
		postRepository.save(post);

	}

	@Override
	public Iterable<PostDto> findPostByAuthor(String author) {
		return postRepository.findByAuthorIgnoreCase(author);
	}

	@Override
	public Iterable<PostDto> findPostsByTags(List<String> tags) {
		return postRepository.findByTagsInIgnoreCase(tags);
	}

	@Override
	public Iterable<PostDto> findPostsByPeriod(DatePeriodDto datePeriodDto) {
		return postRepository.findPostsByPeriod(datePeriodDto.getDateFrom(), datePeriodDto.getDateTo().plusDays(1));
	}
	
	private Post getPostById(String id) {
		return postRepository.findById(id).orElseThrow(PostNotFoundExceptions::new);
		
	}
	
	private PostDto postToPostDto(Post post) {
		return modelMapper.map(post, PostDto.class);
	}

}
