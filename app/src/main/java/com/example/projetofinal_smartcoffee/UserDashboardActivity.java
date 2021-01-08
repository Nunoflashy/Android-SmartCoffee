package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class UserDashboardActivity extends AppCompatActivity {

    TextView tvUserDashboardUser = null;
    User u = null;

    CardView cvCafetaria;
    CardView cvPastelaria;
    CardView cvSalgados;
    CardView cvBebidas;
    CardView cvTecnologia;

    ImageView btnUserLogout;

    private void bindControls() {
        tvUserDashboardUser = findViewById(R.id.tvUserDashboardUser);
        cvCafetaria         = findViewById(R.id.cvCafetaria);
        cvPastelaria        = findViewById(R.id.cvPastelaria);
        cvSalgados          = findViewById(R.id.cvSalgados);
        cvBebidas           = findViewById(R.id.cvBebidas);
        cvTecnologia        = findViewById(R.id.cvTecnologia);
        btnUserLogout       = findViewById(R.id.btnUserLogout);
    }

    private void initControls() {
        cvCafetaria.setOnClickListener((v) -> {
            startActivity(new Intent(this, CafetariaActivity.class));
        });
        cvPastelaria.setOnClickListener((v) -> {
            startActivity(new Intent(this, BakeryActivity.class));
        });
        cvSalgados.setOnClickListener((v) -> {
            startActivity(new Intent(this, SavoriesActivity.class));
        });
        cvBebidas.setOnClickListener((v) -> {
            startActivity(new Intent(this, DrinksActivity.class));
        });
        cvTecnologia.setOnClickListener((v) -> {
            startActivity(new Intent(this, TechnologyActivity.class));
        });
        btnUserLogout.setOnClickListener((v) -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
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