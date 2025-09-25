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

public class AdminDashboardActivity extends AppCompatActivity {

    private TextView textViewWelcome, textViewSystemStats, textViewPendingTasks;
    private CardView cardUserManagement, cardRoomManagement, cardVisitorApprovals, 
                    cardLeaveApprovals, cardComplaintManagement, cardNoticeManagement,
                    cardFeeManagement, cardReportsAnalytics, cardSystemSettings;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        initializeViews();
        setupUserInfo();
        setupClickListeners();

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
    }

    private void initializeViews() {
        textViewWelcome = findViewById(R.id.textViewWelcome);
        textViewSystemStats = findViewById(R.id.textViewSystemStats);
        textViewPendingTasks = findViewById(R.id.textViewPendingTasks);
        
        cardUserManagement = findViewById(R.id.cardUserManagement);
        cardRoomManagement = findViewById(R.id.cardRoomManagement);
        cardVisitorApprovals = findViewById(R.id.cardVisitorApprovals);
        cardLeaveApprovals = findViewById(R.id.cardLeaveApprovals);
        cardComplaintManagement = findViewById(R.id.cardComplaintManagement);
        cardNoticeManagement = findViewById(R.id.cardNoticeManagement);
        cardFeeManagement = findViewById(R.id.cardFeeManagement);
        cardReportsAnalytics = findViewById(R.id.cardReportsAnalytics);
        cardSystemSettings = findViewById(R.id.cardSystemSettings);
    }

    private void setupUserInfo() {
        String userName = sharedPreferences.getString("user_name", "Admin");
        String userRole = sharedPreferences.getString("user_role", "Admin");
        
        textViewWelcome.setText("Welcome, " + userName);
        
        // Mock system statistics
        textViewSystemStats.setText("Total Students: 120 | Occupied Rooms: 85 | Active Visitors: 3");
        textViewPendingTasks.setText("Pending Approvals: 8 | Open Complaints: 5");
    }

    private void setupClickListeners() {
        cardUserManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement User Management Activity
                startActivity(new Intent(AdminDashboardActivity.this, SettingsActivity.class));
            }
        });

        cardRoomManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, RoomManagementActivity.class));
            }
        });

        cardVisitorApprovals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, SecurityVisitorManagementActivity.class));
            }
        });

        cardLeaveApprovals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, LeaveRequestActivity.class));
            }
        });

        cardComplaintManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, ComplaintActivity.class));
            }
        });

        cardNoticeManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, NoticesActivity.class));
            }
        });

        cardFeeManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement Fee Management Activity
                startActivity(new Intent(AdminDashboardActivity.this, SettingsActivity.class));
            }
        });

        cardReportsAnalytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, SecurityReportsActivity.class));
            }
        });

        cardSystemSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, SettingsActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin_dashboard, menu);
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