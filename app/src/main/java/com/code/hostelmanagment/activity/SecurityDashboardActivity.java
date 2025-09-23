package com.code.hostelmanagment.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.code.hostelmanagment.R;

public class SecurityDashboardActivity extends AppCompatActivity {

    private TextView textViewWelcome, textViewActiveVisitors, textViewAlerts;
    private CardView cardVisitorManagement, cardEmergencyAlerts, cardEntryExitLogs, 
                    cardSecurityNotices, cardAccessControl, cardReports;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_dashboard);

        initializeViews();
        setupUserInfo();
        setupClickListeners();
    }

    private void initializeViews() {
        textViewWelcome = findViewById(R.id.textViewWelcome);
        textViewActiveVisitors = findViewById(R.id.textViewActiveVisitors);
        textViewAlerts = findViewById(R.id.textViewAlerts);
        
        cardVisitorManagement = findViewById(R.id.cardVisitorManagement);
        cardEmergencyAlerts = findViewById(R.id.cardEmergencyAlerts);
        cardEntryExitLogs = findViewById(R.id.cardEntryExitLogs);
        cardSecurityNotices = findViewById(R.id.cardSecurityNotices);
        cardAccessControl = findViewById(R.id.cardAccessControl);
        cardReports = findViewById(R.id.cardReports);

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
    }

    private void setupUserInfo() {
        String userEmail = sharedPreferences.getString("user_email", "Security");
        String userName = userEmail.split("@")[0];
        textViewWelcome.setText("Welcome, " + userName);
        
        // Mock security data
        textViewActiveVisitors.setText("5 active visitors");
        textViewAlerts.setText("2 pending alerts");
    }

    private void setupClickListeners() {
        cardVisitorManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecurityDashboardActivity.this, SecurityVisitorManagementActivity.class));
            }
        });

        cardEmergencyAlerts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecurityDashboardActivity.this, EmergencyAlertsActivity.class));
            }
        });

        cardEntryExitLogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecurityDashboardActivity.this, EntryExitLogsActivity.class));
            }
        });

        cardSecurityNotices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecurityDashboardActivity.this, SecurityNoticesActivity.class));
            }
        });

        cardAccessControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecurityDashboardActivity.this, AccessControlActivity.class));
            }
        });

        cardReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecurityDashboardActivity.this, SecurityReportsActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_security_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.action_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        } else if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.action_logout) {
            logout();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}