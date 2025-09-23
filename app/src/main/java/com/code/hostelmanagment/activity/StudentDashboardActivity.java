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

public class StudentDashboardActivity extends AppCompatActivity {

    private TextView textViewWelcome, textViewRoomNumber, textViewNotifications;
    private CardView cardRoomManagement, cardVisitorRequest, cardLeaveRequest, 
                    cardComplaints, cardNotices, cardEmergency;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        initializeViews();
        setupUserInfo();
        setupClickListeners();
    }

    private void initializeViews() {
        textViewWelcome = findViewById(R.id.textViewWelcome);
        textViewRoomNumber = findViewById(R.id.textViewRoomNumber);
        textViewNotifications = findViewById(R.id.textViewNotifications);
        
        cardRoomManagement = findViewById(R.id.cardRoomManagement);
        cardVisitorRequest = findViewById(R.id.cardVisitorRequest);
        cardLeaveRequest = findViewById(R.id.cardLeaveRequest);
        cardComplaints = findViewById(R.id.cardComplaints);
        cardNotices = findViewById(R.id.cardNotices);
        cardEmergency = findViewById(R.id.cardEmergency);

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
    }

    private void setupUserInfo() {
        String userEmail = sharedPreferences.getString("user_email", "Student");
        String userName = userEmail.split("@")[0]; // Extract name from email
        textViewWelcome.setText("Welcome, " + userName);
        
        // Mock room number - in real app, this would come from user profile
        textViewRoomNumber.setText("Room: A-101");
        textViewNotifications.setText("3 new notifications");
    }

    private void setupClickListeners() {
        cardRoomManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentDashboardActivity.this, RoomManagementActivity.class));
            }
        });

        cardVisitorRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentDashboardActivity.this, VisitorRequestActivity.class));
            }
        });

        cardLeaveRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentDashboardActivity.this, LeaveRequestActivity.class));
            }
        });

        cardComplaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentDashboardActivity.this, ComplaintActivity.class));
            }
        });

        cardNotices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentDashboardActivity.this, NoticesActivity.class));
            }
        });

        cardEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentDashboardActivity.this, EmergencyActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_student_dashboard, menu);
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