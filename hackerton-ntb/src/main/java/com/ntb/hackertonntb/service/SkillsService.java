package com.ntb.hackertonntb.service;

import com.ntb.hackertonntb.domain.entity.Skills;
import com.ntb.hackertonntb.domain.entity.SmallCategory;
import com.ntb.hackertonntb.domain.repository.SkillsRepository;
import com.ntb.hackertonntb.dto.SkillsDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor
public class SkillsService {
    private static final Logger logger = LoggerFactory.getLogger(SkillsService.class);

    private final SkillsRepository skillsRepository;

    public void save(SkillsDto skillsDto, SmallCategory smallCategories) {

        Skills newSkills = skillsDto.toEntity(smallCategories);
        skillsRepository.save(newSkills);
    }


}
