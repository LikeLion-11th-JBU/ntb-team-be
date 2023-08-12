package com.ntb.hackertonntb.domain.repository;

import com.ntb.hackertonntb.domain.entity.Category;
import com.ntb.hackertonntb.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<Category> findByIdContaining(String loginId);

    User findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);

}
