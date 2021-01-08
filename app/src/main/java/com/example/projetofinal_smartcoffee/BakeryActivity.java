package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.projetofinal_smartcoffee.Database.DatabaseManager;
import com.example.projetofinal_smartcoffee.Database.ProductDatabase;
import com.example.projetofinal_smartcoffee.Util.Languages;
import com.example.projetofinal_smartcoffee.Util.Toast;

public class BakeryActivity extends BaseCategoryActivity {

    static final String PRODUCT_CATEGORY = "Pastelaria";

    LinearLayout svBakeryLayout;
    ProductDatabase productDB = DatabaseManager.GetProductDB("productDB");

    private void bindControls() {
        svBakeryLayout = findViewById(R.id.svBakeryLayout);
    }

    private void addProductsIfNotExists() {
        if(!productDB.hasProductsFromCategory(PRODUCT_CATEGORY)) {
            Product[] products = new Product[] {
                new Product("Bolo", PRODUCT_CATEGORY, 1, 1f),
                new Product("Fatia de Bolo", PRODUCT_CATEGORY, 1, 1.8f),
                new Product("Miniaturas", PRODUCT_CATEGORY, 1, 1f),
                new Product("Cheese Cake", PRODUCT_CATEGORY, 1, 1.50f),
                new Product("Muffin", PRODUCT_CATEGORY, 1, 1.20f),
                new Product("Torta", PRODUCT_CATEGORY, 1, 1.80f)
            };
            for(Product p : products) {
                productDB.addProduct(p);
            }
        }
    }

    @Override
    protected String getCategory() {
        return PRODUCT_CATEGORY;
    }

    @Override
    protected LinearLayout getLinearLayout() {
        return svBakeryLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bakery);

        // Configurar Linguagem
        Languages.SetLanguage(getString(R.string.language));

        bindControls();
        super.addProductsToLayout();
        addProductsIfNotExists();
    }
}