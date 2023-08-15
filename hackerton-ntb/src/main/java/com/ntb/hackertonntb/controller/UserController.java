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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.*;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Log4j2
@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    private final UserService userService;
    private final CategoryService categoryService;
    private final SkillsService skillsService;
    private final SmallCategoryService smallCategoryService;
    private final HaveSkillService haveSkillService;
    private final WantSkillService wantSkillService;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final SmallCategoryRepository smallCategoryRepository;
    private final WantSkillRepository wantSkillRepository;
    private final SkillsRepository skillsRepository;


    @GetMapping("main")
    @Operation(summary = "메인 페이지", description = "메인 페이지 입니다.", tags = {"testMainPage"})
    public ResponseEntity<?> main(
    ) {
        return ResponseEntity.ok("환영 합니다");
    }



    @GetMapping("/test")
    public ResponseEntity<Object> test() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String loginId = authentication.getName();
            List<Object[]> userData = userService.getUserAndConnectedTablesByLoginId(loginId);
            if (!userData.isEmpty()) {
                List<Map<String, Object>> resultList = new ArrayList<>();
                String[] identifiers = {"have", "want"};
                int identifierIndex = 0;
                for (Object[] objects : userData) {
                    User user = (User) objects[0];
                    Category category = (Category) objects[1];
                    SmallCategory smallCategory = (SmallCategory) objects[2];
                    Skills skill = (Skills) objects[3];

                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("username", user.getName());
                    resultMap.put("introduce", user.getIntroduce());
                    resultMap.put("openChat", user.getOpenChat());
                    resultMap.put(identifiers[identifierIndex] + "Category", category.getCategoryname());
                    resultMap.put(identifiers[identifierIndex] + "SmallCategory", smallCategory.getSmallcategoryname());
                    resultMap.put(identifiers[identifierIndex] +"Skill", skill.getSkillname());
                    resultMap.put(identifiers[identifierIndex] + "Skill2", skill.getSkillname2());
                    resultMap.put(identifiers[identifierIndex] + "Skill3", skill.getSkillname3());

                    resultList.add(resultMap);
                    identifierIndex = (identifierIndex + 1) % identifiers.length;
                }
                return ResponseEntity.ok(resultList);
            }
            return ResponseEntity.ok("none session");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }

    @GetMapping("main/login")
    @Operation(summary = "로그인 페이지", description = "로그인 페이지 입니다.", tags = {"UserPage"})
    public ResponseEntity loginForm() {
        return ResponseEntity.ok("login please");
    }


    @PostMapping("main/signup")
    @Operation(summary = "회원가입 요청", description = "회원가입 요청 입니다.", tags = {"UserRequest"})
    public ResponseEntity<String> signup(
            @RequestPart("profile") MultipartFile profile,
           @RequestPart SignUpDto requestData
    ) throws  Exception
    {
        try {
            UserDto userDto = requestData.getUserDto();
            String wantSkillCategory = requestData.getWantSkillCategory();
            String smallWantSkillCategory = requestData.getSmallWantSkillCategory();
            String wantSkillKeyword1 = requestData.getWantSkillKeyword1();
            String wantSkillKeyword2 = requestData.getWantSkillKeyword2();
            String wantSkillKeyword3 = requestData.getWantSkillKeyword3();
            String haveSkillCategory = requestData.getHaveSkillCategory();
            String smallHaveSkillCategory = requestData.getSmallHaveSkillCategory();
            String haveSkillKeyword1 = requestData.getHaveSkillKeyword1();
            String haveSkillKeyword2 = requestData.getHaveSkillKeyword2();
            String haveSkillKeyword3 = requestData.getHaveSkillKeyword3();

            userService.save(userDto, profile);
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
            haveSkillService.save(haveEntity, haveSkillEntity);

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
            wantSkillService.save(wantEntity, wantSkillEntity);
            return ResponseEntity.ok("User signup successfully. You can now log in.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to singup user: " + e.getMessage());
        }
    }


    @GetMapping("main/signup")
    @Operation(summary = "회원가입 페이지", description = "회원가입 페이지 입니다.", tags = {"UserPage"})
    public ResponseEntity signupForm(
    ) {
        return ResponseEntity.ok("Are ready to signup");
    }



    @PostMapping("main/signup/exists")
    @Operation(summary = "아이디 중복 요청", description = "아이디 중복 요청 입니다..", tags = {"UserRequest"})
    public ResponseEntity<?> checkLoginIdDuplicate(@RequestBody String loginId) {
        if (userService.checkLoginIdDuplicate(loginId)) {
            return ResponseEntity.ok(true);
        } else {
            ErrorDto errors = new ErrorDto("이미 사용 중인 아이디입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
    }



    @GetMapping("/main/user")
    @Operation(summary = "나의 유저 상세 페이지", description = "나의 유저 상세 페이지 입니다.", tags = {"UserPage"})
    public ResponseEntity<? extends Object> userDetails(
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String loginId = authentication.getName();
            List<Object[]> userData = userService.getUserAndConnectedTablesByLoginId(loginId);
            if (!userData.isEmpty()) {
                List<Map<String, Object>> resultList = new ArrayList<>();
                String[] identifiers = {"have", "want"};
                int identifierIndex = 0;
                for (Object[] objects : userData) {
                    User user = (User) objects[0];
                    Category category = (Category) objects[1];
                    SmallCategory smallCategory = (SmallCategory) objects[2];
                    Skills skill = (Skills) objects[3];

                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("username", user.getName());
                    resultMap.put("introduce", user.getIntroduce());
                    resultMap.put("openChat", user.getOpenChat());
                    resultMap.put(identifiers[identifierIndex] + "Category", category.getCategoryname());
                    resultMap.put(identifiers[identifierIndex] + "SmallCategory", smallCategory.getSmallcategoryname());
                    resultMap.put(identifiers[identifierIndex] +"Skill", skill.getSkillname());
                    resultMap.put(identifiers[identifierIndex] + "Skill2", skill.getSkillname2());
                    resultMap.put(identifiers[identifierIndex] + "Skill3", skill.getSkillname3());

                    resultList.add(resultMap);
                    identifierIndex = (identifierIndex + 1) % identifiers.length;
                }
                return ResponseEntity.ok(resultList);
            }
            return ResponseEntity.ok("none session");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }


    @PostMapping("/main/user/delete")
    @Operation(summary = "회원 탈퇴 페이지", description = "회원 탈퇴 페이지 입니다.", tags = {"UserRequest"})
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String loginId = authentication.getName();
            UserDto userDto = userService.findByUser(loginId);
            userService.deleteById(userDto.getLoginId());
            return ResponseEntity.ok("Delete user success");
        }
        return ResponseEntity.ok("Delete user Errors");
    }


    @GetMapping("/main/user/update")
    @Operation(summary = "회원 정보 수정 페이지", description = "회원 정보 수정 페이지 입니다.", tags = {"UserPage"})
    public String updateUserForm(
            Model model,
            Principal principal
    ) {
        User user = userRepository.findByLoginId(principal.getName());
        List<Category> categories = user.getCategories();

        List<Category> haveCategories = new ArrayList<>();
        List<Category> wantCategories = new ArrayList<>();

        for (Category category : categories) {
            if (category.getId() % 2 != 1) {
                wantCategories.add(category);
            } else {
                haveCategories.add(category);
            }
        }
        List<SmallCategory> smallCategories = new ArrayList<>();
        for (Category category : categories) {
            List<SmallCategory> smallCategoriesForCategory = category.getSmallCategories();
            smallCategories.addAll(smallCategoriesForCategory);
        }
        List<SmallCategory> wantSmallCategories = new ArrayList<>();
        List<SmallCategory> haveSmallCategories = new ArrayList<>();

        for (SmallCategory smallCategory : smallCategories) {
            if (smallCategory.getId() % 2 != 1) {
                wantSmallCategories.add(smallCategory);
            } else {
                haveSmallCategories.add(smallCategory);
            }
        }
        List<Skills> skiils = new ArrayList<>();
        for (SmallCategory smallCategory : smallCategories) {
            List<Skills> skillsForSmallCategory = smallCategory.getSkills();
            skiils.addAll(skillsForSmallCategory);
        }
        List<Skills> wantSkills = new ArrayList<>();
        List<Skills> haveSkills = new ArrayList<>();

        for (SmallCategory smallCategory : smallCategories) {
            List<Skills> skillsForSmallCategory = smallCategory.getSkills();

            for (Skills skill : skillsForSmallCategory) {
                List<WantSkill> wantSkillsForSkill = skill.getWantSkills();
                List<HaveSkill> haveSkillsForSkill = skill.getHaveSkills();

                if (!wantSkillsForSkill.isEmpty()) {
                    wantSkills.add(skill);
                } else if (!haveSkillsForSkill.isEmpty()) {
                    haveSkills.add(skill);
                }
            }
        }
        model.addAttribute("userDto", user);
        model.addAttribute("wantCategories", wantCategories);
        model.addAttribute("haveCategories", haveCategories);
        model.addAttribute("wantSmallCategories", wantSmallCategories);
        model.addAttribute("haveSmallCategories", haveSmallCategories);
        model.addAttribute("wantSkills", wantSkills);
        model.addAttribute("haveSkills", haveSkills);
        return "user-update";
    }


    @PostMapping("/main/user/update")
    @Operation(summary = "회원 정보 수정 요청", description = "회원 정보 수정 요청 입니다.", tags = {"UserRequest"})
    public ResponseEntity<String> updateUser(
            @RequestPart("profile") MultipartFile profile,
            @RequestPart SignUpDto requestData
    ) throws Exception {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                User user = userRepository.findByLoginId(authentication.getName());
                UserDto userProfileChange = userService.findByUser(user.getName());
                userService.deleteByImage(user.getName());
                userService.updateByImage(userProfileChange, profile); //결국에는 저장하는 메소드라서 오류

                UserDto userDto = requestData.getUserDto();
                String wantSkillCategory = requestData.getWantSkillCategory();
                String smallWantSkillCategory = requestData.getSmallWantSkillCategory();
                String wantSkillKeyword1 = requestData.getWantSkillKeyword1();
                String wantSkillKeyword2 = requestData.getWantSkillKeyword2();
                String wantSkillKeyword3 = requestData.getWantSkillKeyword3();
                String haveSkillCategory = requestData.getHaveSkillCategory();
                String smallHaveSkillCategory = requestData.getSmallHaveSkillCategory();
                String haveSkillKeyword1 = requestData.getHaveSkillKeyword1();
                String haveSkillKeyword2 = requestData.getHaveSkillKeyword2();
                String haveSkillKeyword3 = requestData.getHaveSkillKeyword3();

                userService.save(userDto, profile);
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
                haveSkillService.save(haveEntity, haveSkillEntity);

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
                wantSkillService.save(wantEntity, wantSkillEntity);
                return ResponseEntity.ok("회원 수정이 완료 됐습니다.");
            }

            UserDto userDto = requestData.getUserDto();
            String wantSkillCategory = requestData.getWantSkillCategory();
            String smallWantSkillCategory = requestData.getSmallWantSkillCategory();
            String wantSkillKeyword1 = requestData.getWantSkillKeyword1();
            String wantSkillKeyword2 = requestData.getWantSkillKeyword2();
            String wantSkillKeyword3 = requestData.getWantSkillKeyword3();
            String haveSkillCategory = requestData.getHaveSkillCategory();
            String smallHaveSkillCategory = requestData.getSmallHaveSkillCategory();
            String haveSkillKeyword1 = requestData.getHaveSkillKeyword1();
            String haveSkillKeyword2 = requestData.getHaveSkillKeyword2();
            String haveSkillKeyword3 = requestData.getHaveSkillKeyword3();

            userService.save(userDto, profile);
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
            haveSkillService.save(haveEntity, haveSkillEntity);

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
            wantSkillService.save(wantEntity, wantSkillEntity);
        } catch (Exception errors) {
            return ResponseEntity.ok("회원 가입 실패");
        }
        return ResponseEntity.ok("login again");
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

}

