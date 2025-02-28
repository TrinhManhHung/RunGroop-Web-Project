package com.rungroop.web.dto;
//bảo mật dữ liệu, tránh việc client nhận được những thông tin không cần thiết hoặc nhạy cảm.
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data //Cho phép sử dụng builder pattern để tạo đối tượng CLubDto.
@Builder //Tự động tạo các getter, setter, toString, equals, hashCode.
public class ClubDto {
    private Long id;
    private String title;
    private String photoUrl;
    private String content;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
