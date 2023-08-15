package com.ntb.hackertonntb.domain.repository;

import com.ntb.hackertonntb.domain.entity.HaveSkill;
import com.ntb.hackertonntb.domain.entity.Skills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SkillsRepository extends JpaRepository<Skills, Long> {

    @Query(value = "SELECT * FROM skills ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Skills> findRandomSkills(@Param("limit") int limit);
    List<Skills> findByIdContaining(String keyword);
}