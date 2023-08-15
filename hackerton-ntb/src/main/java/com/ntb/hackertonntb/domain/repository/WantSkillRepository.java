package com.ntb.hackertonntb.domain.repository;

import com.ntb.hackertonntb.domain.entity.WantSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WantSkillRepository extends JpaRepository<WantSkill, Long> {

    List<WantSkill> findByIdContaining(String keyword);

}
