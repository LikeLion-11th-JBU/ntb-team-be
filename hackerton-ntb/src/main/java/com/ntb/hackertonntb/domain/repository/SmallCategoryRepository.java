package com.ntb.hackertonntb.domain.repository;

import com.ntb.hackertonntb.domain.entity.SmallCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmallCategoryRepository extends JpaRepository<SmallCategory, Integer> {

    SmallCategory findById(int id);
}
