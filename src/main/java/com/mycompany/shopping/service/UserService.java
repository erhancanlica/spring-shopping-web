package com.mycompany.shopping.service;

import com.mycompany.shopping.domain.User;
import com.mycompany.shopping.service.dto.AdminUserDto;
import com.mycompany.shopping.service.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> activateRegistration(String key);

    Optional<User> completePasswordReset(String newPassword, String key);

    Optional<User> requestPasswordReset(String mail);

    User registerUser(AdminUserDto adminUserDto, String password);

    boolean removeNonActivatedUser(User existingUser);

    User createUser(AdminUserDto userDto);

    Optional<AdminUserDto> updateUser(AdminUserDto userDto);

    void deleteUser(String login);

    void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl);

    void changePassword(String currentClearTextPassword, String newPassword);

    List<AdminUserDto> getAllManagedUsers();

    List<UserDto> getAllPublicUsers();

    Optional<User> getUserWithAuthoritiesByUsername(String username);

    Optional<User> getUserWithAuthorities();

    void removeNotActivatedUser();

    List<String> getAuthorities();

    void clearUserCaches(User user);

}