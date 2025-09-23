package com.code.hostelmanagment.model;

import java.util.Date;

public class Visitor {
    public enum VisitorStatus {
        PENDING,
        APPROVED,
        REJECTED,
        CHECKED_IN,
        CHECKED_OUT
    }

    private String visitorId;
    private String visitorName;
    private String phoneNumber;
    private String idProofType;
    private String idProofNumber;
    private String studentId; // Student being visited
    private String purpose;
    private Date requestedDate;
    private Date approvalDate;
    private Date checkInTime;
    private Date checkOutTime;
    private VisitorStatus status;
    private String approvedBy;
    private String qrCode;
    private String otpCode;

    // Constructors
    public Visitor() {}

    public Visitor(String visitorId, String visitorName, String phoneNumber, String studentId, String purpose) {
        this.visitorId = visitorId;
        this.visitorName = visitorName;
        this.phoneNumber = phoneNumber;
        this.studentId = studentId;
        this.purpose = purpose;
        this.requestedDate = new Date();
        this.status = VisitorStatus.PENDING;
    }

    // Getters and Setters
    public String getVisitorId() { return visitorId; }
    public void setVisitorId(String visitorId) { this.visitorId = visitorId; }

    public String getVisitorName() { return visitorName; }
    public void setVisitorName(String visitorName) { this.visitorName = visitorName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getIdProofType() { return idProofType; }
    public void setIdProofType(String idProofType) { this.idProofType = idProofType; }

    public String getIdProofNumber() { return idProofNumber; }
    public void setIdProofNumber(String idProofNumber) { this.idProofNumber = idProofNumber; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public Date getRequestedDate() { return requestedDate; }
    public void setRequestedDate(Date requestedDate) { this.requestedDate = requestedDate; }

    public Date getApprovalDate() { return approvalDate; }
    public void setApprovalDate(Date approvalDate) { this.approvalDate = approvalDate; }

    public Date getCheckInTime() { return checkInTime; }
    public void setCheckInTime(Date checkInTime) { this.checkInTime = checkInTime; }

    public Date getCheckOutTime() { return checkOutTime; }
    public void setCheckOutTime(Date checkOutTime) { this.checkOutTime = checkOutTime; }

    public VisitorStatus getStatus() { return status; }
    public void setStatus(VisitorStatus status) { this.status = status; }

    public String getApprovedBy() { return approvedBy; }
    public void setApprovedBy(String approvedBy) { this.approvedBy = approvedBy; }

    public String getQrCode() { return qrCode; }
    public void setQrCode(String qrCode) { this.qrCode = qrCode; }

    public String getOtpCode() { return otpCode; }
    public void setOtpCode(String otpCode) { this.otpCode = otpCode; }
}