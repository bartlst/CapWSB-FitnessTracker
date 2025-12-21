package pl.wsb.fitnesstracker.user.internal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserEmailDto;
import pl.wsb.fitnesstracker.user.api.UserProvider;
import pl.wsb.fitnesstracker.user.api.UserService;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;


/**
 * UserServiceImpl is the implementation of UserService and UserProvider interfaces.
 * It handles user-related operations such as creating, retrieving, updating, and deleting users.
 */
@Service
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;
    /**
     * Constructs a UserServiceImpl with the specified UserRepository.
     *
     * @param userRepository the UserRepository to be used for user operations
     */
    UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    /**
     * Creates a new user.
     *
     * @param user the User entity to be created
     * @return the created User entity
     * @throws IllegalArgumentException if the user already has an ID
     */
    @Override
    public User createUser(final User user) {
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }
    /**
     * Retrieves a user by their ID.
     *
     * @param userId the ID of the user to retrieve
     * @return an {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }
    /**
     * Retrieves users by their email fragment.
     *
     * @param email the email fragment to search for
     * @return a list of {@link UserEmailDto} representing users with matching email fragments
     */
    @Override
    public List<UserEmailDto> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }
    /**
     * Retrieves all users.
     *
     * @return a list of all User entities
     */
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    /**
     * Deletes a user.
     *
     * @param user the User entity to be deleted
     */
    public void deleteUserById(User user){
        userRepository.delete(user);
    }
    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to be deleted
     */
    public void deleteUserById(Long id) {
        userRepository.findById(id).ifPresent(userRepository::delete);
    }
    /**
     * Updates an existing user.
     *
     * @param user the User entity with updated information
     * @return the updated User entity
     * @throws IllegalArgumentException if the user does not have an ID
     */
    public User updateUser(final User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User must have ID to be updated");
        }
        return userRepository.save(user);
    }
    /**
     * Retrieves all users older than the specified date.
     *
     * @param date the date to compare user birthdates against
     * @return a list of users born before the specified date
     */
    public List<User> findAllUsersOlderThan(LocalDate date) {
        return userRepository.findAllByBirthdateBefore(date);
    }
}