package com.example.leaveapplication.service;

import com.example.leaveapplication.dto.CreateLeaveRequestDto;
import com.example.leaveapplication.dto.LeaveBalanceDto;
import com.example.leaveapplication.dto.LeaveRequestDto;
import com.example.leaveapplication.dto.UpdateLeaveRequestStatusDto;
import com.example.leaveapplication.entity.LeaveBalance;
import com.example.leaveapplication.entity.LeaveRequest;
import com.example.leaveapplication.entity.LeaveType;
import com.example.leaveapplication.entity.User;
import com.example.leaveapplication.repository.LeaveBalanceRepository;
import com.example.leaveapplication.repository.LeaveRequestRepository;
import com.example.leaveapplication.repository.LeaveTypeRepository;
import com.example.leaveapplication.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepo;
    private final UserRepository userRepo;
    private final LeaveTypeRepository leaveTypeRepo;
    private final LeaveBalanceRepository leaveBalanceRepo;

    public LeaveRequestService(LeaveRequestRepository leaveRequestRepo,
                               UserRepository userRepo,
                               LeaveBalanceRepository leaveBalanceRepo,
                               LeaveTypeRepository leaveTypeRepo) {
        this.leaveRequestRepo = leaveRequestRepo;
        this.userRepo = userRepo;
        this.leaveBalanceRepo = leaveBalanceRepo;
        this.leaveTypeRepo = leaveTypeRepo;
    }

    public LeaveRequestDto createLeaveRequest(CreateLeaveRequestDto dto) {
        User user = userRepo.findById(dto.getUserId()).orElseThrow();
        LeaveType leaveType = leaveTypeRepo.findById(dto.getLeaveTypeId()).orElseThrow();

        LeaveRequest request = new LeaveRequest();
        request.setUser(user);
        request.setLeaveType(leaveType);
        request.setStartDate(dto.getStartDate());
        request.setEndDate(dto.getEndDate());
        request.setReason(dto.getReason());
        request.setStatus("Pending");

        LeaveRequest saved = leaveRequestRepo.save(request);
        return mapToDto(saved);
    }

    public List<LeaveRequestDto> getAllLeaveRequests() {
        return leaveRequestRepo.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<LeaveRequestDto> getLeaveRequestsByUserId(Long userId) {
        return leaveRequestRepo.findByUserId(userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public LeaveRequestDto updateLeaveStatus(Long id, UpdateLeaveRequestStatusDto dto) {
        LeaveRequest request = leaveRequestRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        if ("Approved".equalsIgnoreCase(dto.getStatus())) {
            long days = ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate()) + 1;
            int year = request.getStartDate().getYear();
            Long userId = request.getUser().getId();
            Long leaveTypeId = request.getLeaveType().getId();

            LeaveBalance balance = leaveBalanceRepo
                    .findByUserIdAndLeaveTypeIdAndYear(userId, leaveTypeId, year).orElseThrow(() -> new RuntimeException("Remaining leave days not found."));

            BigDecimal newDays = balance.getRemainingDays().subtract(BigDecimal.valueOf(days));
            if (newDays.compareTo(BigDecimal.ZERO) < 0) {
                throw new RuntimeException("The remaining days are not enough.");
            }

            balance.setRemainingDays(newDays);
            leaveBalanceRepo.save(balance);
        }

        request.setStatus(dto.getStatus());
        request.setManagerComment(dto.getManagerComment());
        LeaveRequest updated = leaveRequestRepo.save(request);
        return mapToDto(updated);
    }

    private LeaveRequestDto mapToDto(LeaveRequest entity) {
        LeaveRequestDto dto = new LeaveRequestDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setUsername(entity.getUser().getUsername());
        dto.setLeaveTypeId(entity.getLeaveType().getId());
        dto.setLeaveTypeName(entity.getLeaveType().getName());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setReason(entity.getReason());
        dto.setStatus(entity.getStatus());
        dto.setManagerComment(entity.getManagerComment());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
    public long getTotalLeaveDays(Long userId, int year) {
        List<LeaveRequest> approvedRequests = leaveRequestRepo.findApprovedByUserAndYear(userId, year);

        long totalDays = 0;
        for (LeaveRequest req : approvedRequests) {
            long days = ChronoUnit.DAYS.between(req.getStartDate(), req.getEndDate()) + 1;
            totalDays += days;
        }

        return totalDays;
    }

    public long countPendingRequestsByUser(Long userId) {
        return leaveRequestRepo.countByUserIdAndStatus(userId, "Pending");
    }
    public List<LeaveRequestDto> getRequestsByStatus(String status) {
        List<LeaveRequest> requests = leaveRequestRepo.findByStatusIgnoreCase(status);
        return requests.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}

