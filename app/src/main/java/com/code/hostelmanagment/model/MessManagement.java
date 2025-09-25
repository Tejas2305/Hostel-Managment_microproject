package com.code.hostelmanagment.model;

import java.util.Date;

public class MessManagement {
    public enum MealType {
        BREAKFAST,
        LUNCH,
        SNACKS,
        DINNER
    }

    private String menuId;
    private Date date;
    private MealType mealType;
    private String[] items;
    private String[] ingredients;
    private boolean isVegetarian;
    private double price;
    private int availableQuantity;
    private String specialNote;

    // Constructors
    public MessManagement() {}

    public MessManagement(String menuId, Date date, MealType mealType, String[] items, double price) {
        this.menuId = menuId;
        this.date = date;
        this.mealType = mealType;
        this.items = items;
        this.price = price;
        this.isVegetarian = true;
    }

    // Getters and Setters
    public String getMenuId() { return menuId; }
    public void setMenuId(String menuId) { this.menuId = menuId; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public MealType getMealType() { return mealType; }
    public void setMealType(MealType mealType) { this.mealType = mealType; }

    public String[] getItems() { return items; }
    public void setItems(String[] items) { this.items = items; }

    public String[] getIngredients() { return ingredients; }
    public void setIngredients(String[] ingredients) { this.ingredients = ingredients; }

    public boolean isVegetarian() { return isVegetarian; }
    public void setVegetarian(boolean vegetarian) { isVegetarian = vegetarian; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getAvailableQuantity() { return availableQuantity; }
    public void setAvailableQuantity(int availableQuantity) { this.availableQuantity = availableQuantity; }

    public String getSpecialNote() { return specialNote; }
    public void setSpecialNote(String specialNote) { this.specialNote = specialNote; }
}

// Fee Management Model
class FeeManagement {
    public enum FeeType {
        HOSTEL_FEE,
        MESS_FEE,
        SECURITY_DEPOSIT,
        MAINTENANCE_FEE,
        LATE_FINE,
        DAMAGE_FINE,
        OTHER
    }

    public enum PaymentStatus {
        PENDING,
        PAID,
        OVERDUE,
        PARTIALLY_PAID,
        REFUNDED
    }

    private String feeId;
    private String studentId;
    private FeeType feeType;
    private double amount;
    private Date dueDate;
    private Date paidDate;
    private PaymentStatus status;
    private String description;
    private String paymentMode;
    private String transactionId;
    private double paidAmount;
    private double balance;

    // Constructors
    public FeeManagement() {}

    public FeeManagement(String feeId, String studentId, FeeType feeType, double amount, Date dueDate) {
        this.feeId = feeId;
        this.studentId = studentId;
        this.feeType = feeType;
        this.amount = amount;
        this.dueDate = dueDate;
        this.status = PaymentStatus.PENDING;
        this.balance = amount;
    }

    // Getters and Setters
    public String getFeeId() { return feeId; }
    public void setFeeId(String feeId) { this.feeId = feeId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public FeeType getFeeType() { return feeType; }
    public void setFeeType(FeeType feeType) { this.feeType = feeType; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    public Date getPaidDate() { return paidDate; }
    public void setPaidDate(Date paidDate) { this.paidDate = paidDate; }

    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public double getPaidAmount() { return paidAmount; }
    public void setPaidAmount(double paidAmount) { this.paidAmount = paidAmount; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}