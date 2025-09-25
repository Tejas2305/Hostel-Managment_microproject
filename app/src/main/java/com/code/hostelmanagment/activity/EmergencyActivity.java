package com.code.hostelmanagment.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.code.hostelmanagment.R;

public class EmergencyActivity extends AppCompatActivity {

    private static final int CALL_PERMISSION_REQUEST_CODE = 100;
    
    private TextView textViewEmergencyInfo;
    private CardView cardSOS, cardWarden, cardSecurity, cardAmbulance, cardFireDept, cardPolice;
    private Button buttonEmergencyContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        setupToolbar();
        initializeViews();
        setupClickListeners();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Emergency");
        }
    }

    private void initializeViews() {
        textViewEmergencyInfo = findViewById(R.id.textViewEmergencyInfo);
        cardSOS = findViewById(R.id.cardSOS);
        cardWarden = findViewById(R.id.cardWarden);
        cardSecurity = findViewById(R.id.cardSecurity);
        cardAmbulance = findViewById(R.id.cardAmbulance);
        cardFireDept = findViewById(R.id.cardFireDept);
        cardPolice = findViewById(R.id.cardPolice);
        buttonEmergencyContacts = findViewById(R.id.buttonEmergencyContacts);

        textViewEmergencyInfo.setText("In case of emergency, use the buttons below to get immediate help. The SOS button will alert all emergency services.");
    }

    private void setupClickListeners() {
        cardSOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                triggerSOS();
            }
        });

        cardWarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall("9876543210", "Warden");
            }
        });

        cardSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall("9876543211", "Security");
            }
        });

        cardAmbulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall("108", "Ambulance");
            }
        });

        cardFireDept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall("101", "Fire Department");
            }
        });

        cardPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall("100", "Police");
            }
        });

        buttonEmergencyContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Show detailed emergency contacts list
                Toast.makeText(EmergencyActivity.this, "Emergency Contacts:\nWarden: 9876543210\nSecurity: 9876543211\nAmbulance: 108\nFire: 101\nPolice: 100", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void triggerSOS() {
        // In a real app, this would:
        // 1. Send location to emergency services
        // 2. Send alerts to warden, security, and emergency contacts
        // 3. Start recording audio/video
        // 4. Send push notifications to relevant authorities
        
        Toast.makeText(this, "SOS Alert Triggered!\nEmergency services have been notified.\nHelp is on the way.", Toast.LENGTH_LONG).show();
        
        // For demonstration, call security
        makeCall("9876543211", "Security (SOS)");
    }

    private void makeCall(String phoneNumber, String contactName) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_REQUEST_CODE);
            return;
        }

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        
        try {
            startActivity(callIntent);
            Toast.makeText(this, "Calling " + contactName + "...", Toast.LENGTH_SHORT).show();
        } catch (SecurityException e) {
            // Fallback to dialer
            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
            dialIntent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(dialIntent);
            Toast.makeText(this, "Opening dialer for " + contactName, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (requestCode == CALL_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted. Please try calling again.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied. Using dialer instead.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
