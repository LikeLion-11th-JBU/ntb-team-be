package com.ntb.hackertonntb.service;


import com.ntb.hackertonntb.domain.entity.HaveSkill;
import com.ntb.hackertonntb.domain.entity.Skills;
import com.ntb.hackertonntb.domain.entity.WantSkill;
import com.ntb.hackertonntb.domain.repository.HaveSkillRepository;
import com.ntb.hackertonntb.domain.repository.SkillsRepository;
import com.ntb.hackertonntb.domain.repository.WantSkillRepository;
import com.ntb.hackertonntb.dto.HaveSkillDto;
import com.ntb.hackertonntb.dto.SkillsDto;
import com.ntb.hackertonntb.dto.WantSkillDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Data
@RequiredArgsConstructor
public class HaveSkillService {


    private static final Logger logger = LoggerFactory.getLogger(SkillsService.class);

    private final HaveSkillRepository haveSkillRepository;


    public void save(HaveSkillDto haveSkillDto, Skills skills) {

        HaveSkill newHaveSkills = haveSkillDto.toEntity(skills);
        haveSkillRepository.save(newHaveSkills);
    }

    @Transactional
    @Modifying
    public void update(HaveSkillDto haveSkillDto, Skills skills) {

        HaveSkill newHaveSkills = haveSkillDto.toEntity(skills);
        haveSkillRepository.save(newHaveSkills);
    }

}
