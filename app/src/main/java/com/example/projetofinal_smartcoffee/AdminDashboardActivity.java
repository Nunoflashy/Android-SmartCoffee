package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.projetofinal_smartcoffee.Database.DatabaseManager;
import com.example.projetofinal_smartcoffee.Database.UserDatabase;

public class AdminDashboardActivity extends AppCompatActivity {

    TextView tvUserCount;
    UserDatabase userDB = DatabaseManager.GetDB("userDB");

    private void bindControls() {
        tvUserCount = findViewById(R.id.tvUserCount);
    }

    private void init() {
        userDB.open();
        tvUserCount.setText(String.valueOf(userDB.getAll().size()));
    }

    private void update() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        bindControls();
        init();
    }
}