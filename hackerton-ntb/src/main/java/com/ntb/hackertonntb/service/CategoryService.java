package com.ntb.hackertonntb.service;

import com.ntb.hackertonntb.domain.entity.Category;
import com.ntb.hackertonntb.domain.entity.User;
import com.ntb.hackertonntb.domain.repository.CategoryRepository;
import com.ntb.hackertonntb.domain.repository.UserRepository;
import com.ntb.hackertonntb.dto.CategoryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@Data
@RequiredArgsConstructor
public class CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    public void save(CategoryDto categoryDto) {

        Category newCategory = categoryDto.toEntity();
        categoryRepository.save(newCategory);
    }



}
