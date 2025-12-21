package pl.wsb.fitnesstracker.user.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserEmailDto;

import java.time.LocalDate;
import java.util.List;

/**
 * UserRepository is responsible for performing CRUD operations on User entities.
 * It extends JpaRepository to leverage Spring Data JPA functionalities.
 */
interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds users whose email contains the specified fragment, case-insensitively.
     *
     * @param fragment the fragment to search for within email addresses
     * @return a list of UserEmailDto representing users with matching email fragments
     */
    default List<UserEmailDto> findByEmail(String fragment) {
        String lowered = fragment.toLowerCase();

        return findAll().stream()
                .filter(user -> user.getEmail().toLowerCase().contains(lowered))
                .map(user -> new UserEmailDto(user.getId(), user.getEmail()))
                .toList();
    }

    /**
     * Finds all users born before the specified date.
     *
     * @param date the date to compare birthdates against
     * @return a list of users born before the specified date
     */
    default List<User> findAllByBirthdateBefore(LocalDate date) {
        return findAll().stream()
                .filter(user -> user.getBirthdate().isBefore(date))
                .toList();
    }

}
