package pl.wsb.fitnesstracker.user.internal;

import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.UserEmailDto;
import pl.wsb.fitnesstracker.user.api.UserSimpleDto;

/**
 * UserMapper is responsible for converting User entities to various Data Transfer Objects (DTOs).
 */
@Component
class UserMapper {
    /**
     * Converts a User entity to a UserDto.
     *
     * @param user the User entity to convert
     * @return the corresponding UserDto
     */
    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }
    /**
     * Converts a User entity to a UserSimpleDto.
     *
     * @param user the User entity to convert
     * @return the corresponding UserSimpleDto
     */
    UserSimpleDto toSimpleDto(User user){
        return new UserSimpleDto(user.getId(),user.getFirstName(), user.getLastName());
    }
    /**
     * Converts a User entity to a UserEmailDto.
     *
     * @param user the User entity to convert
     * @return the corresponding UserEmailDto
     */
    UserEmailDto toUserEmailDto(User user){
            return new UserEmailDto(user.getId(),user.getEmail());

    }
}
