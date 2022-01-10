package com.mycompany.shopping.service.Impl;

import com.mycompany.shopping.repository.AuthorityRepository;
import com.mycompany.shopping.service.UserService;
import com.mycompany.shopping.domain.Authority;
import com.mycompany.shopping.domain.User;
import com.mycompany.shopping.exception.security.EmailAlreadyUsedException;
import com.mycompany.shopping.exception.security.InvalidPasswordException;
import com.mycompany.shopping.exception.security.UsernameAlreadyUsedException;
import com.mycompany.shopping.repository.UserRepository;
import com.mycompany.shopping.service.dto.AdminUserDto;
import com.mycompany.shopping.service.dto.UserDto;
import com.mycompany.shopping.utils.AuthoritiesConstants;
import com.mycompany.shopping.utils.Constants;
import com.mycompany.shopping.utils.SecurityUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static final int DEF_COUNT = 20;

    private static final SecureRandom SECURE_RANDOM;

    static {
        SECURE_RANDOM = new SecureRandom();
        SECURE_RANDOM.nextBytes(new byte[64]);
    }

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final CacheManager cacheManager;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository, CacheManager cacheManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.cacheManager = cacheManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository
                .findOneByActivationKey(key)
                .map(user -> {
                    user.setActivated(true);
                    user.setActivationKey(null);
                    this.clearUserCaches(user);
                    log.debug("Activated user: {}", user);
                    return user;
                });
    }

    @Override
    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        return userRepository
                .findOneByResetKey(key)
                .filter(user -> user.getResetDate().isAfter(Instant.now().minus(1, ChronoUnit.DAYS)))
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    user.setResetKey(null);
                    user.setResetDate(null);
                    this.clearUserCaches(user);
                    return user;
                });
    }

    @Override
    public Optional<User> requestPasswordReset(String mail) {
        return userRepository
                .findOneByEmailIgnoreCase(mail)
                .filter(User::isActivated)
                .map(user -> {
                    user.setResetKey(RandomStringUtils.random(DEF_COUNT, 0, 0, true, true, null, SECURE_RANDOM));
                    user.setResetDate(Instant.now());
                    this.clearUserCaches(user);
                    return user;
                });

    }

    @Override
    public User registerUser(AdminUserDto adminUserDto, String password) {
        userRepository
                .findOneByUsername(adminUserDto.getUsername().toLowerCase())
                .ifPresent(user -> {
                    boolean removed = removeNonActivatedUser(user);
                    if (!removed) {
                        throw new UsernameAlreadyUsedException();
                    }
                });
        userRepository
                .findOneByEmailIgnoreCase(adminUserDto.getEmail())
                .ifPresent(user -> {
                    boolean removed = removeNonActivatedUser(user);
                    if (!removed) {
                        throw new EmailAlreadyUsedException();
                    }
                });
        User user = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        user.setUsername(adminUserDto.getUsername().toLowerCase());
        user.setPassword(encryptedPassword);
        user.setFirstName(adminUserDto.getFirstName());
        user.setLastName(adminUserDto.getLastName());
        if (adminUserDto.getEmail() != null) {
            user.setEmail(adminUserDto.getEmail().toLowerCase());
        }
        user.setImageUrl(adminUserDto.getImageUrl());
        user.setLangKey(adminUserDto.getLangKey());
        user.setActivated(false);
        user.setActivationKey(RandomStringUtils.random(DEF_COUNT, 0, 0, true, true, null, SECURE_RANDOM));
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        user.setAuthorities(authorities);
        userRepository.save(user);
        this.clearUserCaches(user);
        return user;
    }

    @Override
    public boolean removeNonActivatedUser(User existingUser) {
        if (existingUser.isActivated()) {
            return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        this.clearUserCaches(existingUser);
        return true;
    }

    @Override
    public User createUser(AdminUserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername().toLowerCase());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail().toLowerCase());
        }
        user.setImageUrl(userDto.getImageUrl());
        if (userDto.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE);
        } else {
            user.setLangKey(userDto.getLangKey());
        }
        String encryptedPassword = passwordEncoder.encode(RandomStringUtils.random(DEF_COUNT, 0, 0, true, true, null, SECURE_RANDOM));
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomStringUtils.random(DEF_COUNT, 0, 0, true, true, null, SECURE_RANDOM));
        user.setResetDate(Instant.now());
        user.setActivated(true);
        if (userDto.getAuthorities() != null) {
            Set<Authority> authorities = userDto
                    .getAuthorities()
                    .stream()
                    .map(authorityRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            user.setAuthorities(authorities);
        }
        userRepository.save(user);
        this.clearUserCaches(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    @Override
    public Optional<AdminUserDto> updateUser(AdminUserDto userDto) {
        return Optional
                .of(userRepository.findById(userDto.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(user -> {
                    this.clearUserCaches(user);
                    user.setUsername(userDto.getUsername().toLowerCase());
                    user.setFirstName(userDto.getFirstName());
                    user.setLastName(userDto.getLastName());
                    if (userDto.getEmail() != null) {
                        user.setEmail(userDto.getEmail().toLowerCase());
                    }
                    user.setImageUrl(userDto.getImageUrl());
                    user.setActivated(userDto.isActivated());
                    user.setLangKey(userDto.getLangKey());
                    Set<Authority> managedAuthorities = user.getAuthorities();
                    managedAuthorities.clear();
                    userDto
                            .getAuthorities()
                            .stream()
                            .map(authorityRepository::findById)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .forEach(managedAuthorities::add);
                    this.clearUserCaches(user);
                    log.debug("Changed Information for User: {}", user);
                    return user;
                })
                .map(AdminUserDto::new);
    }

    @Override
    public void deleteUser(String login) {
        userRepository
                .findOneByUsername(login)
                .ifPresent(user -> {
                    userRepository.delete(user);
                    this.clearUserCaches(user);
                    log.debug("Deleted User: {}", user);
                });
    }

    @Override
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        SecurityUtils
                .getCurrentUserLogin()
                .flatMap(userRepository::findOneByUsername)
                .ifPresent(user -> {
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    if (email != null) {
                        user.setEmail(email.toLowerCase());
                    }
                    user.setLangKey(langKey);
                    user.setImageUrl(imageUrl);
                    this.clearUserCaches(user);
                    log.debug("Changed Information for User: {}", user);
                });
    }

    @Override
    @Transactional
    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils
                .getCurrentUserLogin()
                .flatMap(userRepository::findOneByUsername)
                .ifPresent(user -> {
                    String currentEncryptedPassword = user.getPassword();
                    if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                        throw new InvalidPasswordException();
                    }
                    String encryptedPassword = passwordEncoder.encode(newPassword);
                    user.setPassword(encryptedPassword);
                    this.clearUserCaches(user);
                    log.debug("Changed password for User: {}", user);
                });

    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminUserDto> getAllManagedUsers() {
        return userRepository.findAll().stream().map(AdminUserDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllPublicUsers() {
        return userRepository.findAllByIdNotNullAndActivatedIsTrue().stream().map(UserDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByUsername(String username) {
        return userRepository.findOneWithAuthoritiesByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByUsername);
    }

    @Override
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUser() {
        userRepository
                .findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
                .forEach(user -> {
                    log.debug("Deleting not activated user {}", user.getUsername());
                    userRepository.delete(user);
                    this.clearUserCaches(user);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }

    @Override
    public void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getUsername());
        if (user.getEmail() != null) {
            Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
        }
    }
}
