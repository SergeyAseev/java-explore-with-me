package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.exception.ExistsElementException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserMapper;
import ru.practicum.user.model.User;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserDto createUser(UserDto userDto) {

        if (userRepository.findByName(userDto.getName()) != null) {
            throw new ExistsElementException("User with this name already exist");
        }
        User user = UserMapper.toUser(userDto);
        String email = user.getEmail();
        if (!Objects.isNull(email)) {
            try {
                log.info("User with email {} was created", email);
                User createdUser = userRepository.save(user);
                return UserMapper.toUserDto(createdUser);
            } catch (RuntimeException e) {
                log.warn("User with email {} exists", email);
                throw new ExistsElementException("User exists");
            }
        } else {
            throw new ValidationException(String.format("Email %s not found", email));
        }
    }

    public List<UserDto> retrieveUsers(List<Long> userIds, Integer from, Integer size) {

        Pageable pageable = PageRequest.of(from / size, size);

        if (userIds.isEmpty()) {
            return userRepository.findAll(pageable)
                    .stream()
                    .map(UserMapper::toUserDto)
                    .collect(Collectors.toList());
        }

        return userRepository.findAllByIdIn(userIds, pageable)
                .stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    public void deleteUser(Long userId) {

        getUserById(userId);
        userRepository.deleteById(userId);
    }

    public UserDto getUserById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with ID %s not found", userId)));
        return UserMapper.toUserDto(user);
    }
}
