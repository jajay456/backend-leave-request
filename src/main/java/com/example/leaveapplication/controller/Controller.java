package com.example.leaveapplication.controller;

import com.example.leaveapplication.dto.*;
import com.example.leaveapplication.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class Controller {
    private final LeaveTypeService leaveTypeService;
    private final LeaveRequestService leaveRequestService;
    private final LeaveBalanceService leaveBalanceService;
    private final LeaveReportService leaveReportService;
    private final UserService userService;

    public Controller(UserService userService, LeaveReportService leaveReportService, LeaveTypeService leaveTypeService, LeaveRequestService leaveRequestService, LeaveBalanceService leaveBalanceService) {
        this.userService = userService;
        this.leaveTypeService = leaveTypeService;
        this.leaveRequestService = leaveRequestService;
        this.leaveBalanceService = leaveBalanceService;
        this.leaveReportService = leaveReportService;
    }

    @PostMapping("/insert-user")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.createUser(dto));
    }

    @GetMapping("/get-all-user")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/create-leave-requests")
    public ResponseEntity<LeaveRequestDto> createLeaveRequest(@RequestBody CreateLeaveRequestDto dto) {
        return ResponseEntity.ok(leaveRequestService.createLeaveRequest(dto));
    }


    @GetMapping("/get-all-leave-requests")
    public ResponseEntity<List<LeaveRequestDto>> getAllLeaveRequests() {
        return ResponseEntity.ok(leaveRequestService.getAllLeaveRequests());
    }

    @GetMapping("/get-all-leave-type")
    public ResponseEntity<List<LeaveTypeDto>> getAll() {
        return ResponseEntity.ok(leaveTypeService.findAll());
    }


    @GetMapping("/get-leave-requests-by-user/{userId}")
    public ResponseEntity<List<LeaveRequestDto>> getLeaveRequestsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(leaveRequestService.getLeaveRequestsByUserId(userId));
    }
    @GetMapping("/total-remaining-days/{userId}")
    public ResponseEntity<BigDecimal> getTotalRemainingDays(@PathVariable Long userId) {
        int year = LocalDate.now().getYear(); // หรือเลือกจาก user
        BigDecimal total = leaveBalanceService.getTotalRemainingDays(userId, year);
        return ResponseEntity.ok(total);
    }
    @GetMapping("/total-leave-days/{userId}")
    public ResponseEntity<Long> getTotalLeaveDays(@PathVariable Long userId) {
        int year = LocalDate.now().getYear();
        long totalDays = leaveRequestService.getTotalLeaveDays(userId, year);
        return ResponseEntity.ok(totalDays);
    }

    @GetMapping("/pending-count/{userId}")
    public long getPendingCountByUser(@PathVariable Long userId) {
        return leaveRequestService.countPendingRequestsByUser(userId);
    }

    @GetMapping("/leave-balance-all-type/{userId}")
    public List<LeaveBalanceSummaryDto> getLeaveBalanceByType(
            @PathVariable Long userId) {
        int year = LocalDate.now().getYear();
        return leaveBalanceService.getRemainingLeaveByType(userId, year);
    }

    @GetMapping("/api/leave-requests/pending")
    public List<LeaveRequestDto> getPendingRequests() {
        return leaveRequestService.getRequestsByStatus("Pending");
    }

}

