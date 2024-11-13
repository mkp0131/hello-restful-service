package kr.lililli.hellorestfulservice.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import kr.lililli.hellorestfulservice.bean.AdminUser;
import kr.lililli.hellorestfulservice.bean.AdminUserV2;
import kr.lililli.hellorestfulservice.bean.User;
import kr.lililli.hellorestfulservice.dao.UserDaoService;
import kr.lililli.hellorestfulservice.exception.UserNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminUserController {

  private final UserDaoService userDaoService;

  public AdminUserController(UserDaoService userDaoService) {
    this.userDaoService = userDaoService;
  }


  //  @GetMapping("/v1/user/{id}")
  @GetMapping(value = "/user/{id}", params = "version=1")
  public MappingJacksonValue getUser4Admin(@PathVariable int id) {
    User user = userDaoService.findOne(id);

    AdminUser adminUser = new AdminUser();
    if (user == null) {
//      throw new Exception(String.format("ID[%s] not found", id));
      throw new UserNotFoundException(String.format("ID[%s] not found", id));
    } else {
      // 객체의 속성을 모두 복사 user → adminUser
      BeanUtils.copyProperties(user, adminUser);
    }

    // 필터생성
    SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name",
        "joinDate");

    // 필터를 등록되어있는 jsonfilter 에 등록
    FilterProvider filters = new SimpleFilterProvider().addFilter("AdminUser", filter);

    // 필터 적용
    MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
    mapping.setFilters(filters);

    return mapping;
  }


  @GetMapping(value = "/user/{id}", params = "version=2")
  public MappingJacksonValue getUser4AdminV2(@PathVariable int id) {
    User user = userDaoService.findOne(id);

    AdminUserV2 adminUser = new AdminUserV2();
    if (user == null) {
//      throw new Exception(String.format("ID[%s] not found", id));
      throw new UserNotFoundException(String.format("ID[%s] not found", id));
    } else {
      // 객체의 속성을 모두 복사 user → adminUser
      BeanUtils.copyProperties(user, adminUser);
      adminUser.setGrade("VIP");
    }

    // 필터생성
    SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name",
        "joinDate", "grade");

    // 필터를 등록되어있는 jsonfilter 에 등록
    FilterProvider filters = new SimpleFilterProvider().addFilter("AdminUserV2", filter);

    // 필터 적용
    MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
    mapping.setFilters(filters);

    return mapping;
  }

}
