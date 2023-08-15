package com.ntb.hackertonntb.service;

import com.ntb.hackertonntb.domain.entity.HaveSkill;
import com.ntb.hackertonntb.domain.entity.Skills;
import com.ntb.hackertonntb.domain.entity.WantSkill;
import com.ntb.hackertonntb.domain.repository.HaveSkillRepository;
import com.ntb.hackertonntb.domain.repository.WantSkillRepository;
import com.ntb.hackertonntb.dto.HaveSkillDto;
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
public class WantSkillService {

    private static final Logger logger = LoggerFactory.getLogger(SkillsService.class);

    private final WantSkillRepository wantSkillRepository;


    public void save(WantSkillDto wantSkillDto, Skills skills) {

        WantSkill newWantSkills = wantSkillDto.toEntity(skills);
        wantSkillRepository.save(newWantSkills);
    }

    @Transactional
    @Modifying
    public void update(WantSkillDto wantSkillDto, Skills skills) {

        WantSkill newWantSkills = wantSkillDto.toEntity(skills);
        wantSkillRepository.save(newWantSkills);
    }

}
