package com.rungroop.web.controller;
/*
-Xử lý các yêu cầu (requests) từ phía client (ví dụ: trình duyệt web) và trả về phản hồi (response) phù hợp.
@GetMapping: Ánh xạ một yêu cầu HTTP GET đến một phương thức trong Controller.
@PostMapping: Ánh xạ một yêu cầu HTTP POST đến một phương thức trong Controller.

 */

import com.rungroop.web.dto.EventDto;
import com.rungroop.web.models.Club;
import com.rungroop.web.models.Event;
import com.rungroop.web.models.UserEntity;
import com.rungroop.web.security.SecurityUtil;
import com.rungroop.web.service.UploadFileService;
import com.rungroop.web.service.UserService;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import com.rungroop.web.dto.ClubDto;
import com.rungroop.web.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class ClubController {
    private UserService userService;
    private ClubService clubService;
    private UploadFileService uploadFileService;

    @Autowired
    public ClubController(ClubService clubService, UserService userService, UploadFileService uploadFileService) {
        this.userService = userService;
        this.clubService = clubService;
        this.uploadFileService = uploadFileService;
    }
    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/clubs")
    public String listClubs(Model model) {
        UserEntity user = new UserEntity();
        List<ClubDto> clubs = clubService.findAllClubs();
        String email = SecurityUtil.getSessionUser();
        if(email != null) {
            user = userService.findByEmail(email);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
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
    public String saveClub(
            @Valid @ModelAttribute("club") ClubDto clubDto,
            BindingResult result, // chuyển BindingResult ngay sau DTO
            @RequestParam("photo") MultipartFile file,
            Model model
    ) {
        if (result.hasErrors() || file.isEmpty()) {
//            result.getFieldErrors().forEach(error -> {
//                System.out.println("Lỗi tại field: " + error.getField() + " - " + error.getDefaultMessage());
//            });
            model.addAttribute("club", clubDto);
            return "clubs-create";
        }

        String photoUrl = uploadFileService.handleSaveUploadedFile(file, "clubs");
        if (photoUrl != null && !photoUrl.isEmpty()) {
            clubDto.setPhotoUrl(photoUrl);
        }

        clubService.saveClub(clubDto);
        return "redirect:/clubs";
    }


    @GetMapping("/clubs/{clubId}")
    public String clubDetail(@PathVariable("clubId") long clubId, Model model){
        UserEntity user = new UserEntity();
        ClubDto clubDto = clubService.findClubById(clubId);
        String email = SecurityUtil.getSessionUser();
        if(email != null) {
            user = userService.findByEmail(email);
            model.addAttribute("user", user);
        }
        for(EventDto x : clubDto.getEvents()) {
            System.out.println(x);
        }
        model.addAttribute("user", user);
        model.addAttribute("club", clubDto);
        return "clubs-detail";
    }

    @GetMapping("/clubs/{clubId}/edit")
    public String editClubForm(@PathVariable("clubId") long clubId, Model model) {
        ClubDto club = clubService.findClubById(clubId);
        model.addAttribute("club", club);
        return "clubs-edit";
    }

    @PostMapping("/clubs/{clubId}/edit")
    public String updateClub(@PathVariable("clubId") Long clubId,
                             @Valid @ModelAttribute("club") ClubDto club,
                             BindingResult result,
                             @RequestParam("photo") MultipartFile file,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("club", club);
            return "clubs-edit";
        }

        ClubDto currentClub = clubService.findClubById(clubId);
        String photoUrl = currentClub.getPhotoUrl();
        if(!file.isEmpty()) photoUrl = uploadFileService.handleSaveUploadedFile(file, "clubs");
        club.setId(clubId);
        club.setPhotoUrl(photoUrl);
        clubService.updateClub(club);

        return "redirect:/clubs";
    }

    @GetMapping("/clubs/{clubId}/delete")
    public String deleteClub(@PathVariable("clubId") long clubId) {
        clubService.delete(clubId);
        return "redirect:/clubs";
    }

    @GetMapping("/clubs/search")
    public String searchClub(@RequestParam(value = "query") String query, Model model){
        UserEntity user = new UserEntity();
        String email = SecurityUtil.getSessionUser();
        if(email != null) {
            user = userService.findByEmail(email);
            model.addAttribute("user", user);
        }
        List<ClubDto> clubs = clubService.searchClubs(query != null? query : "");
        model.addAttribute("clubs", clubs);
        return "clubs-list";
    }
}
