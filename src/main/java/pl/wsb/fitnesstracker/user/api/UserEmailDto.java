package pl.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;

/**
 * Data Transfer Object (DTO) representing a user's email address.
 *
 * This DTO is used to transfer basic information about a user's email
 * between application layers, e.g. in API responses or when updating data.
 *
 * @param id    the unique identifier of the record; may be {@code null} if the entity has not yet been persisted
 * @param email the user's email address; should be a valid email (validation is performed outside this DTO)
 */
public record UserEmailDto(@Nullable Long id,
                      String email) {

}
