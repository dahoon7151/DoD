package com.dahoon.toy.artcollector.repository;

import com.dahoon.toy.artcollector.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
