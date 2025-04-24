package com.rungroop.web.repository;

import com.rungroop.web.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);

    UserEntity findFirstByUsername(String username);

    @Query("SELECT u FROM users u WHERE u.username <> 'admin'")
    List<UserEntity> findAllExcludingAdmin();
}
