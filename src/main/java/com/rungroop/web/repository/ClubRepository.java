package com.rungroop.web.repository;

import com.rungroop.web.dto.ClubDto;
import com.rungroop.web.models.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

//Interface ClubRepository giúp bạn làm việc với database dễ dàng mà không cần viết SQL, tận dụng sức mạnh của Spring Data JPA.
public interface ClubRepository extends JpaRepository<Club, Long> {
    Optional<Club> findByTitle(String url);

//    @Query("SELECT c FROM Club c WHERE c.title LIKE CONCAT('%', :query, '%')")
//    List<Club> searchClubs(String query);
    @Query("SELECT new com.rungroop.web.dto.ClubDto(c.id, c.title, c.photoUrl, c.createdBy) " +
            "FROM Club c LEFT JOIN c.createdBy " +
            "WHERE c.title LIKE %:query%")
    List<ClubDto> searchClubs(@Param("query") String query);




}
