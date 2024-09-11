package com.dahoon.toy.artcollector.post.repository;

import com.dahoon.toy.artcollector.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
