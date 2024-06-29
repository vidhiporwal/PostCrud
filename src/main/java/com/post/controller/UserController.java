package com.post.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.post.entity.Post;
import com.post.entity.User;
import com.post.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
   

    @Autowired
    private  UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }


    @PostMapping("/{userId}/posts")
    public ResponseEntity<Post> createPost(
            @PathVariable Long userId,
            @RequestBody Post postRequest) {

        // Call UserService to create a post for the given userId
        Post createdPost = userService.createPost(userId, postRequest.getContent());

        // Return a response entity with the created post and HTTP status 201 (Created)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }


    @DeleteMapping("/posts/{postId}")
    public void deletePost(@PathVariable Long postId) {
        userService.deletePost(postId);
    }

    @PostMapping("/{userId}/follow/{followUserId}")
    public ResponseEntity<?> followUser(@PathVariable Long userId, @PathVariable Long followUserId) {
        userService.followUser(userId, followUserId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/unfollow/{unfollowUserId}")
    public ResponseEntity<?> unfollowUser(@PathVariable Long userId, @PathVariable Long unfollowUserId) {
        userService.unfollowUser(userId, unfollowUserId);
        return ResponseEntity.ok().build();
    }
    
    
    @GetMapping("/{userId}/newsfeed")
    public ResponseEntity<List<Post>> getNewsFeed(@PathVariable Long userId) {
        List<Post> newsFeed = userService.getNewsFeed(userId);
        return ResponseEntity.ok().body(newsFeed);
    }

    @GetMapping("/{userId}/newsfeed/paginated")
    public ResponseEntity<List<Post>> getNewsFeedPaginated(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") Integer pageNumber) {
        List<Post> newsFeed = userService.getNewsFeedPaginated(userId, pageNumber);
        return ResponseEntity.ok().body(newsFeed);
    }

}

