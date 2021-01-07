package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.projetofinal_smartcoffee.Database.DatabaseManager;
import com.example.projetofinal_smartcoffee.Database.ProductDatabase;
import com.example.projetofinal_smartcoffee.Util.Toast;

public class CafetariaActivity extends AppCompatActivity {

    LinearLayout svCafetariaLayout;

    ProductDatabase productDB = DatabaseManager.GetProductDB("productDB");

    private void bindControls() {
        svCafetariaLayout = findViewById(R.id.svCafetariaLayout);
    }

    private void addProductsToLayout() {
        productDB.open();
        for(Product p : productDB.getAll()) {
            Button product = new Button(this);
            product.setId(p.getID());
            product.setBackgroundColor(Color.argb(255, 46, 46, 46));
            product.setTextColor(Color.WHITE);
            product.setAllCaps(false);
            product.setEnabled(true);
            product.setText(String.format("%s (%.2f€)", p.getName(), p.getPrice()));
            svCafetariaLayout.addView(product);

            product.setOnClickListener((v) -> {
                Toast.Show(this, String.format("Produto %s adicionado!", p.getName()));
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafetaria);

        bindControls();
        addProductsToLayout();
    }
}