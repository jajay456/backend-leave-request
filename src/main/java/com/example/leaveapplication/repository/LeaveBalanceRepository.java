package com.example.leaveapplication.repository;

import com.example.leaveapplication.entity.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {
    List<LeaveBalance> findByUserId(Long userId);
    Optional<LeaveBalance> findByUserIdAndLeaveTypeIdAndYear(Long userId, Long leaveTypeId, Integer year);
    @Query("SELECT SUM(lb.remainingDays) FROM LeaveBalance lb WHERE lb.user.id = :userId AND lb.year = :year")
    BigDecimal sumRemainingDaysByUserAndYear(@Param("userId") Long userId,
                                             @Param("year") Integer year);
    List<LeaveBalance> findByUserIdAndYear(Long userId, Integer year);
}