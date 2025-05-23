package com.rungroop.web.dto;
//bảo mật dữ liệu, tránh việc client nhận được những thông tin không cần thiết hoặc nhạy cảm.
import com.rungroop.web.models.UserEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data //Cho phép sử dụng builder pattern để tạo đối tượng CLubDto.
@Builder //Tự động tạo các getter, setter, toString, equals, hashCode.
@AllArgsConstructor
@NoArgsConstructor
public class ClubDto {
    private Long id;
    @NotEmpty(message = "Club title should not be empty")
    private String title;
//    @NotEmpty(message = "Photo link should not be empty")
    private String photoUrl;
    @NotEmpty(message = "Club content should not be empty")
    private String content;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private List<EventDto> events;
    private UserEntity createdBy;
    @NotEmpty(message = "Describe content should not be empty")
    private String describe;

    public ClubDto(Long id, String title, String photoUrl, UserEntity createdBy) {
        this.id = id;
        this.title = title;
        this.photoUrl = photoUrl;
        this.createdBy = createdBy;
    }

}
