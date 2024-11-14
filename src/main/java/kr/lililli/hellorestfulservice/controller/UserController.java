package kr.lililli.hellorestfulservice.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import kr.lililli.hellorestfulservice.bean.User;
import kr.lililli.hellorestfulservice.dao.UserDaoService;
import kr.lililli.hellorestfulservice.exception.UserNotFoundException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {

  private final UserDaoService userDaoService;

  public UserController(UserDaoService userDaoService) {
    this.userDaoService = userDaoService;
  }

  @GetMapping("/users")
  public List<User> getUsers() {
    return userDaoService.findAll();
  }

  @GetMapping("/user/{id}")
  public EntityModel<User> getUser(@PathVariable int id) throws Exception {
    User user = userDaoService.findOne(id);
    if (user == null) {
//      throw new Exception(String.format("ID[%s] not found", id));
      throw new UserNotFoundException(String.format("ID[%s] not found", id));
    }
    EntityModel<User> model = EntityModel.of(user);
    WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(
        WebMvcLinkBuilder.methodOn(this.getClass()).getUsers());
    model.add(linkTo.withRel("all-users"));
    
    return model;
  }

  @PostMapping("/user")
  public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
    // 1. userDaoService를 통해 전달된 'user' 객체를 저장하고,
    //    저장된 'newUser' 객체를 반환받습니다.
    User newUser = userDaoService.save(user);

    // 2. 현재 요청 URI를 기준으로 새로 생성된 리소스의 URI를 만듭니다.
    //    ServletUriComponentsBuilder.fromCurrentRequest()는 현재 요청 URI (/user)를 가져옵니다.
    //    .path("/{id}")를 통해 URI 경로에 '/{id}'를 추가하여 최종 URI 형태를 만듭니다.
    //    .buildAndExpand(newUser.getId())는 'newUser' 객체의 ID 값을 '{id}'에 매핑하여
    //    최종 URI를 생성합니다. 예: /user/1
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(newUser.getId())
        .toUri();

    // 3. ResponseEntity.created(location)으로 '201 Created' 상태 코드를 설정하고,
    //    Location 헤더에 'location' URI를 포함하여 응답을 생성합니다.
    //    build()를 호출하여 응답 본문 없이 빈 응답(ResponseEntity)을 반환합니다.
    return ResponseEntity.created(location).body(newUser);
  }

  @DeleteMapping("/user/{id}")
  public ResponseEntity<User> deleteUser(@PathVariable int id) {
    User user = userDaoService.deleteById(id);
    if (user == null) {
      throw new UserNotFoundException(String.format("ID[%s] not found", id));
    }
    return ResponseEntity.noContent().build();
  }
}
