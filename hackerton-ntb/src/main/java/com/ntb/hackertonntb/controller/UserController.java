package com.ntb.hackertonntb.controller;

import com.ntb.hackertonntb.domain.entity.*;
import com.ntb.hackertonntb.domain.repository.*;
import com.ntb.hackertonntb.dto.*;
import com.ntb.hackertonntb.service.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Log4j2
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final CategoryService categoryService;
    private final SkillsService skillsService;
    private final SmallCategoryService smallCategoryService;
    private final HaveSkillService haveSkillService;
    private final WantSkillService wantSkillService;
    private final UserRepository userRepository;


    @GetMapping("main")
    @Operation(summary = "메인 페이지", description = "메인 페이지 입니다.", tags = {"testMainPage"})
    public String main(
            Model model,
            Principal principal
    ) {
        try {
            if (principal != null) {
                logger.info("connect user : {}", principal.getName());
                UserDto userDto = userService.findByUser(principal.getName());
                model.addAttribute("userDto", userDto);
            } else {
                logger.info("no user login");
            }
        } catch (NullPointerException e) {
            logger.info("no user login");
        }
        return "main";
    }

    @GetMapping("main/login")
    @Operation(summary = "로그인 페이지", description = "로그인 페이지 입니다.", tags = {"UserPage"})
    public String loginForm() {
        return "login-form";
    }


    @PostMapping("main/signup")
    @Operation(summary = "회원가입 요청", description = "회원가입 요청 입니다.", tags = {"UserRequest"})
    public String signup(
            @Valid UserDto userDto,
            @RequestParam("wantSkillCategory") String wantSkillCategory,
            @RequestParam("smallWantSkillCategory") String smallWantSkillCategory,
            @RequestParam("wantSkillKeyword1") String wantSkillKeyword1,
            @RequestParam("wantSkillKeyword2") String wantSkillKeyword2,
            @RequestParam("wantSkillKeyword3") String wantSkillKeyword3,
            @RequestParam("haveSkillCategory") String haveSkillCategory,
            @RequestParam("smallHaveSkillCategory") String smallHaveSkillCategory,
            @RequestParam("haveSkillKeyword1") String haveSkillKeyword1,
            @RequestParam("haveSkillKeyword2") String haveSkillKeyword2,
            @RequestParam("haveSkillKeyword3") String haveSkillKeyword3,
            MultipartFile profile
    ) throws  Exception
    {
        userService.save(userDto,profile);
        User userEntity = userRepository.findByLoginId(userDto.getLoginId());

        CategoryDto haveCategory = new CategoryDto();
        haveCategory.setCategoryname(haveSkillCategory);
        Category haveCategoryEntity = haveCategory.toEntity(userEntity);
        SmallCategoryDto smallHaveCategory = new SmallCategoryDto();
        smallHaveCategory.setSmallcategoryname(smallHaveSkillCategory);
        SmallCategory smallHaveEntity = smallHaveCategory.toEntity(haveCategoryEntity);
        SkillsDto haveSkill = new SkillsDto();
        haveSkill.setSkillname(haveSkillKeyword1);
        haveSkill.setSkillname2(haveSkillKeyword2);
        haveSkill.setSkillname3(haveSkillKeyword3);
        Skills haveSkillEntity = haveSkill.toEntity(smallHaveEntity);
        HaveSkillDto haveEntity = new HaveSkillDto();
        haveSkillService.save(haveEntity,haveSkillEntity);

        CategoryDto wantCategory = new CategoryDto();
        wantCategory.setCategoryname(wantSkillCategory);
        Category wantCategoryEntity = wantCategory.toEntity(userEntity);
        SmallCategoryDto smallWantCategory = new SmallCategoryDto();
        smallWantCategory.setSmallcategoryname(smallWantSkillCategory);
        SmallCategory smallWantEntity = smallWantCategory.toEntity(wantCategoryEntity);
        SkillsDto wantSkill = new SkillsDto();
        wantSkill.setSkillname(wantSkillKeyword1);
        wantSkill.setSkillname2(wantSkillKeyword2);
        wantSkill.setSkillname3(wantSkillKeyword3);
        Skills wantSkillEntity = wantSkill.toEntity(smallWantEntity);
        WantSkillDto wantEntity = new WantSkillDto();
        wantSkillService.save(wantEntity,wantSkillEntity);
        return "redirect:/main";
    }


    @GetMapping("main/signup")
    @Operation(summary = "회원가입 페이지", description = "회원가입 페이지 입니다.", tags = {"UserPage"})
    public String signupForm(
    ) {
        return "signup-form";
    }



    @GetMapping("main/signup/{loginId}/exists")
    @Operation(summary = "아이디 중복 요청", description = "아이디 중복이면 true 아니면 false 를 반환합니다..", tags = {"UserRequest"})
    public ResponseEntity<Boolean> checkLoginIdDuplicate(@PathVariable String loginId) {
        return ResponseEntity.ok(userService.checkLoginIdDuplicate(loginId));
    }


    @GetMapping("/main/user/detail")
    @Operation(summary = "유저 상세 페이지", description = "유저 상세 페이지 입니다.", tags = {"UserPage"})
    public String userDetails(Model model, Principal principal) {
        UserDto userDto = userService.findByUser(principal.getName());
        model.addAttribute("userDto", userDto);
        return "user-detail";
    }


    @GetMapping("/main/user/delete")
    @Operation(summary = "회원 탈퇴 페이지", description = "회원 탈퇴 페이지 입니다.", tags = {"UserRequest"})
    public String deleteUser(Principal principal) {
        userService.deleteById(principal.getName());
    return "redirect:/main/login";
    }


    @GetMapping("/main/user/update")
    @Operation(summary = "회원 정보 수정 페이지", description = "회원 정보 수정 페이지 입니다.", tags = {"UserPage"})
    public String updateUserForm(
            Model model,
            Principal principal
    ) {
        UserDto userDto = userService.findByUser(principal.getName());
        model.addAttribute("userDto", userDto);
        return "user-update";
    }


    @PostMapping("/main/user/update")
    @Operation(summary = "회원 정보 수정 요청", description = "회원 정보 수정 요청 입니다.", tags = {"UserRequest"})
    public String updateUser(
            MultipartFile profile,
            Principal principal,
            UserDto userDto
    ) throws Exception {
        userService.deleteByImage(principal.getName());
        UserDto updateUser = userService.findByUser(principal.getName());
        updateUser.setName(userDto.getName());
        updateUser.setIntroduce(userDto.getIntroduce());
        updateUser.setOpenChat(userDto.getOpenChat());
        updateUser.setEmail(userDto.getEmail());
        userService.update(updateUser,profile);
        return "redirect:/main/user/detail";
    }

    @GetMapping("/files/{profileName}")
    @Operation(summary = "사진 바이트 변환 ", description = "빌드 후 사진을 꺼내오기 위한 작업입니다.", tags = "UserRequest")
    public ResponseEntity<byte[]> getProfileImage(
            @PathVariable String profileName
    ) throws IOException {
        String baseDir = System.getProperty("user.dir");
        String staticDir = "src" +
                File.separator +
                "main" +
                File.separator +
                "resources" +
                File.separator +
                "static";
        String absoluteFilePath = baseDir +
                File.separator +
                "hackerton-ntb" +
                File.separator +
                staticDir +
                File.separator +
                "files" +
                File.separator +
                profileName;
        InputStream inputStream = new FileInputStream(absoluteFilePath);
        byte[] imageByteArray = IOUtils.toByteArray(inputStream);
        inputStream.close();
        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
    }


    @Secured("SUPER")
    @GetMapping("/main/super")
    @Operation(summary = "관리자 페이지", description = "관리자 페이지 입니다.", tags = {"AdminPage"})
    public String adminPage() {
        return "admin";
    }
}
