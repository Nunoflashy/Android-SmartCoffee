package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.projetofinal_smartcoffee.Database.DatabaseManager;
import com.example.projetofinal_smartcoffee.Database.UserDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class AdminDashboardActivity extends AppCompatActivity {

    BottomNavigationView menubar;
    TextView tvUserCount, tvNewestUser;
    CardView cvUserCount, cvLastRegistered;
    UserDatabase userDB = DatabaseManager.GetDB("userDB");

    private void bindControls() {
        tvUserCount      = findViewById(R.id.tvUserCount);
        tvNewestUser     = findViewById(R.id.tvNewestUser);
        cvUserCount      = findViewById(R.id.cvUserCount);
        cvLastRegistered = findViewById(R.id.cvLastRegistered);
    }

    /**
     *
     * @return Retorna o utilizador que se registou mais recentemente na db
     */
    private User getNewestUser() {
        User newestUser = null;
        for(User u : userDB.getAll()) {
            newestUser = u;
        }
        return newestUser;
    }

    private void initMenubar() {
        menubar = findViewById(R.id.menubar);

        menubar.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId()) {
                case R.id.nav_overview: break;
                case R.id.nav_listUsers:
                    startActivity(new Intent(this, ListClientesActivity.class));
                break;
                case R.id.nav_settings:
                break;
                case R.id.nav_logout:
                    startActivity(new Intent(this, LoginActivity.class));
                    break;
            }
            return true;
        });
    }

    private void init() {
        userDB.open();

        tvUserCount.setText(String.valueOf(userDB.getAll().size()));

        User u = getNewestUser();
        tvNewestUser.setText(u.getName());

        cvUserCount.setOnClickListener((v)      -> startActivity(new Intent(this, ListClientesActivity.class)));
        cvLastRegistered.setOnClickListener((v) -> startActivity(new Intent(this, ClienteDetailsActivity.class).putExtra("user", u)));
    }

    private void update() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        bindControls();
        init();
        initMenubar();
    }
}