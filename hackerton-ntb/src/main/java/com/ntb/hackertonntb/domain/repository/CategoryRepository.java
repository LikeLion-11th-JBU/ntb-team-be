package com.ntb.hackertonntb.domain.repository;

import com.ntb.hackertonntb.domain.entity.Category;
import com.ntb.hackertonntb.domain.entity.SmallCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findById(int id);

    List<SmallCategory> findByIdContaining(Category category);

}