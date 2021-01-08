package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.projetofinal_smartcoffee.Database.DatabaseManager;
import com.example.projetofinal_smartcoffee.Database.ProductDatabase;
import com.example.projetofinal_smartcoffee.Util.Toast;

public class DrinksActivity extends AppCompatActivity {

    static final String PRODUCT_CATEGORY = "Bebida";

    LinearLayout svDrinksLayout;
    ProductDatabase productDB = DatabaseManager.GetProductDB("productDB");

    private void bindControls() {
        svDrinksLayout = findViewById(R.id.svDrinksLayout);
    }

    private void addProductsIfNotExists() {
        if(!productDB.hasProductsFromCategory(PRODUCT_CATEGORY)) {
            Product[] products = new Product[] {
                new Product("Água", PRODUCT_CATEGORY, 1, 0.80f),
                new Product("Chá", PRODUCT_CATEGORY, 1, 1.40f),
                new Product("Sumo de Lata", PRODUCT_CATEGORY, 1, 1.25f),
                new Product("Sumo Natural", PRODUCT_CATEGORY, 1, 2.50f),
                new Product("Bebida Energetica", PRODUCT_CATEGORY, 1, 2.10f),
                new Product("Nectar", PRODUCT_CATEGORY, 1, 1.25f),
            };
            for(Product p : products) {
                productDB.addProduct(p);
            }
        }
    }

    private void addProductsToLayout() {
        productDB.open();
        for(Product p : productDB.getAllFromCategory(PRODUCT_CATEGORY)) {
            Button product = new Button(this);
            product.setId(p.getID());
            product.setBackgroundColor(Color.argb(255, 46, 46, 46));
            product.setTextColor(Color.WHITE);
            product.setAllCaps(false);
            product.setText(String.format("%s (%.2f€)", p.getName(), p.getPrice()));

            product.setOnClickListener((v) -> {
                Toast.Show(this, String.format("Produto %s adicionado!", p.getName()));
            });

            svDrinksLayout.addView(product);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks);

        bindControls();
        addProductsToLayout();
        addProductsIfNotExists();
    }
}