package com.code.hostelmanagment.activity;

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
import com.code.hostelmanagment.model.Room;
import java.util.ArrayList;
import java.util.List;

public class RoomManagementActivity extends AppCompatActivity {

    private TextView textViewCurrentRoom, textViewRoomDetails;
    private Button buttonRequestRoom, buttonChangeRoom;
    private Spinner spinnerRoomType, spinnerFloor;
    private ListView listViewAvailableRooms;
    private List<Room> availableRooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_management);

        setupToolbar();
        initializeViews();
        setupSpinners();
        setupClickListeners();
        loadRoomData();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Room Management");
        }
    }

    private void initializeViews() {
        textViewCurrentRoom = findViewById(R.id.textViewCurrentRoom);
        textViewRoomDetails = findViewById(R.id.textViewRoomDetails);
        buttonRequestRoom = findViewById(R.id.buttonRequestRoom);
        buttonChangeRoom = findViewById(R.id.buttonChangeRoom);
        spinnerRoomType = findViewById(R.id.spinnerRoomType);
        spinnerFloor = findViewById(R.id.spinnerFloor);
        listViewAvailableRooms = findViewById(R.id.listViewAvailableRooms);
        
        availableRooms = new ArrayList<>();
    }

    private void setupSpinners() {
        // Room Type Spinner
        String[] roomTypes = {"Any Type", "Single", "Double", "Triple", "Quad"};
        ArrayAdapter<String> roomTypeAdapter = new ArrayAdapter<>(this, 
            android.R.layout.simple_spinner_item, roomTypes);
        roomTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoomType.setAdapter(roomTypeAdapter);

        // Floor Spinner
        String[] floors = {"Any Floor", "Ground Floor", "1st Floor", "2nd Floor", "3rd Floor"};
        ArrayAdapter<String> floorAdapter = new ArrayAdapter<>(this, 
            android.R.layout.simple_spinner_item, floors);
        floorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFloor.setAdapter(floorAdapter);
    }

    private void setupClickListeners() {
        buttonRequestRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestRoom();
            }
        });

        buttonChangeRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestRoomChange();
            }
        });
    }

    private void loadRoomData() {
        // Mock current room assignment
        textViewCurrentRoom.setText("Current Room: A-101");
        textViewRoomDetails.setText("Double occupancy • 2nd Floor • Block A\nRoommate: John Doe");

        // Mock available rooms
        availableRooms.clear();
        availableRooms.add(new Room("r1", "A-201", "2", "A", Room.RoomType.DOUBLE, 2));
        availableRooms.add(new Room("r2", "B-105", "1", "B", Room.RoomType.SINGLE, 1));
        availableRooms.add(new Room("r3", "A-301", "3", "A", Room.RoomType.TRIPLE, 3));

        // Create simple adapter for room list
        List<String> roomDescriptions = new ArrayList<>();
        for (Room room : availableRooms) {
            String desc = String.format("%s - %s (%s occupancy) - Floor %s", 
                room.getRoomNumber(), 
                room.getBlockName(),
                room.getRoomType().toString().toLowerCase(),
                room.getFloorNumber());
            roomDescriptions.add(desc);
        }

        ArrayAdapter<String> roomAdapter = new ArrayAdapter<>(this, 
            android.R.layout.simple_list_item_1, roomDescriptions);
        listViewAvailableRooms.setAdapter(roomAdapter);
    }

    private void requestRoom() {
        // Mock room request functionality
        // In real app, this would send request to backend
        buttonRequestRoom.setText("Request Sent!");
        buttonRequestRoom.setEnabled(false);
    }

    private void requestRoomChange() {
        // Mock room change request functionality
        // In real app, this would send change request to backend
        buttonChangeRoom.setText("Change Requested!");
        buttonChangeRoom.setEnabled(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
