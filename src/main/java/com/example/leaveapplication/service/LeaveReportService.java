package com.example.leaveapplication.service;

import com.example.leaveapplication.entity.LeaveRequest;
import com.example.leaveapplication.repository.LeaveRequestRepository;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class LeaveReportService {

    private final LeaveRequestRepository leaveRequestRepo;

    public LeaveReportService(LeaveRequestRepository leaveRequestRepo) {
        this.leaveRequestRepo = leaveRequestRepo;
    }

    public List<Map<String, Object>> getDetailsByUser(int month, int year, String department) {
        List<LeaveRequest> requests = leaveRequestRepo.findByMonthYearAndDepartment(month, year, department);

        Map<String, Map<String, Object>> summary = new LinkedHashMap<>();

        for (LeaveRequest req : requests) {
            String username = req.getUser().getUsername();
            String dept = req.getUser().getDepartment();
            String type = req.getLeaveType().getName();

            long days = ChronoUnit.DAYS.between(req.getStartDate(), req.getEndDate().plusDays(1));

            String key = username;

            summary.putIfAbsent(key, new HashMap<>());
            Map<String, Object> userData = summary.get(key);

            userData.put("username", username);
            userData.put("department", dept);


            int prev = ((Number) userData.getOrDefault(type, 0)).intValue();
            userData.put(type, prev + (int) days);
        }


        for (Map<String, Object> userData : summary.values()) {
            int total = userData.entrySet().stream()
                    .filter(e -> !e.getKey().equals("username") && !e.getKey().equals("department"))
                    .mapToInt(e -> ((Number) e.getValue()).intValue())
                    .sum();
            userData.put("total", total);
        }

        return new ArrayList<>(summary.values());
    }
}
