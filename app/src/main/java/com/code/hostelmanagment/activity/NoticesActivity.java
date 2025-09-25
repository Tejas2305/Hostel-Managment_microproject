package com.code.hostelmanagment.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.code.hostelmanagment.R;
import com.code.hostelmanagment.model.Notice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NoticesActivity extends AppCompatActivity {

    private TextView textViewNoticeCount;
    private Spinner spinnerCategory;
    private Button buttonFilterNotices;
    private ListView listViewNotices;
    
    private SharedPreferences sharedPreferences;
    private List<Notice> allNotices;
    private List<Notice> filteredNotices;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notices);

        setupToolbar();
        initializeViews();
        setupSpinners();
        setupClickListeners();
        loadNotices();

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Notices & Announcements");
        }
    }

    private void initializeViews() {
        textViewNoticeCount = findViewById(R.id.textViewNoticeCount);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        buttonFilterNotices = findViewById(R.id.buttonFilterNotices);
        listViewNotices = findViewById(R.id.listViewNotices);
        
        allNotices = new ArrayList<>();
        filteredNotices = new ArrayList<>();
    }

    private void setupSpinners() {
        String[] categories = {"All Categories", "General", "Maintenance", "Security", "Event", 
                              "Mess", "Academic", "Emergency", "Fees"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
    }

    private void setupClickListeners() {
        buttonFilterNotices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterNotices();
            }
        });
    }

    private void loadNotices() {
        // Load sample notices (in real app, this would come from server/database)
        createSampleNotices();
        filteredNotices.addAll(allNotices);
        updateNoticesList();
        updateNoticeCount();
    }

    private void createSampleNotices() {
        allNotices.clear();
        
        // Sample notices
        Notice notice1 = new Notice("1", "Hostel Fee Payment Due", 
            "Reminder: Hostel fees for the current semester are due by 30th of this month. Please make the payment through the online portal or visit the office.", 
            Notice.NoticeCategory.FEES, "admin");
        notice1.setAuthorName("Admin Office");
        notice1.setPriority(Notice.Priority.HIGH);
        notice1.setPublishedDate(new Date());
        allNotices.add(notice1);

        Notice notice2 = new Notice("2", "Mess Menu Updated", 
            "New vegetarian and non-vegetarian options have been added to the mess menu. Special dishes will be served on weekends.", 
            Notice.NoticeCategory.MESS, "mess-admin");
        notice2.setAuthorName("Mess Committee");
        notice2.setPriority(Notice.Priority.MEDIUM);
        notice2.setPublishedDate(new Date(System.currentTimeMillis() - 86400000)); // Yesterday
        allNotices.add(notice2);

        Notice notice3 = new Notice("3", "Cultural Fest 2024", 
            "Annual cultural fest will be organized next month. Students interested in participating should register by 15th. Prizes worth ₹50,000 to be won!", 
            Notice.NoticeCategory.EVENT, "cultural-head");
        notice3.setAuthorName("Cultural Committee");
        notice3.setPriority(Notice.Priority.MEDIUM);
        notice3.setPublishedDate(new Date(System.currentTimeMillis() - 172800000)); // 2 days ago
        allNotices.add(notice3);

        Notice notice4 = new Notice("4", "Maintenance Work Schedule", 
            "Electrical maintenance work will be carried out in Block A on Saturday from 10 AM to 4 PM. Please plan accordingly.", 
            Notice.NoticeCategory.MAINTENANCE, "maintenance");
        notice4.setAuthorName("Maintenance Team");
        notice4.setPriority(Notice.Priority.HIGH);
        notice4.setPublishedDate(new Date(System.currentTimeMillis() - 259200000)); // 3 days ago
        allNotices.add(notice4);

        Notice notice5 = new Notice("5", "Security Guidelines", 
            "For your safety, please ensure that:\n• Always carry your ID card\n• Do not share room keys\n• Report suspicious activities immediately\n• Follow visitor registration procedures", 
            Notice.NoticeCategory.SECURITY, "security-head");
        notice5.setAuthorName("Security Department");
        notice5.setPriority(Notice.Priority.URGENT);
        notice5.setPublishedDate(new Date(System.currentTimeMillis() - 432000000)); // 5 days ago
        allNotices.add(notice5);

        Notice notice6 = new Notice("6", "Internet Connectivity Upgrade", 
            "WiFi infrastructure is being upgraded. You may experience intermittent connectivity issues this week. High-speed internet will be available from next Monday.", 
            Notice.NoticeCategory.GENERAL, "it-admin");
        notice6.setAuthorName("IT Department");
        notice6.setPriority(Notice.Priority.MEDIUM);
        notice6.setPublishedDate(new Date(System.currentTimeMillis() - 604800000)); // 1 week ago
        allNotices.add(notice6);
    }

    private void filterNotices() {
        String selectedCategory = spinnerCategory.getSelectedItem().toString();
        filteredNotices.clear();

        if (selectedCategory.equals("All Categories")) {
            filteredNotices.addAll(allNotices);
        } else {
            Notice.NoticeCategory categoryFilter = getCategoryFromString(selectedCategory);
            for (Notice notice : allNotices) {
                if (notice.getCategory() == categoryFilter) {
                    filteredNotices.add(notice);
                }
            }
        }

        updateNoticesList();
        updateNoticeCount();
    }

    private Notice.NoticeCategory getCategoryFromString(String categoryStr) {
        switch (categoryStr) {
            case "General":
                return Notice.NoticeCategory.GENERAL;
            case "Maintenance":
                return Notice.NoticeCategory.MAINTENANCE;
            case "Security":
                return Notice.NoticeCategory.SECURITY;
            case "Event":
                return Notice.NoticeCategory.EVENT;
            case "Mess":
                return Notice.NoticeCategory.MESS;
            case "Academic":
                return Notice.NoticeCategory.ACADEMIC;
            case "Emergency":
                return Notice.NoticeCategory.EMERGENCY;
            case "Fees":
                return Notice.NoticeCategory.FEES;
            default:
                return Notice.NoticeCategory.GENERAL;
        }
    }

    private void updateNoticesList() {
        List<String> noticeItems = new ArrayList<>();
        
        for (Notice notice : filteredNotices) {
            String priorityText = getPriorityText(notice.getPriority());
            String dateText = dateFormat.format(notice.getPublishedDate());
            
            String noticeText = notice.getTitle() + "\n" +
                               notice.getCategory() + " | " + priorityText + " | " + dateText + "\n" +
                               "By: " + notice.getAuthorName() + "\n\n" +
                               notice.getContent();
            
            noticeItems.add(noticeText);
        }
        
        if (noticeItems.isEmpty()) {
            noticeItems.add("No notices found for the selected category.");
        }
        
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, noticeItems);
        listViewNotices.setAdapter(adapter);
    }

    private String getPriorityText(Notice.Priority priority) {
        switch (priority) {
            case URGENT:
                return "🔴 URGENT";
            case HIGH:
                return "🟠 HIGH";
            case MEDIUM:
                return "🟡 MEDIUM";
            case LOW:
                return "🟢 LOW";
            default:
                return "MEDIUM";
        }
    }

    private void updateNoticeCount() {
        textViewNoticeCount.setText("Total Notices: " + filteredNotices.size());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
