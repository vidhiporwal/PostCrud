package com.post.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.post.entity.User;
import com.post.entity.UserFollower;

public interface UserFollowerRepository extends JpaRepository<UserFollower, Long>{

	boolean existsByUserAndFollower(User user, User followUser);

	Optional<UserFollower> findByUserAndFollower(User user, User unfollowUser);
	  List<UserFollower> findByUserId(Long userId);
}
