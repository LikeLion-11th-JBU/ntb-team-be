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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            @RequestBody Principal principal
    ) {
        try {
            if (principal != null) {
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
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("userDto", user);
                responseData.put("wantCategories", wantCategories);
                responseData.put("haveCategories", haveCategories);
                responseData.put("wantSmallCategories", wantSmallCategories);
                responseData.put("haveSmallCategories", haveSmallCategories);
                responseData.put("wantSkills", wantSkills);
                responseData.put("haveSkills", haveSkills);

                return ResponseEntity.ok(responseData);

            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
            }
        } catch (NullPointerException e) {
        }
        return ResponseEntity.ok("no user login");
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
    @Operation(summary = "유저 상세 페이지", description = "유저 상세 페이지 입니다.", tags = {"UserPage"})
    public ResponseEntity<? extends Object> userDetails(Principal principal) {
        try {
            if (principal != null) {
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
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("userDto", user);
                responseData.put("wantCategories", wantCategories);
                responseData.put("haveCategories", haveCategories);
                responseData.put("wantSmallCategories", wantSmallCategories);
                responseData.put("haveSmallCategories", haveSmallCategories);
                responseData.put("wantSkills", wantSkills);
                responseData.put("haveSkills", haveSkills);

                return ResponseEntity.ok(responseData);

            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유저 정보를 찾지 못 했습니다.");
            }
        } catch (NullPointerException e) {
        }
        return ResponseEntity.ok("no user login");
    }


    @PostMapping("/main/user/delete")
    @Operation(summary = "회원 탈퇴 페이지", description = "회원 탈퇴 페이지 입니다.", tags = {"UserRequest"})
    public ResponseEntity<?> deleteUser(Principal principal) {
        userService.deleteById(principal.getName());
        return ResponseEntity.ok("Delete user success");
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
    public String updateUser(
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
            Principal principal,
            MultipartFile profile
    ) throws Exception {
        if (profile != null) {
            UserDto userProfileChange = userService.findByUser(principal.getName());
            userService.deleteByImage(principal.getName());
            userService.updateByImage(userProfileChange, profile); //결국에는 저장하는 메소드라서 오류

            User userEntity = userRepository.findByLoginId(principal.getName());
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
        }

        UserDto originalUser = userService.findByUser(principal.getName());

        User userEntity = originalUser.toEntity();

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
        return "redirect:/main/user";
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

