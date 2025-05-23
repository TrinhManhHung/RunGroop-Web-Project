package com.rungroop.web.service.impl;

import com.rungroop.web.dto.ClubDto;
import com.rungroop.web.models.Club;
import com.rungroop.web.models.UserEntity;
import com.rungroop.web.repository.ClubRepository;
import com.rungroop.web.repository.UserRepository;
import com.rungroop.web.security.SecurityUtil;
import com.rungroop.web.service.ClubService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.rungroop.web.mapper.ClubMapper.mapToClub;
import static com.rungroop.web.mapper.ClubMapper.mapToClubDto;

@Service

public class ClubServiceImpl implements ClubService {
    private ClubRepository clubRepository;
    private UserRepository userRepository;

    @Autowired
    public ClubServiceImpl(ClubRepository clubRepository, UserRepository userRepository) {

        this.clubRepository = clubRepository;
        this.userRepository = userRepository;
    }
    @Override
    public List<ClubDto> findAllClubs() {
        List<Club> clubs = clubRepository.findAll();
        return clubs.stream().map((club) -> mapToClubDto(club)).collect(Collectors.toList());
    }

    @Override
    public ClubDto findClubById(long clubId){
        Club club = clubRepository.findById(clubId).get();
        return mapToClubDto(club);
    }

    @Override
    public void updateClub(ClubDto clubDto) {
        String email = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByEmail(email);
        Club club = mapToClub(clubDto);
        club.setCreatedBy(user);
        clubRepository.save(club);
    }

    @Override
    public Club saveClub(ClubDto clubDto) {
        String email = SecurityUtil.getSessionUser();
//        System.out.println("Username từ session: " + email);
        UserEntity user = userRepository.findByEmail(email);
//        System.out.println("User tìm được: " + user);
        Club club = mapToClub(clubDto);
        club.setCreatedBy(user);
        return clubRepository.save(club);
    }

    @Override
    public void delete(long clubId){
        clubRepository.deleteById(clubId);
    }

//    @Override
//    public List<ClubDto> searchClubs(String query){
//        List<Club> clubs = clubRepository.searchClubs(query);
//        return clubs.stream().map(club -> mapToClubDto(club)).collect(Collectors.toList());
//    }


    @Override
    public List<ClubDto> searchClubs(String query) {
        return clubRepository.searchClubs(query);
    }


    @Override
    public long countClubs() {
        return clubRepository.count();
    }

    @Override
    public List<ClubDto> findSortedClubs() {
        List<Object[]> results = clubRepository.findSortedClubs();
        List<ClubDto> dtos = results.stream().map(r -> {
            ClubDto dto = new ClubDto();
            dto.setId(((Number) r[0]).longValue());
            dto.setTitle((String) r[1]);
            dto.setPhotoUrl((String) r[2]);
            return dto;
        }).collect(Collectors.toList());

        return dtos;
    }

    public List<Long> findEventCount() {
        List<Object[]> results = clubRepository.findSortedClubs();
        return results.stream()
                .map(r -> ((Number) r[3]).longValue())
                .collect(Collectors.toList());
    }

}

