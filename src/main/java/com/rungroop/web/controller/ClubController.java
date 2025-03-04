package com.rungroop.web.controller;
/*
-Xử lý các yêu cầu (requests) từ phía client (ví dụ: trình duyệt web) và trả về phản hồi (response) phù hợp.
@GetMapping: Ánh xạ một yêu cầu HTTP GET đến một phương thức trong Controller.
@PostMapping: Ánh xạ một yêu cầu HTTP POST đến một phương thức trong Controller.

 */

import com.rungroop.web.models.Club;
import org.springframework.ui.Model;
import com.rungroop.web.dto.ClubDto;
import com.rungroop.web.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ClubController {
    private ClubService clubService;

    @Autowired
    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping("/clubs")
    public String listClubs(Model model) {
        List<ClubDto> clubs = clubService.findAllClubs();
        model.addAttribute("clubs", clubs);
        return "clubs-list";
    }

    @GetMapping("/clubs/new")
    public String createClubForm(Model model){
        //Khi truy cập /clubs/new, controller sẽ tạo một đối tượng Club rỗng và truyền vào model.
        Club club = new Club();
        model.addAttribute("club", club);
        return "clubs-create";
    }

    @PostMapping("/clubs/new")
    public String saveClub(@ModelAttribute("club") Club club) {
        clubService.saveClub(club);
        return "redirect:/clubs";
    }
}
