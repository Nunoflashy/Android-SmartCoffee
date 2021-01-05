package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AdminMenuActivity extends AppCompatActivity {

    TextView tvAdminName;
    User currentAdmin;

    private void BindControls() {
        tvAdminName = findViewById(R.id.tvAdminName);
    }

    private void init() {
        currentAdmin = (User)getIntent().getExtras().getSerializable("admin");
    }

    private void update() {
        tvAdminName.setText(currentAdmin.getName());
    }

    public void StartActivityListClientes(View v) {
        startActivity(new Intent(this, ListClientesActivity.class));
    }

    public void StartActivityAdminDashboard(View v) {
        startActivity(new Intent(this, AdminDashboardActivity.class));
    }

    public void StartActivityLogin(View v) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
        BindControls();
        init();
        update();
    }
}