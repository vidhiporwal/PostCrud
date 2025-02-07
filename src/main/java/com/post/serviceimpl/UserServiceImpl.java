package com.post.serviceimpl;

import com.post.entity.Post;
import com.post.entity.User;
import com.post.entity.UserFollower;
import com.post.repository.PostRepository;
import com.post.repository.UserFollowerRepository;
import com.post.repository.UserRepository;
import com.post.service.UserService;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	 private  UserFollowerRepository userFollowerRepository;
	@Autowired
    private  UserRepository userRepository;
	@Autowired
    private  PostRepository postRepository;

    

	@Override
	public User createUser(User user) {
		
		User newUser = new User();
		newUser.setUsername(user.getUsername());
		newUser.setCreatedAt(System.currentTimeMillis());
		return userRepository.save(newUser);
	}

	@Override
	public Post createPost(Long userId, Post post) {
		Optional<User> Puser = userRepository.findById(userId);
		if (!Puser.isPresent()) {
			throw new RuntimeException("user does not exists");
		}

		User user = Puser.get();
		Post newpost = new Post();
		newpost.setContent(post.getContent());
		newpost.setCreatedAt(System.currentTimeMillis());
		newpost.setUser(user);

		return postRepository.save(newpost);
	}
    @Override
    public void deletePost(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            postRepository.delete(postOptional.get());
        } else {
            throw new RuntimeException("Post not found");
        }
    }

    
    @Transactional
    public UserFollower follow(Long userId, Long followUserId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<User> followUserOptional = userRepository.findById(followUserId);

        if (userOptional.isPresent() && followUserOptional.isPresent()) {
            User user = userOptional.get();
            User followUser = followUserOptional.get();

            // Check if already following
            if (userFollowerRepository.existsByUserAndFollower(user, followUser)) {
                throw new IllegalStateException("User with id " + userId + " already follows user with id " + followUserId);
            }

            UserFollower userFollower = new UserFollower();
            userFollower.setUser(user);
            userFollower.setFollower(followUser);

            return userFollowerRepository.save(userFollower);
        } else {
            throw new IllegalArgumentException("User or followUser not found");
        }
    }

    @Transactional
    public void unfollow(Long userId, Long unfollowUserId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<User> unfollowUserOptional = userRepository.findById(unfollowUserId);

        if (userOptional.isPresent() && unfollowUserOptional.isPresent()) {
            User user = userOptional.get();
            User unfollowUser = unfollowUserOptional.get();

            Optional<UserFollower> userFollowerOptional = userFollowerRepository.findByUserAndFollower(user, unfollowUser);
            if (userFollowerOptional.isPresent()) {
                userFollowerRepository.delete(userFollowerOptional.get());
            } else {
                throw new IllegalStateException("User with id " + userId + " does not follow user with id " + unfollowUserId);
            }
        } else {
            throw new IllegalArgumentException("User or unfollowUser not found");
        }
    }

    public List<Post> getNewsFeed(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User with id " + userId + " not found");
        }
        User user = userOptional.get();

        // Get the user IDs the current user is following
        List<Long> userIdsToFollow = userFollowerRepository.findByUserId(user.getId())
                .stream()
                .map(UserFollower::getFollower)
                .map(User::getId)
                .collect(Collectors.toList());
        userIdsToFollow.add(user.getId()); // Include the user's own posts

        return postRepository.findByAuthorIdsOrderByCreatedAtDesc(userIdsToFollow);
    }

    public List<Post> getNewsFeedPaginated(Long userId, Integer pageNumber) {
        int pageSize = 10; // Example: 10 posts per page
        int offset = pageNumber > 0 ? pageNumber - 1 : 0;
        Pageable pageRequest = PageRequest.of(offset, pageSize);

        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User with id " + userId + " not found");
        }
        User user = userOptional.get();

        // Get the user IDs the current user is following
        List<Long> userIdsToFollow = userFollowerRepository.findByUserId(user.getId())
                .stream()
                .map(UserFollower::getFollower)
                .map(User::getId)
                .collect(Collectors.toList());
        userIdsToFollow.add(user.getId()); // Include the user's own posts

        return postRepository.findByAuthorIdsOrderByCreatedAtDesc(userIdsToFollow, pageRequest);
    }

	

	

}

