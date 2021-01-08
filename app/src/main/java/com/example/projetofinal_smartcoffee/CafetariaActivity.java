package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.projetofinal_smartcoffee.Database.DatabaseManager;
import com.example.projetofinal_smartcoffee.Database.ProductDatabase;
import com.example.projetofinal_smartcoffee.Util.MessageBox;
import com.example.projetofinal_smartcoffee.Util.Toast;

public class CafetariaActivity extends AppCompatActivity {

    static final String PRODUCT_CATEGORY = "Cafetaria";

    LinearLayout svCafetariaLayout;

    ProductDatabase productDB = DatabaseManager.GetProductDB("productDB");

    private void bindControls() {
        svCafetariaLayout = findViewById(R.id.svCafetariaLayout);
    }

    private void addProductsIfNotExists() {
        if(!productDB.hasProductsFromCategory(PRODUCT_CATEGORY)) {
            Product[] products = new Product[] {
                new Product("Café", PRODUCT_CATEGORY, 1, 0.60f),
                new Product("Descafeinado", PRODUCT_CATEGORY, 1, 0.60f),
                new Product("Meia de Leite", PRODUCT_CATEGORY, 1, 1f),
                new Product("Galão", PRODUCT_CATEGORY, 1, 1f),
                new Product("Latte", PRODUCT_CATEGORY, 1, 1.20f),
                new Product("Leite de Chocolate", PRODUCT_CATEGORY, 1, 1.30f)
            };
            for(Product p : products) {
                productDB.addProduct(p);
            }
        }
    }

    private void addProductsToLayout() {
        productDB.open();
        for(Product p : productDB.getAllFromCategory(PRODUCT_CATEGORY)) {
            if (productDB.isAvailable(p)) {
                Button product = new Button(this);
                product.setId(p.getID());
                product.setBackgroundColor(Color.argb(255, 46, 46, 46));
                product.setTextColor(Color.WHITE);
                product.setAllCaps(false);
                product.setText(String.format("%s (%.2f€)", p.getName(), p.getPrice()));

                product.setOnClickListener((v) -> {
                    Toast.Show(this, String.format("Produto %s adicionado!", p.getName()));
                });

                svCafetariaLayout.addView(product);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafetaria);
        MessageBox msg = new MessageBox(this);
        bindControls();
        addProductsToLayout();
        addProductsIfNotExists();
    }
}