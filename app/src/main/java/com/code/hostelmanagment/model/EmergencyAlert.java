package com.code.hostelmanagment.model;

import java.util.Date;

public class EmergencyAlert {
    public enum AlertType {
        PANIC,
        FIRE,
        MEDICAL,
        SECURITY_BREACH,
        NATURAL_DISASTER,
        POWER_OUTAGE,
        OTHER
    }

    public enum AlertStatus {
        ACTIVE,
        ACKNOWLEDGED,
        IN_PROGRESS,
        RESOLVED,
        FALSE_ALARM
    }

    public enum Priority {
        LOW,
        MEDIUM,
        HIGH,
        CRITICAL
    }

    private String alertId;
    private String triggeredBy;
    private AlertType alertType;
    private Priority priority;
    private AlertStatus status;
    private String description;
    private String location;
    private double latitude;
    private double longitude;
    private Date triggeredTime;
    private Date acknowledgedTime;
    private Date resolvedTime;
    private String acknowledgedBy;
    private String resolvedBy;
    private String[] responders;
    private String resolution;
    private String[] attachments;

    // Constructors
    public EmergencyAlert() {}

    public EmergencyAlert(String alertId, String triggeredBy, AlertType alertType, String description, String location) {
        this.alertId = alertId;
        this.triggeredBy = triggeredBy;
        this.alertType = alertType;
        this.description = description;
        this.location = location;
        this.triggeredTime = new Date();
        this.status = AlertStatus.ACTIVE;
        this.priority = alertType == AlertType.PANIC ? Priority.CRITICAL : Priority.HIGH;
    }

    // Getters and Setters
    public String getAlertId() { return alertId; }
    public void setAlertId(String alertId) { this.alertId = alertId; }

    public String getTriggeredBy() { return triggeredBy; }
    public void setTriggeredBy(String triggeredBy) { this.triggeredBy = triggeredBy; }

    public AlertType getAlertType() { return alertType; }
    public void setAlertType(AlertType alertType) { this.alertType = alertType; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public AlertStatus getStatus() { return status; }
    public void setStatus(AlertStatus status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public Date getTriggeredTime() { return triggeredTime; }
    public void setTriggeredTime(Date triggeredTime) { this.triggeredTime = triggeredTime; }

    public Date getAcknowledgedTime() { return acknowledgedTime; }
    public void setAcknowledgedTime(Date acknowledgedTime) { this.acknowledgedTime = acknowledgedTime; }

    public Date getResolvedTime() { return resolvedTime; }
    public void setResolvedTime(Date resolvedTime) { this.resolvedTime = resolvedTime; }

    public String getAcknowledgedBy() { return acknowledgedBy; }
    public void setAcknowledgedBy(String acknowledgedBy) { this.acknowledgedBy = acknowledgedBy; }

    public String getResolvedBy() { return resolvedBy; }
    public void setResolvedBy(String resolvedBy) { this.resolvedBy = resolvedBy; }

    public String[] getResponders() { return responders; }
    public void setResponders(String[] responders) { this.responders = responders; }

    public String getResolution() { return resolution; }
    public void setResolution(String resolution) { this.resolution = resolution; }

    public String[] getAttachments() { return attachments; }
    public void setAttachments(String[] attachments) { this.attachments = attachments; }
}