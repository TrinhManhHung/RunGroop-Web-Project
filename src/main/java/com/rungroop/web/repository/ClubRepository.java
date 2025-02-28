package com.rungroop.web.repository;

import com.rungroop.web.models.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//Interface ClubRepository giúp bạn làm việc với database dễ dàng mà không cần viết SQL, tận dụng sức mạnh của Spring Data JPA.
public interface ClubRepository extends JpaRepository<Club, Long> {
    Optional<Club> findByTitle(String url);
}
