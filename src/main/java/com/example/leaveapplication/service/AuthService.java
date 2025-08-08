package com.example.leaveapplication.service;

import com.example.leaveapplication.dto.AuthResponse;
import com.example.leaveapplication.dto.LoginRequest;
import com.example.leaveapplication.dto.RegisterRequest;
import com.example.leaveapplication.dto.UserDto;
import com.example.leaveapplication.entity.LeaveBalance;
import com.example.leaveapplication.entity.LeaveType;
import com.example.leaveapplication.entity.User;
import com.example.leaveapplication.repository.LeaveBalanceRepository;
import com.example.leaveapplication.repository.LeaveTypeRepository;
import com.example.leaveapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class AuthService {
    private UserRepository userRepo;
    private PasswordEncoder encoder;
    private JwtService jwtService;
    private final LeaveBalanceRepository leaveBalanceRepository;
    private final LeaveTypeRepository leaveTypeRepository;

    public AuthService(UserRepository userRepo,PasswordEncoder encoder,JwtService jwtService,LeaveBalanceRepository leaveBalanceRepository, LeaveTypeRepository leaveTypeRepository) {
        this.leaveBalanceRepository = leaveBalanceRepository;
        this.leaveTypeRepository = leaveTypeRepository;
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setDepartment(request.getDepartment());
        user.setRole("USER");
        userRepo.save(user);

        int currentYear = LocalDate.now().getYear();
        List<Long> leaveTypeIds = List.of(1L, 2L, 3L);
        List<BigDecimal> days = List.of(
                BigDecimal.valueOf(10),
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(30)
        );

        for (int i = 0; i < leaveTypeIds.size(); i++) {
            Long leaveTypeId = leaveTypeIds.get(i);
            LeaveType leaveType = leaveTypeRepository.findById(leaveTypeId)
                    .orElseThrow(() -> new RuntimeException("LeaveType not found: " + leaveTypeId));

            LeaveBalance balance = new LeaveBalance();
            balance.setUser(user);
            balance.setLeaveType(leaveType);
            balance.setYear(currentYear);
            balance.setRemainingDays(days.get(i));

            leaveBalanceRepository.save(balance);
        }

        UserDto dto = mapToDto(user);
        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token, dto);
    }


    public AuthResponse login(LoginRequest request) {
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        UserDto dto = mapToDto(user);
        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token, dto);
    }

    private UserDto mapToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setDepartment(user.getDepartment());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}
