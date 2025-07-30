package com.example.leaveapplication.repository;


import com.example.leaveapplication.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByUserId(Long userId);
    @Query("SELECT r FROM LeaveRequest r WHERE r.user.id = :userId AND r.leaveType.id  = :leaveTypeId AND r.status = :status AND YEAR(r.startDate) = :year")
    List<LeaveRequest> findByUserIdAndLeaveTypeIdAndStatusAndYear(
            @Param("userId") Long userId,
            @Param("leaveTypeId")Long leaveTypeId,
            @Param("status") String status,
            @Param("year") int year
    );
    @Query("SELECT r FROM LeaveRequest r WHERE MONTH(r.startDate) = :month AND YEAR(r.startDate) = :year AND (:department IS NULL OR :department = '' OR LOWER(r.user.department) = LOWER(:department)) AND r.status = 'Approved'")
    List<LeaveRequest> findByMonthYearAndDepartment(
            @Param("month") int month,
            @Param("year") int year,
            @Param("department") String department
    );
    @Query("SELECT r FROM LeaveRequest r WHERE r.user.id = :userId AND r.status = 'Approved' AND EXTRACT(YEAR FROM r.startDate) = :year")
    List<LeaveRequest> findApprovedByUserAndYear(@Param("userId") Long userId, @Param("year") int year);
    long countByUserIdAndStatus(Long userId, String status);

    List<LeaveRequest> findByStatusIgnoreCase(String status);
}

