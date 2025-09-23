package com.code.hostelmanagment.model;

import java.util.Date;

public class Room {
    public enum RoomType {
        SINGLE,
        DOUBLE,
        TRIPLE,
        QUAD
    }

    public enum RoomStatus {
        AVAILABLE,
        OCCUPIED,
        MAINTENANCE,
        RESERVED
    }

    private String roomId;
    private String roomNumber;
    private String floorNumber;
    private String blockName;
    private RoomType roomType;
    private RoomStatus status;
    private int capacity;
    private int currentOccupancy;
    private String[] amenities;
    private Date lastMaintenanceDate;

    // Constructors
    public Room() {}

    public Room(String roomId, String roomNumber, String floorNumber, String blockName, RoomType roomType, int capacity) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.floorNumber = floorNumber;
        this.blockName = blockName;
        this.roomType = roomType;
        this.capacity = capacity;
        this.currentOccupancy = 0;
        this.status = RoomStatus.AVAILABLE;
    }

    // Getters and Setters
    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public String getFloorNumber() { return floorNumber; }
    public void setFloorNumber(String floorNumber) { this.floorNumber = floorNumber; }

    public String getBlockName() { return blockName; }
    public void setBlockName(String blockName) { this.blockName = blockName; }

    public RoomType getRoomType() { return roomType; }
    public void setRoomType(RoomType roomType) { this.roomType = roomType; }

    public RoomStatus getStatus() { return status; }
    public void setStatus(RoomStatus status) { this.status = status; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getCurrentOccupancy() { return currentOccupancy; }
    public void setCurrentOccupancy(int currentOccupancy) { this.currentOccupancy = currentOccupancy; }

    public String[] getAmenities() { return amenities; }
    public void setAmenities(String[] amenities) { this.amenities = amenities; }

    public Date getLastMaintenanceDate() { return lastMaintenanceDate; }
    public void setLastMaintenanceDate(Date lastMaintenanceDate) { this.lastMaintenanceDate = lastMaintenanceDate; }

    public boolean isAvailable() {
        return status == RoomStatus.AVAILABLE && currentOccupancy < capacity;
    }
}