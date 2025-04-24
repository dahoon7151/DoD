package com.dahoon.toy.artcollector.post;

import com.dahoon.toy.artcollector.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
