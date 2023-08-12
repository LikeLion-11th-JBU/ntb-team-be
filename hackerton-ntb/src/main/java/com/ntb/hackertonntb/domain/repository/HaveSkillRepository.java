package com.ntb.hackertonntb.domain.repository;

import com.ntb.hackertonntb.domain.entity.HaveSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HaveSkillRepository extends JpaRepository<HaveSkill, Integer> {

    List<HaveSkill> findByIdContaining(String keyword);
}
