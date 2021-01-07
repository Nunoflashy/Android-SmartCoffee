package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class UserDashboardActivity extends AppCompatActivity {

    TextView tvUserDashboardUser = null;
    User u = null;

    private void bindControls() {
        tvUserDashboardUser = findViewById(R.id.tvUserDashboardUser);
    }

    private void init() {
        u = (User)getIntent().getExtras().getSerializable("user");
    }

    private void update() {
        tvUserDashboardUser.setText(u.getName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        bindControls();
        init();
        update();
    }
}