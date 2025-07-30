package com.example.leaveapplication.service;

import com.example.leaveapplication.dto.LeaveTypeDto;
import com.example.leaveapplication.repository.LeaveTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveTypeService {

    private final LeaveTypeRepository leaveTypeRepo;

    public LeaveTypeService(LeaveTypeRepository leaveTypeRepo) {
        this.leaveTypeRepo = leaveTypeRepo;
    }


    public List<LeaveTypeDto> findAll() {
        return leaveTypeRepo.findAll().stream().map(type -> {
            LeaveTypeDto dto = new LeaveTypeDto();
            dto.setId(type.getId());
            dto.setName(type.getName());
            dto.setDescription(type.getDescription());
            dto.setMaxDays(type.getMaxDays());
            return dto;
        }).collect(Collectors.toList());
    }
}