package com.ntb.hackertonntb.service;

import com.ntb.hackertonntb.domain.entity.*;
import com.ntb.hackertonntb.domain.repository.*;
import com.ntb.hackertonntb.dto.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Data
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final SmallCategoryRepository smallCategoryRepository;
    private final SkillsRepository skillsRepository;
    private final HaveSkillRepository haveSkillRepository;
    private final WantSkillRepository wantSkillRepository;
    private final WantSkillService wantSkillService;
    private final HaveSkillService haveSkillService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    // 로그인을 위한 UserDetailService 의 필수 method
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        final User user = userRepository.findByLoginId(loginId);
        return new org.springframework.security.core.userdetails.User(loginId, user.getPassword(), new ArrayList<>()) {
        };
    }

    // 모든 사용자 정보를 가져옵니다.
    public List<Object[]> getUserAndConnectedTablesByLoginId(String loginId) {
        return userRepository.getUserAndConnectedTablesByLoginId(loginId);
    }

    // 회원 가입 기능 입니다.
    public void save(UserDto userDto, MultipartFile profile) throws Exception {
        // 프로필 사진 저장
        String projectPath = Paths.get(
                System.getProperty("user.dir"),
                "hackerton-ntb",
                "src", "main",
                "resources",
                "static",
                "files"
        ).toString();
        UUID uuid = UUID.randomUUID();
        String profileName = uuid + "_" + profile.getOriginalFilename();
        File savefile = new File(projectPath, profileName);
        profile.transferTo(savefile);
        userDto.setProfileName(profileName);
        userDto.setProfilePath("/files/" + profileName);

        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encryptedPassword);

        User newUser = userDto.toEntity();
        userRepository.save(newUser);
    }

    //회원 업데이트 입니다.
    @Transactional
    public void update(UserDto userDto) throws Exception {
        User updateUser = userDto.toEntity();
        userRepository.save(updateUser);
    }


    // 아이디 중복 체크입니다.
    public boolean checkLoginIdDuplicate(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }


    //사용자 정보 가져오기
    public UserDto findByUser(String loginId) {
        User user = userRepository.findByLoginId(loginId);
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setLoginId(user.getLoginId());
        userDto.setName(user.getName());
        userDto.setPassword(user.getPassword());
        userDto.setIntroduce(user.getIntroduce());
        userDto.setOpenChat(user.getOpenChat());
        userDto.setEmail(user.getEmail());
        userDto.setProfileName(user.getProfileName());
        userDto.setProfilePath(user.getProfilePath());
        return userDto;
    }


    // 회원 탈퇴 입니다. (사진 삭제도 함께 진행 됩니다.)
    public void deleteById(String loginId) {
        User user = userRepository.findByLoginId(loginId);
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setProfileName(user.getProfileName());
        String profileName = userDto.getProfileName();
        String projectPath = Paths.get(
                System.getProperty("user.dir"),
                "hackerton-ntb",
                "src",
                "main",
                "resources",
                "static",
                "files"
        ).toString();
        File userProfile = new File(projectPath, profileName);
        if (profileName != null) {
            userProfile.delete();
        }
        userRepository.deleteById(userDto.getId());
    }

    // 유저 프로필 사진 변경 전 삭제
    public void deleteByImage(String loginId) {
        User user = userRepository.findByLoginId(loginId);
        UserDto userDto = new UserDto();
        userDto.setProfileName(user.getProfileName());
        String profileName = userDto.getProfileName();
        String projectPath = Paths.get(
                System.getProperty("user.dir"),
                "hackerton-ntb",
                "src",
                "main",
                "resources",
                "static",
                "files"
        ).toString();
        File userProfile = new File(projectPath, profileName);
        if (profileName != null) {
            userProfile.delete();
        }
    }


    // 프로필 사진 변경 업로드
    public void updateByImage(String loginId, MultipartFile profile) throws Exception {

        // 프로필 사진 저장
        String projectPath = Paths.get(
                System.getProperty("user.dir"),
                "hackerton-ntb",
                "src", "main",
                "resources",
                "static",
                "files"
        ).toString();
        UUID uuid = UUID.randomUUID();
        String profileName = uuid + "_" + profile.getOriginalFilename();
        File savefile = new File(projectPath, profileName);
        profile.transferTo(savefile);
        UserDto changeUser = findByUser(loginId);
        changeUser.setProfileName(profileName);
        changeUser.setProfilePath("/files/" + profileName);
        User newUser = changeUser.toEntity();
        userRepository.save(newUser);
    }
}
