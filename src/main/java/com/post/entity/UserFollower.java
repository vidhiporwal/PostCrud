package com.post.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Entity
@Table(name = "user_followers")
public class UserFollower {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;

    // Getters, setters, constructors, and other methods as needed
}
