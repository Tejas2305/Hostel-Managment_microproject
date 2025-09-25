package com.code.hostelmanagment.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.code.hostelmanagment.R;

public class ProfileActivity extends AppCompatActivity {

    private TextView textViewUserRole, textViewRoomNumber;
    private EditText editTextName, editTextEmail, editTextPhone, editTextEmergencyContact, editTextRollNumber;
    private Button buttonUpdateProfile, buttonChangePassword;
    
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setupToolbar();
        initializeViews();
        setupClickListeners();
        loadUserProfile();

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("My Profile");
        }
    }

    private void initializeViews() {
        textViewUserRole = findViewById(R.id.textViewUserRole);
        textViewRoomNumber = findViewById(R.id.textViewRoomNumber);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmergencyContact = findViewById(R.id.editTextEmergencyContact);
        editTextRollNumber = findViewById(R.id.editTextRollNumber);
        buttonUpdateProfile = findViewById(R.id.buttonUpdateProfile);
        buttonChangePassword = findViewById(R.id.buttonChangePassword);
        
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
    }

    private void setupClickListeners() {
        buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement change password functionality
                Toast.makeText(ProfileActivity.this, "Change password functionality will be implemented", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadUserProfile() {
        // Load user data from SharedPreferences
        String name = sharedPreferences.getString("user_name", "");
        String email = sharedPreferences.getString("user_email", "");
        String phone = sharedPreferences.getString("user_phone", "");
        String role = sharedPreferences.getString("user_role", "Student");
        String emergencyContact = sharedPreferences.getString("emergency_contact", "");
        String rollNumber = sharedPreferences.getString("roll_number", "");
        String roomNumber = sharedPreferences.getString("room_number", "Not Assigned");

        // Populate fields
        editTextName.setText(name);
        editTextEmail.setText(email);
        editTextPhone.setText(phone);
        editTextEmergencyContact.setText(emergencyContact);
        editTextRollNumber.setText(rollNumber);
        
        textViewUserRole.setText(role);
        textViewRoomNumber.setText("Room: " + roomNumber);

        // Hide roll number field for non-students
        if (!role.equals("Student")) {
            editTextRollNumber.setVisibility(View.GONE);
            findViewById(R.id.textInputLayoutRollNumber).setVisibility(View.GONE);
        }
    }

    private void updateProfile() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String emergencyContact = editTextEmergencyContact.getText().toString().trim();
        String rollNumber = editTextRollNumber.getText().toString().trim();

        if (validateInput(name, email, phone)) {
            // Save updated profile
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user_name", name);
            editor.putString("user_email", email);
            editor.putString("user_phone", phone);
            editor.putString("emergency_contact", emergencyContact);
            
            String userRole = sharedPreferences.getString("user_role", "Student");
            if (userRole.equals("Student")) {
                editor.putString("roll_number", rollNumber);
            }
            
            editor.apply();

            Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateInput(String name, String email, String phone) {
        if (name.isEmpty()) {
            editTextName.setError("Name is required");
            return false;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            return false;
        }

        if (!email.contains("@")) {
            editTextEmail.setError("Please enter a valid email");
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

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
