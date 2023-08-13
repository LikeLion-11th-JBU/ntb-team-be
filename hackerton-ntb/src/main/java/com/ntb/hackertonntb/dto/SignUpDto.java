package com.ntb.hackertonntb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class SignUpDto {
    private UserDto userDto;
    private String wantSkillCategory;
    private String smallWantSkillCategory;
    private String wantSkillKeyword1;
    private String wantSkillKeyword2;
    private String wantSkillKeyword3;
    private String haveSkillCategory;
    private String smallHaveSkillCategory;
    private String haveSkillKeyword1;
    private String haveSkillKeyword2;
    private String haveSkillKeyword3;
}
