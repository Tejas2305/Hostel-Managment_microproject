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

import com.code.hostelmanagment.R;
import com.code.hostelmanagment.model.User;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Spinner spinnerRole;
    private Button buttonLogin, buttonRegister;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();
        setupRoleSpinner();
        setupClickListeners();

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        
        // Check if user is already logged in
        if (isUserLoggedIn()) {
            navigateToDashboard();
        }
    }

    private void initializeViews() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        spinnerRole = findViewById(R.id.spinnerRole);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);
    }

    private void setupRoleSpinner() {
        String[] roles = {"Select Role", "Student", "Warden", "Admin", "Security"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapter);
    }

    private void setupClickListeners() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void attemptLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String selectedRole = spinnerRole.getSelectedItem().toString();

        if (validateInput(email, password, selectedRole)) {
            // Simulate login validation (in real app, this would be API call)
            if (authenticateUser(email, password, selectedRole)) {
                saveUserSession(email, selectedRole);
                navigateToDashboard();
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateInput(String email, String password, String role) {
        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            return false;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            return false;
        }

        if (role.equals("Select Role")) {
            Toast.makeText(this, "Please select a role", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean authenticateUser(String email, String password, String role) {
        // Mock authentication - in real app, this would be API call
        // For demo purposes, accept any valid email/password combination
        return email.contains("@") && password.length() >= 6;
    }

    private void saveUserSession(String email, String role) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_email", email);
        editor.putString("user_role", role);
        editor.putBoolean("is_logged_in", true);
        editor.apply();
    }

    private boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean("is_logged_in", false);
    }

    private void navigateToDashboard() {
        String role = sharedPreferences.getString("user_role", "Student");
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
}