package com.example.leaveapplication.dto;

import java.math.BigDecimal;

public class LeaveBalanceSummaryDto {
    private Long leaveTypeId;
    private String leaveTypeName;
    private BigDecimal remainingDays;

    public LeaveBalanceSummaryDto(Long leaveTypeId, String leaveTypeName, BigDecimal remainingDays) {
        this.leaveTypeId = leaveTypeId;
        this.leaveTypeName = leaveTypeName;
        this.remainingDays = remainingDays;
    }

    public String getLeaveTypeName() {
        return leaveTypeName;
    }

    public void setLeaveTypeName(String leaveTypeName) {
        this.leaveTypeName = leaveTypeName;
    }

    public BigDecimal getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(BigDecimal remainingDays) {
        this.remainingDays = remainingDays;
    }

    public Long getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(Long leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }
}
