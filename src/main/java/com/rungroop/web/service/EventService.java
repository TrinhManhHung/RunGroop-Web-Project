package com.rungroop.web.service;


import com.rungroop.web.dto.EventDto;
import jakarta.validation.Valid;

import java.util.List;

public interface EventService {
    void createEvent(Long clubId, EventDto eventDto);
    List<EventDto> findAllEvents();

    EventDto findEventById(Long eventId);

    void updateEvent(@Valid EventDto eventDto);

    void deleteEvent(Long eventId);

    long countEvents();
}
