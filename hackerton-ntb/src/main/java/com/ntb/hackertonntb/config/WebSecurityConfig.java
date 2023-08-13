package com.ntb.hackertonntb.config;

import com.ntb.hackertonntb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;

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
    @Autowired
    private DataSource dataSource;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService);
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .rolePrefix("ROLE_");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //이거 추가하면 우회 하는거고 사실상 보안상 취약함
                .authorizeRequests()
//                .antMatchers("/main/super").hasRole("SUPER")
                .antMatchers(
                        "/**"
//                        "/swagger-ui/index.html",
//                        "/v3/api-docs/**",
//                        "/swagger-ui/**",
//                        "/swagger-resources/**",
//                        "/main",
//                        "/main/signup",
//                        "/main/login",
//                        "/main/signup/**"
                ).permitAll() // 나중에 스웨거는 권한 바꺼야해.
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/main/login") // 로그인 페이지
                .loginProcessingUrl("/main/login")
                .usernameParameter("loginId")
                .passwordParameter("password")
                .successHandler((request, response, authentication) -> {
                    // 로그인 성공 시 JSON 응답 생성
                    response.setStatus(HttpStatus.OK.value());
                    response.getWriter().println("{\"message\": \"Login successful\"}");
                })
                .failureHandler((request, response, exception) -> {
                    // 로그인 실패 시 JSON 응답 생성
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().println("{\"message\": \"Login failed\"}");
                })
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout") // 로그아웃 주소
                .logoutSuccessUrl("/main") // 메인 페이지로 이동
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}

