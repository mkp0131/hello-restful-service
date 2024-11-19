package kr.lililli.hellorestfulservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import kr.lililli.hellorestfulservice.bean.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
