package com.post.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.post.entity.Post;
import com.post.entity.User;
import com.post.entity.UserFollower;
import com.post.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
   

    @Autowired
    private  UserService socialMediaService;

    @PostMapping("/create")
	public User createUser(@RequestBody User user) {
		return socialMediaService.createUser(user);
	}
	@PostMapping("/{userId}/create/post")
	public Post createPost(@PathVariable Long userId, @RequestBody Post post) {
		return socialMediaService.createPost(userId, post);
	}

	@DeleteMapping("/deletePost/{postId}")
	public void deletePost(@PathVariable Long postId) {
		socialMediaService.deletePost(postId);
	}

	@PostMapping("/{userId}/follow/{followerId}")
	public UserFollower follow(@PathVariable Long userId,@PathVariable Long followerId) {
	return socialMediaService.follow(userId,followerId);
	}

	@PostMapping("/{userId}/unfollow/{followerId}")
	public void unfollow(@PathVariable Long userId,@PathVariable Long followerId) {
	socialMediaService.unfollow(userId,followerId);
	}

	@GetMapping("/{userId}/newsFeed")
	public List<Post> newsFeed(@PathVariable Long userId){
		return socialMediaService.getNewsFeed(userId);
	}
	
	@GetMapping("/{userId}/newsFeed/paginated")
	public List<Post> getNewsFeedPaginated(@PathVariable Long userId,@RequestParam(defaultValue = "1")  Integer pageNumber){
		return socialMediaService.getNewsFeedPaginated(userId,pageNumber);
		
	}
}

