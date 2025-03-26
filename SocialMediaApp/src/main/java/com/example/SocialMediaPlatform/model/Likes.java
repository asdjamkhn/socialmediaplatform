package com.example.SocialMediaPlatform.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "likes")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Likes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int likesId;

    private LocalDate date = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
