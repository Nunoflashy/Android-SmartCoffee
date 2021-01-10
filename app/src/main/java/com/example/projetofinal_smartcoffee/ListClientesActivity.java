package com.example.projetofinal_smartcoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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
        for(User u : userDB.getAll()) {
            Button tv = new Button(this);
            tv.setId(u.getID());
            tv.setBackgroundColor(Color.argb(255, 46, 46, 46));
            Typeface typeface = ResourcesCompat.getFont(this, R.font.sitka_italic);
            tv.setTypeface(typeface);
            tv.setTextColor(userDB.isUserBlocked(u) ? Color.RED : Color.WHITE);
            tv.setAllCaps(false);
            tv.setText(String.format("%s (id: %s) %s", u.getName(), u.getID(), userDB.isUserBlocked(u) ? "[" + getString(R.string.blocked) + "]" : ""));
            tv.setOnClickListener((v) -> {
                Intent it = new Intent(this, ClienteDetailsActivity.class);
                it.putExtra("user", u);
                startActivity(it);
            });
            scrollViewLayout.addView(tv);
        }
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_list_clientes;
    }

    @Override
    protected int getBottomNavigationMenuItemID() {
        return R.id.nav_listUsers;
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_clientes);

        super.initNavView();

        bindControls();
        addToLayout();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // Atualizar todos os users na lista no fim de
        // possiveis alterações terem ocorrido
        scrollViewLayout.removeAllViews();
        scrollViewLayout.invalidate();
        addToLayout();
    }
}