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

public class DrinksActivity extends BaseCategoryActivity {

    static final String PRODUCT_CATEGORY = "Bebida";

    LinearLayout svDrinksLayout;

    private void bindControls() {
        svDrinksLayout = findViewById(R.id.svDrinksLayout);
    }

    @Override
    protected Product[] defaultProducts() {
        return  new Product[] {
                new Product("Água", PRODUCT_CATEGORY, 1, 0.80f),
                new Product("Chá", PRODUCT_CATEGORY, 1, 1.40f),
                new Product("Sumo de Lata", PRODUCT_CATEGORY, 1, 1.25f),
                new Product("Sumo Natural", PRODUCT_CATEGORY, 1, 2.50f),
                new Product("Bebida Energetica", PRODUCT_CATEGORY, 1, 2.10f),
                new Product("Nectar", PRODUCT_CATEGORY, 1, 1.25f),
        };
    }

    @Override
    protected String getCategory() {
        return PRODUCT_CATEGORY;
    }

    @Override
    protected LinearLayout getLinearLayout() {
        return svDrinksLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks);

        bindControls();
        super.addProductsToLayout();
    }
}