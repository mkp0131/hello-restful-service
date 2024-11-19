package kr.lililli.hellorestfulservice.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import kr.lililli.hellorestfulservice.bean.Post;
import kr.lililli.hellorestfulservice.bean.User;
import kr.lililli.hellorestfulservice.exception.UserNotFoundException;
import kr.lililli.hellorestfulservice.repository.PostRepository;
import kr.lililli.hellorestfulservice.repository.UserRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/jpa")
public class UserJpaController {

  private final UserRepository userRepository;
  private final PostRepository postRepository;

  public UserJpaController(UserRepository userRepository, PostRepository postRepository) {
    this.userRepository = userRepository;
    this.postRepository = postRepository;
  }

  @GetMapping("/users")
  public List<User> getUsers() {
    return userRepository.findAll();
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<?> getMethodName(@PathVariable int id) {
    Optional<User> user = userRepository.findById(id);

    if (user.isEmpty()) {
      System.out.println("ID[" + id + "] not found");
      throw new UserNotFoundException(String.format("ID[%s] not found", id));
    }

    EntityModel<User> model = EntityModel.of(user.get());
    WebMvcLinkBuilder linkTo =
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUsers());
    model.add(linkTo.withRel("all-users"));

    return ResponseEntity.ok(model);
  }

  @DeleteMapping("/user/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable int id) {

    Optional<User> user = userRepository.findById(id);

    if (user.isEmpty()) {
      System.out.println("ID[" + id + "] not found");
      throw new UserNotFoundException(String.format("ID[%s] not found", id));
    }

    userRepository.deleteById(id);

    return ResponseEntity.noContent().build();
  }

  @PostMapping("/user")
  public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
    System.out.println("user: " + user);
    User newUser = userRepository.save(user);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(newUser.getId()).toUri();

    return ResponseEntity.created(location).body(newUser);
  }

  @GetMapping("/users/{id}/posts")
  public List<Post> getPostsByUserId(@PathVariable int id) {
    Optional<User> user = userRepository.findById(id);
    if (user.isEmpty()) {
      throw new UserNotFoundException(String.format("ID[%s] not found", id));
    }

    return user.get().getPosts();
  }

  @PostMapping("/users/{id}/posts")
  public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post post) {
    Optional<User> user = userRepository.findById(id);
    if (user.isEmpty()) {
      throw new UserNotFoundException(String.format("ID[%s] not found", id));
    }

    post.setUser(user.get());
    Post newPost = postRepository.save(post);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(newPost.getId()).toUri();

    return ResponseEntity.created(location).body(newPost);
  }
}
