package kr.lililli.hellorestfulservice.bean;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("AdminUserV2")
public class AdminUserV2 extends AdminUser {

  private String grade;
}
