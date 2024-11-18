package kr.lililli.hellorestfulservice.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ScurityConfig {

  @Bean
  UserDetailsService userDetailsService() {
    InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
    UserDetails newUser = User.withUsername("user")
        .password(passwordEncoder().encode("passw0rd"))
        .authorities("read")
        .build();
    userDetailsManager.createUser(newUser);
    return userDetailsManager;
  }

  // 암호화 처리하지 않으면 에러가난다.
  @Bean
  BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}