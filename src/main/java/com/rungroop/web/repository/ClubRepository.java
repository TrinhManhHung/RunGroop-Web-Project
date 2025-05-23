package com.rungroop.web.repository;

import com.rungroop.web.dto.ClubDto;
import com.rungroop.web.models.Club;
import com.rungroop.web.dto.EventDto;
import com.rungroop.web.models.Event;
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

    @Query(value = """
        SELECT c.id, c.title, c.photo_url AS photoUrl, COUNT(e.id) AS eventCount
        FROM clubs c
        LEFT JOIN event e ON c.id = e.club_id
        GROUP BY c.id, c.title, c.photo_url
        ORDER BY eventCount DESC
    """, nativeQuery = true)
    List<Object[]> findSortedClubs();







}
