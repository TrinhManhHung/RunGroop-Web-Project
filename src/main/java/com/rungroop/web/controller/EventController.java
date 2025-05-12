package com.rungroop.web.controller;

import com.rungroop.web.dto.ClubDto;
import com.rungroop.web.dto.EventDto;
import com.rungroop.web.models.Event;
import com.rungroop.web.models.UserEntity;
import com.rungroop.web.security.SecurityUtil;
import com.rungroop.web.service.EventService;
import com.rungroop.web.service.UploadFileService;
import com.rungroop.web.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller

public class EventController {
    private UserService userService;
    private EventService eventService;
    private UploadFileService uploadFileService;

    @Autowired
    public EventController(EventService eventService, UserService userService) {
        this.userService = userService;
        this.eventService = eventService;
        this.uploadFileService = new UploadFileService();
    }

    @GetMapping("/events")
    public String listEvents(Model model){
        UserEntity user = new UserEntity();
        List<EventDto> events = eventService.findAllEvents();
        String email = SecurityUtil.getSessionUser();
        if(email != null) {
            user = userService.findByEmail(email);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("events", events);
        return "events-list";
    }

    @GetMapping("/events/{eventId}")
    public String viewEvent(@PathVariable("eventId") Long eventId, Model model){
        UserEntity user = new UserEntity();
        EventDto eventDto = eventService.findEventById(eventId);
        String email = SecurityUtil.getSessionUser();
        if(email != null) {
            user = userService.findByEmail(email);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("event", eventDto);
        return "events-detail";
    }

    @GetMapping("/events/{clubId}/new")
    public String createEventForm(@PathVariable("clubId") Long clubId, Model model){
        Event event = new Event();
        model.addAttribute("clubId", clubId);
        model.addAttribute("event", event);
        return "events-create";
    }

    @GetMapping("/events/{eventId}/edit")
    public String editEventForm(@PathVariable("eventId") long eventId, Model model) {
        EventDto event = eventService.findEventById(eventId);
        model.addAttribute("event", event);
        return "events-edit";
    }

    @PostMapping("/events/{clubId}")
    public String createEvent(@PathVariable("clubId") Long clubId,
                              @Valid @ModelAttribute("event") EventDto eventDto,
                              BindingResult result,
                              @RequestParam("photo") MultipartFile file,
                              Model model) {
        if(result.hasErrors() || file.isEmpty()) {
            model.addAttribute("event", eventDto);
            return "events-create";
        }

        String photoUrl = uploadFileService.handleSaveUploadedFile(file, "events");
        if(photoUrl != null && !photoUrl.isEmpty()) {
            eventDto.setPhotoUrl(photoUrl);
        }

        eventService.createEvent(clubId, eventDto);
        return "redirect:/clubs/" + clubId;
    }

    @PostMapping("/events/{eventId}/edit")
    public String updateEvent(@PathVariable("eventId") Long eventId,
                             @Valid @ModelAttribute("event") EventDto event,
                             BindingResult result,
                             @RequestParam("photo") MultipartFile file,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("event", event);
            return "events-edit";
        }
        EventDto eventDto = eventService.findEventById(eventId);

        String photoUrl = uploadFileService.handleSaveUploadedFile(file, "events");
        if(file.isEmpty()) photoUrl = eventDto.getPhotoUrl();

        event.setId(eventId);
        event.setClub(eventDto.getClub());
        event.setPhotoUrl(photoUrl);
        eventService.updateEvent(event);
        return "redirect:/events";
    }

    @GetMapping("/events/{eventId}/delete")
    public String deleteEvent(@PathVariable("eventId") long eventId){
        eventService.deleteEvent(eventId);
        return "redirect:/events";
    }

}
