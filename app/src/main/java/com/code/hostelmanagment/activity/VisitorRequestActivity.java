package com.code.hostelmanagment.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.code.hostelmanagment.R;
import com.code.hostelmanagment.model.Visitor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class VisitorRequestActivity extends AppCompatActivity {

    private EditText editTextVisitorName, editTextPhone, editTextPurpose, editTextVisitDate, editTextVisitTime;
    private Spinner spinnerIdProofType;
    private EditText editTextIdProofNumber;
    private Button buttonSubmitRequest, buttonViewHistory;
    private ListView listViewVisitorHistory;
    
    private SharedPreferences sharedPreferences;
    private Calendar visitCalendar;
    private List<Visitor> visitorHistory;
    private SimpleDateFormat dateFormat, timeFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_request);

        setupToolbar();
        initializeViews();
        setupSpinners();
        setupClickListeners();
        loadVisitorHistory();

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        visitCalendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Visitor Request");
        }
    }

    private void initializeViews() {
        editTextVisitorName = findViewById(R.id.editTextVisitorName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPurpose = findViewById(R.id.editTextPurpose);
        editTextVisitDate = findViewById(R.id.editTextVisitDate);
        editTextVisitTime = findViewById(R.id.editTextVisitTime);
        spinnerIdProofType = findViewById(R.id.spinnerIdProofType);
        editTextIdProofNumber = findViewById(R.id.editTextIdProofNumber);
        buttonSubmitRequest = findViewById(R.id.buttonSubmitRequest);
        buttonViewHistory = findViewById(R.id.buttonViewHistory);
        listViewVisitorHistory = findViewById(R.id.listViewVisitorHistory);
        
        visitorHistory = new ArrayList<>();
    }

    private void setupSpinners() {
        String[] idProofTypes = {"Select ID Proof", "Aadhaar Card", "Driving License", "Voter ID", "PAN Card", "Passport", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, idProofTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdProofType.setAdapter(adapter);
    }

    private void setupClickListeners() {
        editTextVisitDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        editTextVisitTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        buttonSubmitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitVisitorRequest();
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
                    visitCalendar.set(Calendar.YEAR, year);
                    visitCalendar.set(Calendar.MONTH, month);
                    visitCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    editTextVisitDate.setText(dateFormat.format(visitCalendar.getTime()));
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

    private void showTimePicker() {
        Calendar current = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(
            this,
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    visitCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    visitCalendar.set(Calendar.MINUTE, minute);
                    editTextVisitTime.setText(timeFormat.format(visitCalendar.getTime()));
                }
            },
            current.get(Calendar.HOUR_OF_DAY),
            current.get(Calendar.MINUTE),
            true
        );
        timePickerDialog.show();
    }

    private void submitVisitorRequest() {
        String visitorName = editTextVisitorName.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String purpose = editTextPurpose.getText().toString().trim();
        String visitDate = editTextVisitDate.getText().toString().trim();
        String visitTime = editTextVisitTime.getText().toString().trim();
        String idProofType = spinnerIdProofType.getSelectedItem().toString();
        String idProofNumber = editTextIdProofNumber.getText().toString().trim();

        if (validateInput(visitorName, phone, purpose, visitDate, visitTime, idProofType, idProofNumber)) {
            // Create visitor object
            String visitorId = UUID.randomUUID().toString();
            String studentId = sharedPreferences.getString("user_email", "");
            
            Visitor visitor = new Visitor(visitorId, visitorName, phone, studentId, purpose);
            visitor.setIdProofType(idProofType);
            visitor.setIdProofNumber(idProofNumber);
            visitor.setRequestedDate(visitCalendar.getTime());
            
            // Generate OTP for visitor
            String otp = generateOTP();
            visitor.setOtpCode(otp);
            
            // In a real app, this would send request to backend
            // For now, simulate successful submission
            visitorHistory.add(visitor);
            saveVisitorRequest(visitor);
            
            Toast.makeText(this, "Visitor request submitted successfully!\nOTP: " + otp, Toast.LENGTH_LONG).show();
            clearForm();
            updateHistoryList();
        }
    }

    private boolean validateInput(String name, String phone, String purpose, String date, String time, String idProof, String idNumber) {
        if (name.isEmpty()) {
            editTextVisitorName.setError("Visitor name is required");
            return false;
        }

        if (phone.isEmpty()) {
            editTextPhone.setError("Phone number is required");
            return false;
        }

        if (phone.length() < 10) {
            editTextPhone.setError("Please enter a valid phone number");
            return false;
        }

        if (purpose.isEmpty()) {
            editTextPurpose.setError("Purpose of visit is required");
            return false;
        }

        if (date.isEmpty()) {
            editTextVisitDate.setError("Visit date is required");
            return false;
        }

        if (time.isEmpty()) {
            editTextVisitTime.setError("Visit time is required");
            return false;
        }

        if (idProof.equals("Select ID Proof")) {
            Toast.makeText(this, "Please select ID proof type", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (idNumber.isEmpty()) {
            editTextIdProofNumber.setError("ID proof number is required");
            return false;
        }

        return true;
    }

    private String generateOTP() {
        return String.valueOf((int)(Math.random() * 9000) + 1000);
    }

    private void saveVisitorRequest(Visitor visitor) {
        // In a real app, this would save to local database or send to server
        // For now, we'll just add to the history list
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("last_visitor_request", visitor.getVisitorName() + " - " + visitor.getPurpose());
        editor.apply();
    }

    private void clearForm() {
        editTextVisitorName.setText("");
        editTextPhone.setText("");
        editTextPurpose.setText("");
        editTextVisitDate.setText("");
        editTextVisitTime.setText("");
        editTextIdProofNumber.setText("");
        spinnerIdProofType.setSelection(0);
    }

    private void loadVisitorHistory() {
        // In a real app, this would load from database
        // For now, create sample data
        visitorHistory = new ArrayList<>();
        updateHistoryList();
    }

    private void updateHistoryList() {
        if (visitorHistory.isEmpty()) {
            // Add sample data for demonstration
            List<String> historyItems = new ArrayList<>();
            historyItems.add("No visitor requests found");
            
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyItems);
            listViewVisitorHistory.setAdapter(adapter);
        } else {
            List<String> historyItems = new ArrayList<>();
            for (Visitor visitor : visitorHistory) {
                historyItems.add(visitor.getVisitorName() + " - " + visitor.getPurpose() + "\nStatus: " + visitor.getStatus());
            }
            
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyItems);
            listViewVisitorHistory.setAdapter(adapter);
        }
    }

    private void toggleHistoryVisibility() {
        if (listViewVisitorHistory.getVisibility() == View.GONE) {
            listViewVisitorHistory.setVisibility(View.VISIBLE);
            buttonViewHistory.setText("Hide History");
        } else {
            listViewVisitorHistory.setVisibility(View.GONE);
            buttonViewHistory.setText("View History");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
