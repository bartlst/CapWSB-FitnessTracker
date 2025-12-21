package pl.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;

/**
 * Data Transfer Object (DTO) representing simplified user information.
 * <p>
 * This class is used for transferring basic user data between different layers
 * of the application, particularly in API responses.
 *
 * @param id        the unique identifier of the user, may be {@code null} if not yet persisted
 * @param firstName the first name of the user
 * @param lastName  the last name of the user
 */
public record UserSimpleDto(@Nullable Long id, String firstName, String lastName) {

}