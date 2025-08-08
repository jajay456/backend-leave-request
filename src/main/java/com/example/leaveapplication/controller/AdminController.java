package com.example.leaveapplication.controller;

import com.example.leaveapplication.dto.LeaveRequestDto;
import com.example.leaveapplication.dto.UpdateLeaveRequestStatusDto;
import com.example.leaveapplication.service.LeaveReportService;
import com.example.leaveapplication.service.LeaveRequestService;
import com.example.leaveapplication.service.LeaveTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final LeaveRequestService leaveRequestService;
    private final LeaveReportService  leaveReportService;
    public AdminController(LeaveRequestService leaveRequestService, LeaveReportService leaveReportService) {
        this.leaveRequestService = leaveRequestService;
        this.leaveReportService = leaveReportService;
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminOnly() {
        return "This is ADMIN ONLY area.";
    }
    @PutMapping("/update-leave-status/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LeaveRequestDto> updateLeaveStatus(
            @PathVariable Long id,
            @RequestBody UpdateLeaveRequestStatusDto dto) {
        return ResponseEntity.ok(leaveRequestService.updateLeaveStatus(id, dto));
    }
    @GetMapping("/leave-report")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Map<String, Object>>> getLeaveDetailsByUser(
            @RequestParam int month,
            @RequestParam int year,
            @RequestParam(required = false) String department
    ) {
        return ResponseEntity.ok(leaveReportService.getDetailsByUser(month, year, department));
    }
}
