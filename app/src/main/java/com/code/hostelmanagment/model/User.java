package com.code.hostelmanagment.model;

public class User {
    public enum UserRole {
        STUDENT,
        WARDEN,
        ADMIN,
        SECURITY
    }

    private String userId;
    private String name;
    private String email;
    private String phoneNumber;
    private UserRole role;
    private String roomNumber; // For students
    private String emergencyContact;
    private boolean isActive;

    // Constructors
    public User() {}

    public User(String userId, String name, String email, String phoneNumber, UserRole role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.isActive = true;
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}