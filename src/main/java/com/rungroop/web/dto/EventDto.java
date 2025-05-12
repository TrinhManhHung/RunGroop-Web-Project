package com.rungroop.web.dto;

import com.rungroop.web.models.Club;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private Long id;
    @NotEmpty(message = "Event name should not be empty")
    private String name;
//    @NotEmpty(message = "Event startTime should not be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startTime;
//    @NotEmpty(message = "Event endTime should not be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endTime;
    @NotEmpty(message = "Event type should not be empty")
    private String type;
    private String photoUrl;
    private LocalDateTime createdOn;
    private LocalDateTime updateOn;
    @NotEmpty(message = "Event location should not be empty")
    private String location;
    private Club club;
    @NotEmpty(message = "Event describe should not be empty")
    private String describe;
}
