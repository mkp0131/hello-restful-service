package kr.lililli.hellorestfulservice.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"ssn"})
@Schema(description = "사용자 상세 정보를 위한 도메인 객체")
@Entity  // JPA가 관리하는 엔티티 객체임을 명시
@Table(name = "users")  // 'users' 테이블과 매핑
public class User {

  @Schema(title = "사용자 ID", description = "사용자 ID는 자동 생성됩니다.")
  @Id  // 엔티티의 기본키(Primary Key) 지정
  @GeneratedValue  // 기본키 값을 자동으로 생성 (AUTO_INCREMENT)
  private Integer id;
  @Schema(title = "사용자 이름", description = "사용자 이름을 입력합니다.")
  @Size(min = 2, message = "Name은 2글자 이상 입력해 주세요.")
  private String name;
  @Past(message = "미래의 일자는 입력할 수 없습니다.")
  private Date joinDate;
  private String ssn;

  @OneToMany(mappedBy = "user")
  private List<Post> posts;

  public User(Integer id, String name, Date joinDate, String ssn) {
    this.id = id;
    this.name = name;
    this.joinDate = joinDate;
    this.ssn = ssn;
  }
}
