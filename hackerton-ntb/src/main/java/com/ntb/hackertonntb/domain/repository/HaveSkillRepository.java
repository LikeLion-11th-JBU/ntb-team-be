package com.ntb.hackertonntb.domain.repository;

import com.ntb.hackertonntb.domain.entity.HaveSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HaveSkillRepository extends JpaRepository<HaveSkill, Long> {

    List<HaveSkill> findByIdContaining(String keyword);
}
