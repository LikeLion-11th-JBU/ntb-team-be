package com.ntb.hackertonntb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class SignUpDto {
    private UserDto userDto;
    private String wantSkillCategory;
    private String smallWantSkillCategory;
    private String wantSkillKeyword;
    private String haveSkillCategory;
    private String smallHaveSkillCategory;
    private String haveSkillKeyword;
}
