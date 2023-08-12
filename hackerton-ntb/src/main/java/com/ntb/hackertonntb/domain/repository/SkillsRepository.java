package com.ntb.hackertonntb.domain.repository;

import com.ntb.hackertonntb.domain.entity.Skills;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillsRepository extends JpaRepository<Skills, Integer> {

    Skills findById(int id);

    List<Skills> findByIdContaining(String keyword);
}