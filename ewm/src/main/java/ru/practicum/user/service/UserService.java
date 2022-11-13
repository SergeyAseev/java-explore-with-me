package ru.practicum.user.service;

import ru.practicum.user.dto.UserDto;

import java.util.List;

public interface UserService {

    /** Метод создание пользователя
     * @param userDto экземпляр пользователя-дто для создания
     * @return экземпляр созданного пользователя
     */
    UserDto createUser(UserDto userDto);

    /** Метод получения всех или выбранных пользователей
     * @return Список пользователей
     */
    List<UserDto> retrieveUsers(List<Long> userIds, Integer from, Integer size);

    /** метод удаления пользователя
     * @param userId ID пользователя, которого удаляем
     */
    void deleteUser(Long userId);
}
