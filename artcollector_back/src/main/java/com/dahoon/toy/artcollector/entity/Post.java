package com.dahoon.toy.artcollector.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;
}
