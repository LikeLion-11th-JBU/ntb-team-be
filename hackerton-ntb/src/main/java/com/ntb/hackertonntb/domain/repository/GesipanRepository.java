package com.ntb.hackertonntb.domain.repository;

import com.ntb.hackertonntb.domain.entity.Gesipan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GesipanRepository extends JpaRepository<Gesipan, Long> {

    @Query(value = "SELECT * FROM skills ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Gesipan> findRandomGesipan(@Param("limit") int limit);
    List<Gesipan> findByIdContaining(String keyword);
}
