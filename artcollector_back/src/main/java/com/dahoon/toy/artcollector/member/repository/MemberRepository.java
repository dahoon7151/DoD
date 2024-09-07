package com.dahoon.toy.artcollector.member.repository;

import com.dahoon.toy.artcollector.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
