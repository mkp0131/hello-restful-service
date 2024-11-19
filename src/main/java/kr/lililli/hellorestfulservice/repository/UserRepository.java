package kr.lililli.hellorestfulservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kr.lililli.hellorestfulservice.bean.User;

/**
 * User 엔티티에 대한 데이터베이스 작업을 처리하는 리포지토리 인터페이스 JpaRepository를 상속받아 기본적인 CRUD 작업 메서드를 제공
 * 
 * @Repository 어노테이션으로 스프링 빈으로 등록됨
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // JpaRepository에서 제공하는 기본 메서드들:
    // save(), findById(), findAll(), delete() 등
}
