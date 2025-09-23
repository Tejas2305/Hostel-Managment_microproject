package com.code.hostelmanagment.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.code.hostelmanagment.R;

public class SecurityVisitorManagementActivity extends AppCompatActivity {

    private ListView listViewVisitors;
    private TextView textViewEmptyState;
    private FloatingActionButton fabScanQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_visitor_management);

        setupToolbar();
        initializeViews();
        setupClickListeners();
        loadVisitorData();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Visitor Management");
        }
    }

    private void initializeViews() {
        listViewVisitors = findViewById(R.id.listViewVisitors);
        textViewEmptyState = findViewById(R.id.textViewEmptyState);
        fabScanQR = findViewById(R.id.fabScanQR);
    }

    private void setupClickListeners() {
        fabScanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement QR scanner
                // For now, show a placeholder message
            }
        });
    }

    private void loadVisitorData() {
        // Mock data - in real app, this would load from database/API
        // For now, show empty state
        textViewEmptyState.setVisibility(View.VISIBLE);
        listViewVisitors.setVisibility(View.GONE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
