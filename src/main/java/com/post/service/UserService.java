package com.post.service;

import java.util.List;

import com.post.entity.Post;
import com.post.entity.User;
import com.post.entity.UserFollower;

public interface UserService {

	User createUser(User user);

	Post createPost(Long userId, Post post);

	void deletePost(Long postId);

	UserFollower follow(Long userId, Long followerId);

	void unfollow(Long userId, Long followerId);

	List<Post> getNewsFeed(Long userId);

	List<Post> getNewsFeedPaginated(Long userId, Integer pageNumber);
}
