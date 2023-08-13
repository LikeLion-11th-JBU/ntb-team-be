package com.ntb.hackertonntb.domain.repository;

import com.ntb.hackertonntb.domain.entity.Category;
import com.ntb.hackertonntb.domain.entity.SmallCategory;
import com.ntb.hackertonntb.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findById(int id);

    List<SmallCategory> findByIdContaining(Category category);

}