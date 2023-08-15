package com.ntb.hackertonntb.domain.repository;

import com.ntb.hackertonntb.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u, c, sc, s from User u " +
            "left join u.categories c " +
            "left join c.smallCategories sc " +
            "left join sc.skills s " +
            "where u.loginId = :loginId")
    List<Object[]> getUserAndConnectedTablesByLoginId(@Param("loginId") String loginId);



    User findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);

}
