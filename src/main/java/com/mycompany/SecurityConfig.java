package com.mycompany;


import com.mycompany.entity.RoleEntity;
import com.mycompany.entity.UserEntity;
import com.mycompany.services.IUserService;
import com.mycompany.services.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig{

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((auth) -> auth
                    .requestMatchers("/*", "/post/*").permitAll()
                    .requestMatchers("/admin").hasAnyAuthority("ADMIN", "CTVCONTENT")
						.requestMatchers("/admin/category", "/admin/category/**").hasAuthority("ADMIN")
						.requestMatchers("/admin/role", "/admin/role/**").hasAuthority("ADMIN")
						.requestMatchers("/admin/user", "/admin/user/**").hasAuthority("ADMIN")
                    .anyRequest().authenticated())
                .formLogin(login -> login
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/admin", true))	                
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login"));
        return http.build();
    }

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/static/**", "/WebAssets/**", "/adminAssets/**", "/uploads/**");
	}


}
