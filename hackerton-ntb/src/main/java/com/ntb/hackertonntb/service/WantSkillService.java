package com.ntb.hackertonntb.service;

import com.ntb.hackertonntb.domain.entity.HaveSkill;
import com.ntb.hackertonntb.domain.entity.WantSkill;
import com.ntb.hackertonntb.domain.repository.HaveSkillRepository;
import com.ntb.hackertonntb.domain.repository.WantSkillRepository;
import com.ntb.hackertonntb.dto.HaveSkillDto;
import com.ntb.hackertonntb.dto.WantSkillDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor
public class WantSkillService {

    private static final Logger logger = LoggerFactory.getLogger(SkillsService.class);

    private final WantSkillRepository wantSkillRepository;

    public void save(WantSkillDto wantSkillDto) {

        WantSkill newWantSkills = wantSkillDto.toEntity();
        wantSkillRepository.save(newWantSkills);
    }
}