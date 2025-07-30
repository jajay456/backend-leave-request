package com.example.leaveapplication.service;

import com.example.leaveapplication.dto.LeaveBalanceDto;
import com.example.leaveapplication.dto.LeaveBalanceSummaryDto;
import com.example.leaveapplication.entity.LeaveBalance;
import com.example.leaveapplication.entity.LeaveRequest;
import com.example.leaveapplication.entity.LeaveType;
import com.example.leaveapplication.repository.LeaveBalanceRepository;
import com.example.leaveapplication.repository.LeaveRequestRepository;
import com.example.leaveapplication.repository.LeaveTypeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class LeaveBalanceService {

    private final LeaveBalanceRepository leaveBalanceRepo;
    private final LeaveTypeRepository leaveTypeRepo;
    private final LeaveRequestRepository leaveRequestRepo;

    public LeaveBalanceService(LeaveBalanceRepository leaveBalanceRepo,LeaveTypeRepository leaveTypeRepo,LeaveRequestRepository leaveRequestRepo) {
        this.leaveBalanceRepo = leaveBalanceRepo;
        this.leaveTypeRepo = leaveTypeRepo;
        this.leaveRequestRepo = leaveRequestRepo;
    }



    public BigDecimal getTotalRemainingDays(Long userId, int year) {
        return leaveBalanceRepo.sumRemainingDaysByUserAndYear(userId, year);
    }
    public List<LeaveBalanceSummaryDto> getRemainingLeaveByType(Long userId, int year) {
        List<LeaveBalance> balances = leaveBalanceRepo.findByUserIdAndYear(userId, year);
        return balances.stream()
                .map(b -> new LeaveBalanceSummaryDto(
                        b.getLeaveType().getId(),
                        b.getLeaveType().getName(),
                        b.getRemainingDays()
                ))
                .collect(Collectors.toList());
    }

}

