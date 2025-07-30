package com.example.leaveapplication.dto;

public class UpdateLeaveRequestStatusDto {
    private String status;
    private String managerComment;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getManagerComment() {
        return managerComment;
    }

    public void setManagerComment(String managerComment) {
        this.managerComment = managerComment;
    }
}
