package com.post.repository;

//import com.post.entity.Post;
//import com.post.entity.User;
//import com.post.entity.UserFollower;
//
//import java.awt.print.Pageable;
//import java.util.List;
//import java.util.Set;
//
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface PostRepository extends JpaRepository<Post, Long> {
//	  @Query("SELECT p FROM Post p WHERE p.user.id IN :authorIds ORDER BY p.createdAt DESC")
//	    List<Post> findByAuthorIdsOrderByCreatedAtDesc(List<Long> authorIds);
//
//	    @Query("SELECT p FROM Post p WHERE p.user.id IN :authorIds ORDER BY p.createdAt DESC")
//	    List<Post> findByAuthorIdsOrderByCreatedAtDesc(List<Long> authorIds, PageRequest pageable);
//	
//	}
//    


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.post.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
	  @Query("SELECT p FROM Post p WHERE p.user.id IN :authorIds ORDER BY p.createdAt DESC")
	    List<Post> findByAuthorIdsOrderByCreatedAtDesc(List<Long> authorIds);


    @Query("SELECT p FROM Post p WHERE p.user.id IN :authorIds ORDER BY p.createdAt DESC")
    List<Post> findByAuthorIdsOrderByCreatedAtDesc(List<Long> authorIds, Pageable pageable);
}

