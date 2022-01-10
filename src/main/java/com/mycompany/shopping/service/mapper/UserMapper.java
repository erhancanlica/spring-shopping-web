package com.mycompany.shopping.service.mapper;

import com.mycompany.shopping.domain.Authority;
import com.mycompany.shopping.domain.User;
import com.mycompany.shopping.service.dto.AdminUserDto;
import com.mycompany.shopping.service.dto.UserDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserMapper {


    public UserDto userToUserDTO(User user) {
        return new UserDto(user);
    }

    public List<UserDto> usersToUserDTOs(List<User> users) {
        return users.stream().filter(Objects::nonNull).map(this::userToUserDTO).collect(Collectors.toList());
    }

    public AdminUserDto userToAdminUserDto(User user) {
        return new AdminUserDto(user);
    }

    public List<AdminUserDto> usersToAdminUsersDto(List<User> users) {
        return users.stream().filter(Objects::nonNull).map(this::userToAdminUserDto).collect(Collectors.toList());
    }

    public User userDtoToUser(AdminUserDto userDto) {
        if (userDto == null) {
            return null;
        } else {
            User user = new User();
            user.setId(userDto.getId());
            user.setUsername(userDto.getUsername());
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setEmail(userDto.getEmail());
            user.setImageUrl(userDto.getImageUrl());
            user.setActivated(userDto.isActivated());
            user.setLangKey(userDto.getLangKey());
            Set<Authority> authorities = this.authoritiesFromStrings(userDto.getAuthorities());
            user.setAuthorities(authorities);
            return user;
        }
    }

    public User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public UserDto toDtoId(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        return userDto;
    }

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public Set<UserDto> toDtoIdSet(Set<User> users) {
        if (users == null) {
            return Collections.emptySet();
        }

        Set<UserDto> userSet = new HashSet<>();
        for (User userEntity : users) {
            userSet.add(this.toDtoId(userEntity));
        }

        return userSet;
    }

    @Named("login")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    public UserDto toDtoLogin(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        return userDto;
    }

    @Named("loginSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    public Set<UserDto> toDtoLoginSet(Set<User> users) {
        if (users == null) {
            return Collections.emptySet();
        }

        Set<UserDto> userSet = new HashSet<>();
        for (User userEntity : users) {
            userSet.add(this.toDtoLogin(userEntity));
        }

        return userSet;
    }

    private Set<Authority> authoritiesFromStrings(Set<String> authoritiesAsString) {
        Set<Authority> authorities = new HashSet<>();

        if (authoritiesAsString != null) {
            authorities = authoritiesAsString
                    .stream().map(s -> {
                        Authority authority = new Authority();
                        authority.setName(s);
                        return authority;
                    })
                    .collect(Collectors.toSet());
        }

        return authorities;
    }

}
