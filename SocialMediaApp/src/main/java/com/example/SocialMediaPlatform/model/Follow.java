package com.example.SocialMediaPlatform.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "follow")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Follow implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int followId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User follower;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User following;

}
