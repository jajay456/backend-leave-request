package com.example.leaveapplication.service;

import com.example.leaveapplication.dto.UserDto;
import com.example.leaveapplication.entity.User;
import com.example.leaveapplication.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public UserDto createUser(UserDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setDepartment(dto.getDepartment());

        User saved = userRepo.save(user);

        dto.setId(saved.getId());
        dto.setCreatedAt(saved.getCreatedAt());
        return dto;
    }

    public List<UserDto> getAllUsers() {
        return userRepo.findAll().stream()
                .map(user -> {
                    UserDto dto = new UserDto();
                    dto.setId(user.getId());
                    dto.setUsername(user.getUsername());
                    dto.setEmail(user.getEmail());
                    dto.setRole(user.getRole());
                    dto.setDepartment(user.getDepartment());
                    dto.setCreatedAt(user.getCreatedAt());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}

