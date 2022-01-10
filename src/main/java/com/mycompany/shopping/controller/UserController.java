package com.mycompany.shopping.controller;

import com.mycompany.shopping.domain.User;
import com.mycompany.shopping.exception.rest.BadRequestAlertException;
import com.mycompany.shopping.exception.rest.EmailAlreadyUsedException;
import com.mycompany.shopping.exception.rest.LoginAlreadyUsedException;
import com.mycompany.shopping.repository.UserRepository;
import com.mycompany.shopping.service.UserService;
import com.mycompany.shopping.service.dto.AdminUserDto;
import com.mycompany.shopping.utils.AuthoritiesConstants;
import com.mycompany.shopping.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("user/{username}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Optional<AdminUserDto>> getUser(@PathVariable @Pattern(regexp = Constants.LOGIN_REGEX) String username) {
        log.debug("REST request to get User : {}", username);
        return ResponseEntity.ok().body(userService.getUserWithAuthoritiesByUsername(username).map(AdminUserDto::new));
    }

    @GetMapping("users")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<AdminUserDto>> getAllUsers() {
        log.debug("REST request to get all User for an admin");
        return ResponseEntity.ok().body(userService.getAllManagedUsers());
    }

    @PostMapping("user")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<User> createUser(@RequestBody AdminUserDto userDto) throws URISyntaxException {
        log.debug("REST request to save User : {}", userDto);

        if (userDto.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement", "idexists");
            // Lowercase the user login before comparing with database
        } else if (userRepository.findOneByUsername(userDto.getUsername().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        } else if (userRepository.findOneByEmailIgnoreCase(userDto.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {
            User newUser = userService.createUser(userDto);
            return ResponseEntity
                    .created(new URI("/api/admin/users/" + newUser.getUsername()))
                    .body(newUser);
        }
    }

    @PutMapping("/users")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Optional<AdminUserDto>> updateUser(@Valid @RequestBody AdminUserDto userDto) {
        log.debug("REST request to update User : {}", userDto);

        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDto.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDto.getId()))) {
            throw new EmailAlreadyUsedException();
        }
        existingUser = userRepository.findOneByUsername(userDto.getUsername().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDto.getId()))) {
            throw new LoginAlreadyUsedException();
        }
        Optional<AdminUserDto> updatedUser = userService.updateUser(userDto);

        return ResponseEntity.ok().body(updatedUser);
    }

    @DeleteMapping("/users/{username}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteUser(@PathVariable @Pattern(regexp = Constants.LOGIN_REGEX) String username) {
        log.debug("REST request to delete User: {}", username);
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }
}
