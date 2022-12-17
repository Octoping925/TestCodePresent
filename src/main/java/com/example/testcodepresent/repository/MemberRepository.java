package com.example.testcodepresent.repository;

import com.example.testcodepresent.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findMemberById(Long id);
    boolean existsMemberByEmail(String email);

}
