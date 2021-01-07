package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class UserDashboardActivity extends AppCompatActivity {

    TextView tvUserDashboardUser = null;
    User u = null;

    CardView cvCafetaria;

    private void bindControls() {
        tvUserDashboardUser = findViewById(R.id.tvUserDashboardUser);
        cvCafetaria         = findViewById(R.id.cvCafetaria);
    }

    private void initControls() {
        cvCafetaria.setOnClickListener((v) -> {
            startActivity(new Intent(this, CafetariaActivity.class));
        });
    }

    private void init() {
        u = (User)getIntent().getExtras().getSerializable("user");

        initControls();
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