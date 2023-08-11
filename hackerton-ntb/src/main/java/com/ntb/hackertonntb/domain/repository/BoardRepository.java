package com.ntb.hackertonntb.domain.repository;

import com.ntb.hackertonntb.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
