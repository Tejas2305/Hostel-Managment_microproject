package com.code.hostelmanagment.model;

import java.util.Date;

public class Notice {
    public enum NoticeCategory {
        GENERAL,
        MAINTENANCE,
        SECURITY,
        EVENT,
        MESS,
        ACADEMIC,
        EMERGENCY,
        FEES
    }

    public enum Priority {
        LOW,
        MEDIUM,
        HIGH,
        URGENT
    }

    private String noticeId;
    private String title;
    private String content;
    private NoticeCategory category;
    private Priority priority;
    private String authorId;
    private String authorName;
    private Date publishedDate;
    private Date expiryDate;
    private String[] attachments;
    private boolean isActive;
    private String targetAudience; // "ALL", "STUDENTS", "SECURITY", etc.

    // Constructors
    public Notice() {}

    public Notice(String noticeId, String title, String content, NoticeCategory category, String authorId) {
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
        this.category = category;
        this.authorId = authorId;
        this.publishedDate = new Date();
        this.isActive = true;
        this.priority = Priority.MEDIUM;
        this.targetAudience = "ALL";
    }

    // Getters and Setters
    public String getNoticeId() { return noticeId; }
    public void setNoticeId(String noticeId) { this.noticeId = noticeId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public NoticeCategory getCategory() { return category; }
    public void setCategory(NoticeCategory category) { this.category = category; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public String getAuthorId() { return authorId; }
    public void setAuthorId(String authorId) { this.authorId = authorId; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public Date getPublishedDate() { return publishedDate; }
    public void setPublishedDate(Date publishedDate) { this.publishedDate = publishedDate; }

    public Date getExpiryDate() { return expiryDate; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }

    public String[] getAttachments() { return attachments; }
    public void setAttachments(String[] attachments) { this.attachments = attachments; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public String getTargetAudience() { return targetAudience; }
    public void setTargetAudience(String targetAudience) { this.targetAudience = targetAudience; }
}