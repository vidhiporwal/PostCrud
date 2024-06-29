package com.post.service;

import java.util.List;

import com.post.entity.Post;
import com.post.entity.User;

public interface UserService {
	 User createUser(String username);
	    Post createPost(Long userId, String content);
	    void deletePost(Long postId);
	    void followUser(Long userId, Long followUserId);
	    void unfollowUser(Long userId, Long unfollowUserId);
		List<Post> getNewsFeed(Long userId);
		List<Post> getNewsFeedPaginated(Long userId, Integer pageNumber);
}
