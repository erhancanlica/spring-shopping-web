package com.mycompany.shopping.service.dto;

import com.mycompany.shopping.domain.User;
import lombok.*;

@Data
public class UserDto {

    private Long id;

    private String username;

    public UserDto() {}

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }
}
