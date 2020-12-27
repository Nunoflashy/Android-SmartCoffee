package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.projetofinal_smartcoffee.Database.ClienteDB;
import com.example.projetofinal_smartcoffee.Database.UserDatabase;
import com.example.projetofinal_smartcoffee.Util.ListViewUtil;
import com.example.projetofinal_smartcoffee.Util.MessageBox;

public class MainActivity extends AppCompatActivity {

    private int SPLASH_TIME = 1;

    private void StartSplash() {
        new Handler().postDelayed(() -> {
            Intent it = new Intent(MainActivity.this, LoginActivity.class);
            it.putExtra("userDB", new UserDatabase("db_SmartCoffee"));
            startActivity(it);
            finish();
        }, SPLASH_TIME);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup ListViewUtil
        ListViewUtil.SetContext(getApplicationContext());

        // Setup MsgBox
        MessageBox.SetContext(this);

        // Setup DB
        ClienteDB.SetContext(getApplicationContext());

        StartSplash();
    }
}