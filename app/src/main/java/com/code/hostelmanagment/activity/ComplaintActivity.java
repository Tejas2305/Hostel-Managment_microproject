package com.code.hostelmanagment.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android:widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.code.hostelmanagment.R;
import com.code.hostelmanagment.model.Complaint;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ComplaintActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDescription, editTextLocation;
    private Spinner spinnerCategory, spinnerPriority;
    private Button buttonSubmitComplaint, buttonViewHistory;
    private ListView listViewComplaintHistory;
    
    private SharedPreferences sharedPreferences;
    private List<Complaint> complaintHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        setupToolbar();
        initializeViews();
        setupSpinners();
        setupClickListeners();
        loadComplaintHistory();

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Submit Complaint");
        }
    }

    private void initializeViews() {
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextLocation = findViewById(R.id.editTextLocation);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerPriority = findViewById(R.id.spinnerPriority);
        buttonSubmitComplaint = findViewById(R.id.buttonSubmitComplaint);
        buttonViewHistory = findViewById(R.id.buttonViewHistory);
        listViewComplaintHistory = findViewById(R.id.listViewComplaintHistory);
        
        complaintHistory = new ArrayList<>();
    }

    private void setupSpinners() {
        // Category Spinner
        String[] categories = {"Select Category", "Maintenance", "Electrical", "Plumbing", "Cleaning", 
                              "Security", "Food", "Internet", "Noise", "Other"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        // Priority Spinner
        String[] priorities = {"Select Priority", "Low", "Medium", "High", "Urgent"};
        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, priorities);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPriority.setAdapter(priorityAdapter);
    }

    private void setupClickListeners() {
        buttonSubmitComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitComplaint();
            }
        });

        buttonViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleHistoryVisibility();
            }
        });
    }

    private void submitComplaint() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String location = editTextLocation.getText().toString().trim();
        String categoryStr = spinnerCategory.getSelectedItem().toString();
        String priorityStr = spinnerPriority.getSelectedItem().toString();

        if (validateInput(title, description, location, categoryStr, priorityStr)) {
            // Create complaint object
            String complaintId = UUID.randomUUID().toString();
            String studentId = sharedPreferences.getString("user_email", "");
            
            Complaint complaint = new Complaint(
                complaintId, 
                studentId, 
                title, 
                description, 
                getComplaintCategory(categoryStr)
            );
            complaint.setLocation(location);
            complaint.setPriority(getComplaintPriority(priorityStr));
            
            // In a real app, this would send request to backend
            // For now, simulate successful submission
            complaintHistory.add(complaint);
            saveComplaint(complaint);
            
            Toast.makeText(this, "Complaint submitted successfully!\nComplaint ID: " + complaintId.substring(0, 8), Toast.LENGTH_LONG).show();
            clearForm();
            updateHistoryList();
        }
    }

    private boolean validateInput(String title, String description, String location, String category, String priority) {
        if (title.isEmpty()) {
            editTextTitle.setError("Title is required");
            return false;
        }

        if (description.isEmpty()) {
            editTextDescription.setError("Description is required");
            return false;
        }

        if (location.isEmpty()) {
            editTextLocation.setError("Location is required");
            return false;
        }

        if (category.equals("Select Category")) {
            Toast.makeText(this, "Please select a category", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (priority.equals("Select Priority")) {
            Toast.makeText(this, "Please select priority level", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private Complaint.ComplaintCategory getComplaintCategory(String categoryStr) {
        switch (categoryStr) {
            case "Maintenance":
                return Complaint.ComplaintCategory.MAINTENANCE;
            case "Electrical":
                return Complaint.ComplaintCategory.ELECTRICAL;
            case "Plumbing":
                return Complaint.ComplaintCategory.PLUMBING;
            case "Cleaning":
                return Complaint.ComplaintCategory.CLEANING;
            case "Security":
                return Complaint.ComplaintCategory.SECURITY;
            case "Food":
                return Complaint.ComplaintCategory.FOOD;
            case "Internet":
                return Complaint.ComplaintCategory.INTERNET;
            case "Noise":
                return Complaint.ComplaintCategory.NOISE;
            default:
                return Complaint.ComplaintCategory.OTHER;
        }
    }

    private Complaint.Priority getComplaintPriority(String priorityStr) {
        switch (priorityStr) {
            case "Low":
                return Complaint.Priority.LOW;
            case "Medium":
                return Complaint.Priority.MEDIUM;
            case "High":
                return Complaint.Priority.HIGH;
            case "Urgent":
                return Complaint.Priority.URGENT;
            default:
                return Complaint.Priority.MEDIUM;
        }
    }

    private void saveComplaint(Complaint complaint) {
        // In a real app, this would save to local database or send to server
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("last_complaint", complaint.getTitle() + " - " + complaint.getStatus());
        editor.apply();
    }

    private void clearForm() {
        editTextTitle.setText("");
        editTextDescription.setText("");
        editTextLocation.setText("");
        spinnerCategory.setSelection(0);
        spinnerPriority.setSelection(0);
    }

    private void loadComplaintHistory() {
        // In a real app, this would load from database
        complaintHistory = new ArrayList<>();
        updateHistoryList();
    }

    private void updateHistoryList() {
        if (complaintHistory.isEmpty()) {
            List<String> historyItems = new ArrayList<>();
            historyItems.add("No complaints found");
            
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyItems);
            listViewComplaintHistory.setAdapter(adapter);
        } else {
            List<String> historyItems = new ArrayList<>();
            for (Complaint complaint : complaintHistory) {
                historyItems.add(complaint.getTitle() + "\n" + 
                               complaint.getCategory() + " | " + complaint.getPriority() + 
                               "\nStatus: " + complaint.getStatus());
            }
            
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyItems);
            listViewComplaintHistory.setAdapter(adapter);
        }
    }

    private void toggleHistoryVisibility() {
        if (listViewComplaintHistory.getVisibility() == View.GONE) {
            listViewComplaintHistory.setVisibility(View.VISIBLE);
            buttonViewHistory.setText("Hide History");
        } else {
            listViewComplaintHistory.setVisibility(View.GONE);
            buttonViewHistory.setText("View History");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
