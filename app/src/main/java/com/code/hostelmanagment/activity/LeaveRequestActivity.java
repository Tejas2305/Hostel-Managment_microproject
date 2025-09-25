package com.code.hostelmanagment.activity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.code.hostelmanagment.R;
import com.code.hostelmanagment.model.LeaveRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class LeaveRequestActivity extends AppCompatActivity {

    private EditText editTextReason, editTextFromDate, editTextToDate, editTextDestination, editTextEmergencyContact;
    private Spinner spinnerLeaveType;
    private Button buttonSubmitLeave, buttonViewHistory;
    private ListView listViewLeaveHistory;
    
    private SharedPreferences sharedPreferences;
    private Calendar fromCalendar, toCalendar;
    private List<LeaveRequest> leaveHistory;
    private SimpleDateFormat dateFormat;
    private boolean isSelectingFromDate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_request);

        setupToolbar();
        initializeViews();
        setupSpinners();
        setupClickListeners();
        loadLeaveHistory();

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        fromCalendar = Calendar.getInstance();
        toCalendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Leave Request");
        }
    }

    private void initializeViews() {
        editTextReason = findViewById(R.id.editTextReason);
        editTextFromDate = findViewById(R.id.editTextFromDate);
        editTextToDate = findViewById(R.id.editTextToDate);
        editTextDestination = findViewById(R.id.editTextDestination);
        editTextEmergencyContact = findViewById(R.id.editTextEmergencyContact);
        spinnerLeaveType = findViewById(R.id.spinnerLeaveType);
        buttonSubmitLeave = findViewById(R.id.buttonSubmitLeave);
        buttonViewHistory = findViewById(R.id.buttonViewHistory);
        listViewLeaveHistory = findViewById(R.id.listViewLeaveHistory);
        
        leaveHistory = new ArrayList<>();
    }

    private void setupSpinners() {
        String[] leaveTypes = {"Select Leave Type", "Home Leave", "Medical Leave", "Emergency Leave", "Academic Leave", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, leaveTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLeaveType.setAdapter(adapter);
    }

    private void setupClickListeners() {
        editTextFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelectingFromDate = true;
                showDatePicker();
            }
        });

        editTextToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelectingFromDate = false;
                showDatePicker();
            }
        });

        buttonSubmitLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitLeaveRequest();
            }
        });

        buttonViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleHistoryVisibility();
            }
        });
    }

    private void showDatePicker() {
        Calendar current = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
            this,
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    if (isSelectingFromDate) {
                        fromCalendar.set(Calendar.YEAR, year);
                        fromCalendar.set(Calendar.MONTH, month);
                        fromCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        editTextFromDate.setText(dateFormat.format(fromCalendar.getTime()));
                    } else {
                        toCalendar.set(Calendar.YEAR, year);
                        toCalendar.set(Calendar.MONTH, month);
                        toCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        editTextToDate.setText(dateFormat.format(toCalendar.getTime()));
                    }
                }
            },
            current.get(Calendar.YEAR),
            current.get(Calendar.MONTH),
            current.get(Calendar.DAY_OF_MONTH)
        );
        
        // Set minimum date to today
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void submitLeaveRequest() {
        String reason = editTextReason.getText().toString().trim();
        String fromDate = editTextFromDate.getText().toString().trim();
        String toDate = editTextToDate.getText().toString().trim();
        String destination = editTextDestination.getText().toString().trim();
        String emergencyContact = editTextEmergencyContact.getText().toString().trim();
        String leaveTypeStr = spinnerLeaveType.getSelectedItem().toString();

        if (validateInput(reason, fromDate, toDate, destination, emergencyContact, leaveTypeStr)) {
            // Create leave request object
            String leaveId = UUID.randomUUID().toString();
            String studentId = sharedPreferences.getString("user_email", "");
            
            LeaveRequest leaveRequest = new LeaveRequest(
                leaveId, 
                studentId, 
                getLeaveType(leaveTypeStr), 
                fromCalendar.getTime(), 
                toCalendar.getTime(), 
                reason
            );
            leaveRequest.setDestination(destination);
            leaveRequest.setEmergencyContact(emergencyContact);
            
            // In a real app, this would send request to backend
            // For now, simulate successful submission
            leaveHistory.add(leaveRequest);
            saveLeaveRequest(leaveRequest);
            
            Toast.makeText(this, "Leave request submitted successfully!\nStatus: Pending Approval", Toast.LENGTH_LONG).show();
            clearForm();
            updateHistoryList();
        }
    }

    private boolean validateInput(String reason, String fromDate, String toDate, String destination, String emergencyContact, String leaveType) {
        if (reason.isEmpty()) {
            editTextReason.setError("Reason is required");
            return false;
        }

        if (fromDate.isEmpty()) {
            editTextFromDate.setError("From date is required");
            return false;
        }

        if (toDate.isEmpty()) {
            editTextToDate.setError("To date is required");
            return false;
        }

        if (toCalendar.before(fromCalendar)) {
            editTextToDate.setError("To date must be after from date");
            return false;
        }

        if (destination.isEmpty()) {
            editTextDestination.setError("Destination is required");
            return false;
        }

        if (emergencyContact.isEmpty()) {
            editTextEmergencyContact.setError("Emergency contact is required");
            return false;
        }

        if (leaveType.equals("Select Leave Type")) {
            Toast.makeText(this, "Please select leave type", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private LeaveRequest.LeaveType getLeaveType(String leaveTypeStr) {
        switch (leaveTypeStr) {
            case "Home Leave":
                return LeaveRequest.LeaveType.HOME_LEAVE;
            case "Medical Leave":
                return LeaveRequest.LeaveType.MEDICAL_LEAVE;
            case "Emergency Leave":
                return LeaveRequest.LeaveType.EMERGENCY_LEAVE;
            case "Academic Leave":
                return LeaveRequest.LeaveType.ACADEMIC_LEAVE;
            default:
                return LeaveRequest.LeaveType.OTHER;
        }
    }

    private void saveLeaveRequest(LeaveRequest leaveRequest) {
        // In a real app, this would save to local database or send to server
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("last_leave_request", leaveRequest.getLeaveType() + " - " + leaveRequest.getStatus());
        editor.apply();
    }

    private void clearForm() {
        editTextReason.setText("");
        editTextFromDate.setText("");
        editTextToDate.setText("");
        editTextDestination.setText("");
        editTextEmergencyContact.setText("");
        spinnerLeaveType.setSelection(0);
    }

    private void loadLeaveHistory() {
        // In a real app, this would load from database
        leaveHistory = new ArrayList<>();
        updateHistoryList();
    }

    private void updateHistoryList() {
        if (leaveHistory.isEmpty()) {
            List<String> historyItems = new ArrayList<>();
            historyItems.add("No leave requests found");
            
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyItems);
            listViewLeaveHistory.setAdapter(adapter);
        } else {
            List<String> historyItems = new ArrayList<>();
            for (LeaveRequest leave : leaveHistory) {
                String fromDate = dateFormat.format(leave.getFromDate());
                String toDate = dateFormat.format(leave.getToDate());
                historyItems.add(leave.getLeaveType() + "\n" + fromDate + " to " + toDate + "\nStatus: " + leave.getStatus());
            }
            
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyItems);
            listViewLeaveHistory.setAdapter(adapter);
        }
    }

    private void toggleHistoryVisibility() {
        if (listViewLeaveHistory.getVisibility() == View.GONE) {
            listViewLeaveHistory.setVisibility(View.VISIBLE);
            buttonViewHistory.setText("Hide History");
        } else {
            listViewLeaveHistory.setVisibility(View.GONE);
            buttonViewHistory.setText("View History");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
