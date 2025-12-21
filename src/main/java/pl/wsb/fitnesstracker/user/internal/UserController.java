package pl.wsb.fitnesstracker.user.internal;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.UserSimpleDto;
import pl.wsb.fitnesstracker.user.api.UserEmailDto;
import pl.wsb.fitnesstracker.user.api.User;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

/**
 * UserController is responsible for handling HTTP requests related to user operations.
 * It provides endpoints for retrieving and creating users.
 */
@RestController
@RequestMapping("/v1/users")
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    public UserController(UserServiceImpl userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    /**
     * Retrieves all users.
     *
     * @return A list of {@link UserDto} representing all users.
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Retrieves all users with simplified information.
     *
     * @return A list of {@link UserSimpleDto} representing all users with basic details.
     */
    @GetMapping("/simple")
    public List<UserSimpleDto> getAllUsersSimple() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toSimpleDto)
                .collect(Collectors.toList());
    }
    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return A {@link ResponseEntity} containing the {@link UserDto} if found, or a 404 Not Found status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {

        return userService.getUser(id)
                .map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves users by their email.
     *
     * @param email The email of the user to retrieve.
     * @return A list of {@link UserEmailDto} representing users with the specified email.
     */
    @GetMapping("/email")
    public List<UserEmailDto> getUserByEmail(@RequestParam String email) {
     
        return userService.getUserByEmail(email)
            .stream()
            .collect(Collectors.toList());
    }

    /**
     * Creates a new user.
     *
     * @param userDto The {@link UserDto} containing user details.
     * @return A {@link ResponseEntity} containing the created {@link UserDto} and the location URI.
     */
    @PostMapping()
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        User user = new User(
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email()
        );
        User savedUser = userService.createUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).body(userMapper.toDto(savedUser));
    }
    /**
     * Updates an existing user.
     *
     * @param userId  The ID of the user to update.
     * @param userDto The {@link UserDto} containing updated user details.
     * @return A {@link ResponseEntity} containing the updated {@link UserDto} if found, or a 404 Not Found status.
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto){
        return userService.getUser(userId)
                .map(existing -> {
                    existing.setFirstName(userDto.firstName());
                    existing.setLastName(userDto.lastName());
                    existing.setBirthdate(userDto.birthdate());
                    existing.setEmail(userDto.email());
                    User saved = userService.updateUser(existing);
                    return ResponseEntity.ok(userMapper.toDto(saved));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    /**
     * Deletes a user by their ID.
     *
     * @param userId The ID of the user to delete.
     * @return A {@link ResponseEntity} with no content if deleted, or a 404 Not Found status.
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUserById(@PathVariable Long userId) {
        return userService.getUser(userId)
                .map(user -> {
                    userService.deleteUserById(user);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());

    }
    /**
     * Retrieves all users older than the specified date.
     *
     * @param time The date to compare user birthdates against.
     * @return A list of {@link UserDto} representing users older than the specified date.
     */
    @GetMapping("/older/{time}")
    public List<UserDto> getAllUsersOlderThan(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate time) {
        return userService.findAllUsersOlderThan(time)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

}