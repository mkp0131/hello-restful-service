package kr.lililli.hellorestfulservice.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class SecurityConfig {

  @Bean
  UserDetailsService userDetailsService() {
    InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
    UserDetails newUser = User.withUsername("user")
        .password(passwordEncoder().encode("passw0rd")).authorities("read").build();
    userDetailsManager.createUser(newUser);
    return userDetailsManager;
  }

  // 암호화 처리하지 않으면 에러가난다.
  @Bean
  BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  // h2-console 접근을 위한 시큐리티 예외 처리
  @Bean
  WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> {
      web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
    };
  }


  // CSRF 토큰 비활성화
  @Bean
  protected SecurityFilterChain filterChain(HttpSecurity http,
      HandlerMappingIntrospector introspector) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable);
    return http.build();
  }
}
