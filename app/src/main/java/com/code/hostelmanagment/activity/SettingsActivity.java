package com.code.hostelmanagment.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.code.hostelmanagment.R;

public class SettingsActivity extends AppCompatActivity {

    private TextView textViewAppVersion, textViewUserInfo;
    private Switch switchNotifications, switchDarkMode, switchAutoSync;
    private CardView cardAbout, cardPrivacy, cardSupport, cardLogout;
    
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setupToolbar();
        initializeViews();
        setupClickListeners();
        loadSettings();

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Settings");
        }
    }

    private void initializeViews() {
        textViewAppVersion = findViewById(R.id.textViewAppVersion);
        textViewUserInfo = findViewById(R.id.textViewUserInfo);
        switchNotifications = findViewById(R.id.switchNotifications);
        switchDarkMode = findViewById(R.id.switchDarkMode);
        switchAutoSync = findViewById(R.id.switchAutoSync);
        cardAbout = findViewById(R.id.cardAbout);
        cardPrivacy = findViewById(R.id.cardPrivacy);
        cardSupport = findViewById(R.id.cardSupport);
        cardLogout = findViewById(R.id.cardLogout);
        
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        
        // Set app version
        textViewAppVersion.setText("Version 1.0.0");
    }

    private void setupClickListeners() {
        switchNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveSettingPreference("notifications_enabled", isChecked);
                Toast.makeText(SettingsActivity.this, 
                    isChecked ? "Notifications enabled" : "Notifications disabled", 
                    Toast.LENGTH_SHORT).show();
            }
        });

        switchDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveSettingPreference("dark_mode_enabled", isChecked);
                Toast.makeText(SettingsActivity.this, 
                    isChecked ? "Dark mode enabled" : "Light mode enabled", 
                    Toast.LENGTH_SHORT).show();
                // Note: In a real app, you would apply the theme change here
            }
        });

        switchAutoSync.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveSettingPreference("auto_sync_enabled", isChecked);
                Toast.makeText(SettingsActivity.this, 
                    isChecked ? "Auto sync enabled" : "Auto sync disabled", 
                    Toast.LENGTH_SHORT).show();
            }
        });

        cardAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAboutDialog();
            }
        });

        cardPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this, "Privacy Policy will be displayed here", Toast.LENGTH_SHORT).show();
            }
        });

        cardSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSupportInfo();
            }
        });

        cardLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void loadSettings() {
        // Load user info
        String userName = sharedPreferences.getString("user_name", "User");
        String userRole = sharedPreferences.getString("user_role", "Student");
        textViewUserInfo.setText(userName + " (" + userRole + ")");

        // Load setting preferences
        boolean notificationsEnabled = sharedPreferences.getBoolean("notifications_enabled", true);
        boolean darkModeEnabled = sharedPreferences.getBoolean("dark_mode_enabled", false);
        boolean autoSyncEnabled = sharedPreferences.getBoolean("auto_sync_enabled", true);

        switchNotifications.setChecked(notificationsEnabled);
        switchDarkMode.setChecked(darkModeEnabled);
        switchAutoSync.setChecked(autoSyncEnabled);
    }

    private void saveSettingPreference(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private void showAboutDialog() {
        String aboutText = "Hostel Management System v1.0.0\n\n" +
                          "A comprehensive mobile application for managing hostel operations, " +
                          "designed to streamline hostel management for students, wardens, admins, and security staff.\n\n" +
                          "Features:\n" +
                          "• Room Management\n" +
                          "• Visitor Management\n" +
                          "• Leave Requests\n" +
                          "• Complaint System\n" +
                          "• Emergency Services\n" +
                          "• Notices & Announcements\n\n" +
                          "Developed as a final year project for college hostel management.";
        
        Toast.makeText(this, aboutText, Toast.LENGTH_LONG).show();
    }

    private void showSupportInfo() {
        String supportText = "Support Information:\n\n" +
                           "For technical support, contact:\n" +
                           "Email: support@hostelapp.com\n" +
                           "Phone: +91-9876543210\n\n" +
                           "For emergencies, use the Emergency tab in the app.\n\n" +
                           "Office Hours: 9 AM - 6 PM (Mon-Fri)";
        
        Toast.makeText(this, supportText, Toast.LENGTH_LONG).show();
    }

    private void logout() {
        // Clear all user session data
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Redirect to login screen
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
