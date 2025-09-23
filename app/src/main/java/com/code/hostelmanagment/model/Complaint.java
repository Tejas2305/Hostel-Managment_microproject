package com.code.hostelmanagment.model;

import java.util.Date;

public class Complaint {
    public enum ComplaintStatus {
        PENDING,
        ASSIGNED,
        IN_PROGRESS,
        RESOLVED,
        CLOSED,
        REJECTED
    }

    public enum ComplaintCategory {
        MAINTENANCE,
        ELECTRICAL,
        PLUMBING,
        CLEANING,
        SECURITY,
        FOOD,
        INTERNET,
        NOISE,
        OTHER
    }

    public enum Priority {
        LOW,
        MEDIUM,
        HIGH,
        URGENT
    }

    private String complaintId;
    private String studentId;
    private String title;
    private String description;
    private ComplaintCategory category;
    private Priority priority;
    private ComplaintStatus status;
    private Date submittedDate;
    private Date assignedDate;
    private Date resolvedDate;
    private String assignedTo;
    private String[] attachments;
    private String location;
    private String resolution;
    private int rating; // Student rating for resolution
    private String feedback;

    // Constructors
    public Complaint() {}

    public Complaint(String complaintId, String studentId, String title, String description, ComplaintCategory category) {
        this.complaintId = complaintId;
        this.studentId = studentId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.submittedDate = new Date();
        this.status = ComplaintStatus.PENDING;
        this.priority = Priority.MEDIUM;
    }

    // Getters and Setters
    public String getComplaintId() { return complaintId; }
    public void setComplaintId(String complaintId) { this.complaintId = complaintId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public ComplaintCategory getCategory() { return category; }
    public void setCategory(ComplaintCategory category) { this.category = category; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public ComplaintStatus getStatus() { return status; }
    public void setStatus(ComplaintStatus status) { this.status = status; }

    public Date getSubmittedDate() { return submittedDate; }
    public void setSubmittedDate(Date submittedDate) { this.submittedDate = submittedDate; }

    public Date getAssignedDate() { return assignedDate; }
    public void setAssignedDate(Date assignedDate) { this.assignedDate = assignedDate; }

    public Date getResolvedDate() { return resolvedDate; }
    public void setResolvedDate(Date resolvedDate) { this.resolvedDate = resolvedDate; }

    public String getAssignedTo() { return assignedTo; }
    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }

    public String[] getAttachments() { return attachments; }
    public void setAttachments(String[] attachments) { this.attachments = attachments; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getResolution() { return resolution; }
    public void setResolution(String resolution) { this.resolution = resolution; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
}