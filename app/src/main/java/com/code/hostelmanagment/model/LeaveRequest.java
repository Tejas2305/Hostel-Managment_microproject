package com.code.hostelmanagment.model;

import java.util.Date;

public class LeaveRequest {
    public enum LeaveStatus {
        PENDING,
        APPROVED,
        REJECTED,
        CANCELLED
    }

    public enum LeaveType {
        HOME_LEAVE,
        MEDICAL_LEAVE,
        EMERGENCY_LEAVE,
        ACADEMIC_LEAVE,
        OTHER
    }

    private String leaveId;
    private String studentId;
    private LeaveType leaveType;
    private Date fromDate;
    private Date toDate;
    private String reason;
    private String emergencyContact;
    private String destination;
    private LeaveStatus status;
    private Date appliedDate;
    private Date approvalDate;
    private String approvedBy;
    private String remarks;
    private String[] attachments;

    // Constructors
    public LeaveRequest() {}

    public LeaveRequest(String leaveId, String studentId, LeaveType leaveType, Date fromDate, Date toDate, String reason) {
        this.leaveId = leaveId;
        this.studentId = studentId;
        this.leaveType = leaveType;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.reason = reason;
        this.appliedDate = new Date();
        this.status = LeaveStatus.PENDING;
    }

    // Getters and Setters
    public String getLeaveId() { return leaveId; }
    public void setLeaveId(String leaveId) { this.leaveId = leaveId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public LeaveType getLeaveType() { return leaveType; }
    public void setLeaveType(LeaveType leaveType) { this.leaveType = leaveType; }

    public Date getFromDate() { return fromDate; }
    public void setFromDate(Date fromDate) { this.fromDate = fromDate; }

    public Date getToDate() { return toDate; }
    public void setToDate(Date toDate) { this.toDate = toDate; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public LeaveStatus getStatus() { return status; }
    public void setStatus(LeaveStatus status) { this.status = status; }

    public Date getAppliedDate() { return appliedDate; }
    public void setAppliedDate(Date appliedDate) { this.appliedDate = appliedDate; }

    public Date getApprovalDate() { return approvalDate; }
    public void setApprovalDate(Date approvalDate) { this.approvalDate = approvalDate; }

    public String getApprovedBy() { return approvedBy; }
    public void setApprovedBy(String approvedBy) { this.approvedBy = approvedBy; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String[] getAttachments() { return attachments; }
    public void setAttachments(String[] attachments) { this.attachments = attachments; }
}