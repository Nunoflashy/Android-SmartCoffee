package com.example.projetofinal_smartcoffee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetofinal_smartcoffee.Util.MessageBox;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BaseMenubarActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView navView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());

        navView = findViewById(R.id.menubar);
        navView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateState();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }

    protected void initNavView() {
        navView = findViewById(R.id.menubar);

        navView.setOnNavigationItemSelectedListener(item -> {
            if(item.getItemId() == getBottomNavigationMenuItemID()) return true;
            switch(item.getItemId()) {
                case R.id.nav_overview: startActivity(new Intent(getActivity(), AdminDashboardActivity.class)); break;
                case R.id.nav_listUsers: startActivity(new Intent(getActivity(), ListClientesActivity.class)); break;
                case R.id.nav_settings: startActivity(new Intent(getActivity(), SettingsActivity.class)); break;
                case R.id.nav_logout: startActivity(new Intent(getActivity(), LoginActivity.class)); break;
            }
            finish();
            return true;
        });

    }

    private void updateState() {
        int actionID = getBottomNavigationMenuItemID();
        selectItem(actionID);
    }

    private void selectItem(int id) {
        MenuItem item = navView.getMenu().findItem(id);
        item.setChecked(true);
    }

    protected abstract int getLayoutID();
    protected abstract int getBottomNavigationMenuItemID();
    protected abstract Activity getActivity();
}
