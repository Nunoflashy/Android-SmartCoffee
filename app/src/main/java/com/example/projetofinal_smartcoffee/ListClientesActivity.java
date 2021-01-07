package com.example.projetofinal_smartcoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.projetofinal_smartcoffee.Database.DatabaseManager;
import com.example.projetofinal_smartcoffee.Database.UserDatabase;
import com.example.projetofinal_smartcoffee.Util.AuthenticationManager;
import com.example.projetofinal_smartcoffee.Util.MessageBox;

public class ListClientesActivity extends BaseMenubarActivity {

    LinearLayout scrollViewLayout;

    UserDatabase userDB = DatabaseManager.GetDB("userDB");

    private void bindControls() {
        scrollViewLayout = findViewById(R.id.scrollViewLayout);
    }

    private void addToLayout() {
        userDB.open();
        for(User u : userDB.getAll()) {
            Button tv = new Button(this);
            tv.setId(u.getID());
            tv.setBackgroundColor(Color.argb(255, 46, 46, 46));
            tv.setTextColor(userDB.isUserBlocked(u) ? Color.RED : Color.WHITE);
            tv.setAllCaps(false);
            tv.setText(String.format("%s (id: %s) %s", u.getName(), u.getID(), userDB.isUserBlocked(u) ? "[Bloqueado]" : ""));
//            tv.setVisibility(AuthenticationManager.GetAuthenticatedUser().getID() ==
//                    u.getID() ? View.GONE : View.VISIBLE);
            tv.setOnClickListener((v) -> {
                Intent it = new Intent(this, ClienteDetailsActivity.class);
                it.putExtra("user", u);
                startActivity(it);
            });
            scrollViewLayout.addView(tv);
        }
    }

    @Override
    int getLayoutID() {
        return R.layout.activity_list_clientes;
    }

    @Override
    int getBottomNavigationMenuItemID() {
        return R.id.nav_listUsers;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_clientes);

        navView = findViewById(R.id.menubar);

        navView.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId()) {
                case R.id.nav_overview:
                    startActivity(new Intent(this, AdminDashboardActivity.class));
                break;
                case R.id.nav_listUsers: break;
                case R.id.nav_settings:
                    startActivity(new Intent(this, SettingsActivity.class));
                break;
                case R.id.nav_logout:
                    startActivity(new Intent(this, LoginActivity.class));
                break;
            }
            return true;
        });

        bindControls();
        addToLayout();

    }
}