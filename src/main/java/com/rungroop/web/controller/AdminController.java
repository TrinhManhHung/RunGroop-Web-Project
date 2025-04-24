package com.rungroop.web.controller;

import com.rungroop.web.dto.ClubDto;
import com.rungroop.web.dto.EventDto;
import com.rungroop.web.dto.RegistrationDto;
import com.rungroop.web.models.UserEntity;
import com.rungroop.web.service.ClubService;
import com.rungroop.web.service.EventService;
import com.rungroop.web.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final ClubService clubService;
    private final EventService eventService;

    @Autowired
    public AdminController(UserService userService, ClubService clubService, EventService eventService) {
        this.userService = userService;
        this.clubService = clubService;
        this.eventService = eventService;
    }

    @RequestMapping("")
    public String showDashboard(Model model) {
        long userCount = userService.countUsers();
        long clubCount = clubService.countClubs();
        long eventCount = eventService.countEvents();

        model.addAttribute("userCount", userCount);
        model.addAttribute("clubCount", clubCount);
        model.addAttribute("eventCount", eventCount);
        return "admin/dashboard";
    }


    @GetMapping("/users")
    public String showUser(Model model) {
        List<UserEntity> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "admin/users-list";
    }
    @PostMapping("users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }


    @GetMapping("/clubs")
    public String showClubs(Model model) {
        List<ClubDto> clubs = clubService.findAllClubs();
        model.addAttribute("clubs", clubs);
        return "admin/clubs-list";
    }
    @PostMapping("clubs/delete/{id}")
    public String deleteClub(@PathVariable Long id) {
        clubService.delete(id);
        return "redirect:/admin/clubs";
    }


    @GetMapping("/events")
    public String showEvents(Model model){
        List<EventDto> events = eventService.findAllEvents();
        model.addAttribute("events", events);
        return "admin/events-list";
    }
    @PostMapping("events/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return "redirect:/admin/events";
    }
}
