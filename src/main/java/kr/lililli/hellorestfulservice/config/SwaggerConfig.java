package kr.lililli.hellorestfulservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@OpenAPIDefinition(
    info = @Info(
        title = "My Restful Service API 명세서",
        description = "Spring Boot를 이용한 Restful Service API 명세서입니다.",
        version = "v1"
    )
)
public class SwaggerConfig {

  @Bean
  public GroupedOpenApi customTestOpenApi() {
    String[] paths = {"/users/**", "/admin/**"}; // 노출할 api 그룹
    return GroupedOpenApi.builder().group("일반 사용자와 관리자를 위한 User 도메인에 대한 API").pathsToMatch(paths)
        .build();
  }
}
