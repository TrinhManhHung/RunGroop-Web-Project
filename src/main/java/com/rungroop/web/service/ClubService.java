package com.rungroop.web.service;

import com.rungroop.web.dto.ClubDto;
import com.rungroop.web.models.Club;

import java.util.List;

//định nghĩa các phương thức cho serviceImpl thực hiện
public interface ClubService {
    List<ClubDto> findAllClubs();
    Club saveClub(Club club);
}
