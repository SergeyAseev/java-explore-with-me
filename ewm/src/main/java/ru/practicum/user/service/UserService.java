package ru.practicum.user.service;

import ru.practicum.user.dto.UserDto;

import java.util.List;

public interface UserService {

    /**
     * @param userDto
     * @return
     */
    UserDto createUser(UserDto userDto);

    /**
     * @return
     */
    List<UserDto> retrieveUsers(List<Long> userIds, Integer from, Integer size);

    /**
     * @param userId
     */
    void deleteUser(Long userId);
}
