package com.mycompany.shopping.controller;

import com.mycompany.shopping.controller.vm.KeyAndPasswordVM;
import com.mycompany.shopping.controller.vm.ManagedUserVM;
import com.mycompany.shopping.domain.User;
import com.mycompany.shopping.exception.security.EmailAlreadyUsedException;
import com.mycompany.shopping.exception.security.InvalidPasswordException;
import com.mycompany.shopping.repository.UserRepository;
import com.mycompany.shopping.service.UserService;
import com.mycompany.shopping.service.dto.AdminUserDto;
import com.mycompany.shopping.service.dto.PasswordChangeDto;
import com.mycompany.shopping.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AccountController {


    private static class AccountResourceException extends RuntimeException {
        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final UserService userService;

    private final UserRepository userRepository;

    public AccountController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/account")
    public AdminUserDto getAccount() {
        // test 
        return userService
                .getUserWithAuthorities()
                .map(AdminUserDto::new)
                .orElseThrow(() -> new AccountResourceException("User could not be found"));
    }

    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    @GetMapping("/active")
    public void activateAccount(@RequestParam(value = "key") String key) {
        Optional<User> user = userService.activateRegistration(key);
        if (user.isEmpty()) {
            throw new AccountResourceException("No user was found for this activation key");
        }
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@RequestBody ManagedUserVM managedUserVM) {
        if (isPasswordLengthInvalid(managedUserVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        userService.registerUser(managedUserVM, managedUserVM.getPassword());
    }

    @PostMapping("/account")
    public void saveAccount(@RequestBody AdminUserDto adminUserDto) {
        String userLogin = SecurityUtils
                .getCurrentUserLogin()
                .orElseThrow(() -> new AccountResourceException("Current user login not found"));
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(adminUserDto.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getUsername().equalsIgnoreCase(userLogin))) {
            throw new EmailAlreadyUsedException();
        }
        Optional<User> user = userRepository.findOneByUsername(userLogin);
        if (user.isEmpty()) {
            throw new AccountResourceException("User could not be found");
        }
        userService.updateUser(
                adminUserDto.getFirstName(),
                adminUserDto.getLastName(),
                adminUserDto.getEmail(),
                adminUserDto.getLangKey(),
                adminUserDto.getImageUrl()
        );
    }

    @PostMapping(path = "/account/change-password")
    public void changePassword(PasswordChangeDto passwordChangeDto) {
        if (isPasswordLengthInvalid(passwordChangeDto.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        userService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getCurrentPassword());
    }

    @PostMapping(path = "/account/reset-password/init")
    public void requestPasswordReset(@RequestBody String mail) {
        Optional<User> user = userService.requestPasswordReset(mail);
//        if (user.isPresent()) {
//            mailService.sendPasswordResetMail(user.get());
//        } else {
//            // Pretend the request has been successful to prevent checking which emails really exist
//            // but log that an invalid attempt has been made
//            log.warn("Password reset requested for non existing mail");
//        }
        log.warn("Password reset requested for non existing mail");
    }

    @PostMapping(path = "/account/reset-password/finish")
    public void finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
        if (isPasswordLengthInvalid(keyAndPassword.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        Optional<User> user = userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());

        if (user.isEmpty()) {
            throw new AccountResourceException("No user was found for this reset key");
        }
    }

    private static boolean isPasswordLengthInvalid(String password) {
        return (
                StringUtils.isEmpty(password) ||
                        password.length() < ManagedUserVM.PASSWORD_MIN_LENGTH ||
                        password.length() > ManagedUserVM.PASSWORD_MAX_LENGTH
        );
    }
};

