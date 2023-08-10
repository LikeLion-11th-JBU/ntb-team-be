package com.ntb.hackertonntb.config;

import com.ntb.hackertonntb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(
            @Autowired UserService userService
    ) {
        userDetailsService = userService;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(this.userDetailsService);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //이거 추가하면 우회 하는거고 사실상 보안상 취약함
                .authorizeRequests()
                .antMatchers(
                        "/swagger-ui/index.html",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/main",
                        "/main/signup",
                        "/main/login",
                        "/main/signup/**"
                ).permitAll() // 나중에 스웨거는 권한 바꺼야해.
                .antMatchers("/main/super").hasRole("SUPER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/main/login") // 로그인 페이지
                .usernameParameter("loginId")
                .passwordParameter("password")
                .defaultSuccessUrl("/main") //메인 페이지 이동
                .failureUrl("/main/login?error=true")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout") // 로그아웃 주소
                .logoutSuccessUrl("/main") // 메인 페이지로 이동
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll();
    }
}

