package com.rungroop.web.mapper;

import com.rungroop.web.dto.EventDto;
import com.rungroop.web.models.Event;

public class EventMapper {
    public static Event mapToEvent(EventDto eventDto){
        return Event.builder()
                .id(eventDto.getId())
                .name(eventDto.getName())
                .startTime(eventDto.getStartTime())
                .endTime(eventDto.getEndTime())
                .type(eventDto.getType())
                .photoUrl(eventDto.getPhotoUrl())
                .createdOn(eventDto.getCreatedOn())
                .updateOn(eventDto.getUpdateOn())
                .location(eventDto.getLocation())
                .club(eventDto.getClub())
                .describe(eventDto.getDescribe())
                .build();
    }
    public static EventDto mapToEventDto(Event event){
        return EventDto.builder()
                .id(event.getId())
                .name(event.getName())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .type(event.getType())
                .photoUrl(event.getPhotoUrl())
                .createdOn(event.getCreatedOn())
                .updateOn(event.getUpdateOn())
                .location(event.getLocation())
                .club(event.getClub())
                .describe(event.getDescribe())
                .build();
    }
}
