package com.code.hostelmanagment.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.code.hostelmanagment.R;
import com.code.hostelmanagment.model.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPhone, editTextPassword, editTextConfirmPassword;
    private EditText editTextEmergencyContact, editTextRollNumber;
    private Spinner spinnerRole;
    private Button buttonRegister, buttonLoginLink;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupToolbar();
        initializeViews();
        setupRoleSpinner();
        setupClickListeners();

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle("Register");
            }
        }
    }

    private void initializeViews() {
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editTextEmergencyContact = findViewById(R.id.editTextEmergencyContact);
        editTextRollNumber = findViewById(R.id.editTextRollNumber);
        spinnerRole = findViewById(R.id.spinnerRole);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonLoginLink = findViewById(R.id.buttonLoginLink);
    }

    private void setupRoleSpinner() {
        String[] roles = {"Select Role", "Student", "Warden", "Admin", "Security"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapter);
    }

    private void setupClickListeners() {
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegistration();
            }
        });

        buttonLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void attemptRegistration() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();
        String emergencyContact = editTextEmergencyContact.getText().toString().trim();
        String rollNumber = editTextRollNumber.getText().toString().trim();
        String role = spinnerRole.getSelectedItem().toString();

        if (validateInput(name, email, phone, password, confirmPassword, role)) {
            // In a real app, this would send data to backend
            // For now, we'll simulate successful registration
            registerUser(name, email, phone, password, role, emergencyContact, rollNumber);
        }
    }

    private boolean validateInput(String name, String email, String phone, String password, String confirmPassword, String role) {
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

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            return false;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password must be at least 6 characters");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            editTextConfirmPassword.setError("Passwords do not match");
            return false;
        }

        if (role.equals("Select Role")) {
            Toast.makeText(this, "Please select a role", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void registerUser(String name, String email, String phone, String password, String role, String emergencyContact, String rollNumber) {
        // In a real app, this would make API call to register user
        // For now, simulate successful registration
        
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_logged_in", true);
        editor.putString("user_email", email);
        editor.putString("user_name", name);
        editor.putString("user_role", role);
        editor.putString("user_phone", phone);
        editor.putString("emergency_contact", emergencyContact);
        if (role.equals("Student")) {
            editor.putString("roll_number", rollNumber);
        }
        editor.apply();

        Toast.makeText(this, "Registration successful! Welcome " + name, Toast.LENGTH_SHORT).show();
        
        // Navigate to appropriate dashboard
        navigateToDashboard(role);
    }

    private void navigateToDashboard(String role) {
        Intent intent;
        switch (role) {
            case "Security":
                intent = new Intent(this, SecurityDashboardActivity.class);
                break;
            case "Warden":
            case "Admin":
                intent = new Intent(this, AdminDashboardActivity.class);
                break;
            default:
                intent = new Intent(this, StudentDashboardActivity.class);
                break;
        }
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}