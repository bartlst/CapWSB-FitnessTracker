package pl.wsb.fitnesstracker.user.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
/**
 * Data Transfer Object (DTO) representing detailed user information.
 * <p>
 * This class is used for transferring user data between different layers
 * of the application, particularly in API responses.
 *
 * @param id        the unique identifier of the user, may be {@code null} if not yet persisted
 * @param firstName the first name of the user
 * @param lastName  the last name of the user
 * @param birthdate the birthdate of the user in ISO format (yyyy-MM-dd)
 * @param email     the email address of the user
 */
public record UserDto(@Nullable Long id, String firstName, String lastName,
                      @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthdate,
                      String email) {

}
