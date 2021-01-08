package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminMenuActivity extends AppCompatActivity {

    TextView tvAdminName;
    User currentAdmin;

    Button btnAdminOverview;
    Button btnAdminListUsers;
    Button btnAdminSettings;
    Button btnAdminLogout;

    private void bindControls() {
        tvAdminName = findViewById(R.id.tvAdminName);
        btnAdminOverview    = findViewById(R.id.btnAdminOverview);
        btnAdminListUsers   = findViewById(R.id.btnAdminListUsers);
        btnAdminSettings    = findViewById(R.id.btnAdminSettings);
        btnAdminLogout      = findViewById(R.id.btnAdminLogout);
    }

    private void initControls() {
        // Overview
        btnAdminOverview.setOnClickListener((v) -> {
            startActivity(new Intent(this, AdminDashboardActivity.class));
        });
        // List Users
        btnAdminListUsers.setOnClickListener((v) -> {
            startActivity(new Intent(this, ListClientesActivity.class));
        });
        // Settings
        btnAdminSettings.setOnClickListener((v) -> {
            startActivity(new Intent(this, SettingsActivity.class));
        });
        // Logout
        btnAdminLogout.setOnClickListener((v) -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void init() {
        currentAdmin = (User)getIntent().getExtras().getSerializable("admin");
        initControls();
    }

    private void update() {
        tvAdminName.setText(currentAdmin.getName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
        bindControls();
        init();
        update();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}