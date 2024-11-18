package kr.lililli.hellorestfulservice.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"ssn"})
@Schema(description = "사용자 상세 정보를 위한 도메인 객체")
public class User {

  @Schema(title = "사용자 ID", description = "사용자 ID는 자동 생성됩니다.")
  private Integer id;
  @Schema(title = "사용자 이름", description = "사용자 이름을 입력합니다.")
  @Size(min = 2, message = "Name은 2글자 이상 입력해 주세요.")
  private String name;
  @Past(message = "미래의 일자는 입력할 수 없습니다.")
  private Date joinDate;
  private String ssn;
}
