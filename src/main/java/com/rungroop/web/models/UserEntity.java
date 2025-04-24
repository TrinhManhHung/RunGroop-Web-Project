package com.rungroop.web.models;

import com.rungroop.web.dto.ClubDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    //FetchType.EAGER có nghĩa là dữ liệu sẽ được tải ngay lập tức khi entity cha được lấy ra
    //ascadeType.ALL có nghĩa là mọi thay đổi trên entity cha cũng sẽ ảnh hưởng đến entity con.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.REMOVE)
    private List<Club> clubs = new ArrayList<>();
}
