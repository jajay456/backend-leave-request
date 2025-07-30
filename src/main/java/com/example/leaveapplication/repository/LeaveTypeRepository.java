package com.example.leaveapplication.repository;

import com.example.leaveapplication.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {}

