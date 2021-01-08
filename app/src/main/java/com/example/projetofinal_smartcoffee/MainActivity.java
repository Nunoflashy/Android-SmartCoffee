package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.projetofinal_smartcoffee.Database.DatabaseManager;
import com.example.projetofinal_smartcoffee.Database.ProductDatabase;
import com.example.projetofinal_smartcoffee.Database.UserDatabase;
import com.example.projetofinal_smartcoffee.Database.UserType;
import com.example.projetofinal_smartcoffee.Util.Languages;
import com.example.projetofinal_smartcoffee.Util.ListViewUtil;
import com.example.projetofinal_smartcoffee.Util.MessageBox;
import com.example.projetofinal_smartcoffee.Util.RegistrationManager;

public class MainActivity extends AppCompatActivity {

    private int SPLASH_TIME = 4000;

    private void StartSplash() {
        new Handler().postDelayed(() -> {
            Intent it = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(it);
            finish();
        }, SPLASH_TIME);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup MsgBox
        MessageBox.SetContext(this);

        // Init Databases
        UserDatabase db = new UserDatabase(getApplicationContext(),"db_SmartCoffee");
        DatabaseManager.AddDB("userDB", db);

        ProductDatabase productDB = new ProductDatabase(getApplicationContext(), "db_SmartCoffee");
        DatabaseManager.AddDB("productDB", productDB);

        // Configurar Linguagem
        Languages.SetLanguage(getString(R.string.language));

        StartSplash();
    }
}