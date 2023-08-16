package com.ntb.hackertonntb.service;

import com.ntb.hackertonntb.domain.entity.Category;
import com.ntb.hackertonntb.domain.entity.SmallCategory;
import com.ntb.hackertonntb.domain.repository.CategoryRepository;
import com.ntb.hackertonntb.domain.repository.SmallCategoryRepository;
import com.ntb.hackertonntb.domain.repository.UserRepository;
import com.ntb.hackertonntb.dto.CategoryDto;
import com.ntb.hackertonntb.dto.SmallCategoryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor
public class SmallCategoryService {

        private static final Logger logger = LoggerFactory.getLogger(SmallCategoryService.class);

        private final SmallCategoryRepository smallCategoryRepository;


        public void save(SmallCategoryDto smallCategoryDto, Category categories) {

            SmallCategory newSmallCategory = smallCategoryDto.toEntity(categories);
            smallCategoryRepository.save(newSmallCategory);
        }
}
