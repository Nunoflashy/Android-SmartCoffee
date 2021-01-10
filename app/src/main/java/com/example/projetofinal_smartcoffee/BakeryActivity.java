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

    private void bindControls() {
        svBakeryLayout = findViewById(R.id.svBakeryLayout);
    }

    @Override
    protected Product[] defaultProducts() {
        return  new Product[] {
                new Product("Bolo", PRODUCT_CATEGORY, 1, 1f),
                new Product("Fatia de Bolo", PRODUCT_CATEGORY, 1, 1.8f),
                new Product("Miniaturas", PRODUCT_CATEGORY, 1, 1f),
                new Product("Cheese Cake", PRODUCT_CATEGORY, 1, 1.50f),
                new Product("Muffin", PRODUCT_CATEGORY, 1, 1.20f),
                new Product("Torta", PRODUCT_CATEGORY, 1, 1.80f)
        };
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

        bindControls();
        super.addProductsToLayout();
    }
}