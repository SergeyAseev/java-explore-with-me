package ru.practicum.user.service;

import ru.practicum.user.dto.UserDto;

import java.util.List;

public interface UserService {

    /**
     * Добавление нового пользователя
     *
     * @param userDto экземпляр пользователя-дто для создания
     * @return экземпляр созданного пользователя
     */
    UserDto createUser(UserDto userDto);

    /**
     * Получение информации о пользователях
     *
     * @return Список пользователей
     */
    List<UserDto> retrieveUsers(List<Long> userIds, Integer from, Integer size);

    /**
     * Удаление пользователя
     *
     * @param userId ID пользователя, которого удаляем
     */
    void deleteUser(Long userId);
}
