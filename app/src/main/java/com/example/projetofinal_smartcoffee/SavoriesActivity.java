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

import java.util.List;

public class SavoriesActivity extends BaseCategoryActivity {

    static final String PRODUCT_CATEGORY = "Salgados";

    LinearLayout svSavoriesLayout;
    ProductDatabase productDB = DatabaseManager.GetProductDB("productDB");

    private void bindControls() {
        svSavoriesLayout = findViewById(R.id.svSavoriesLayout);
    }

    private void addProductsIfNotExists() {
        if(!productDB.hasProductsFromCategory(PRODUCT_CATEGORY)) {
            Product[] products = new Product[] {
                new Product("Rissol", PRODUCT_CATEGORY, 1, 1.50f),
                new Product("Croquete", PRODUCT_CATEGORY, 1, 1.50f),
                new Product("Coxinha", PRODUCT_CATEGORY, 1, 1.50f),
                new Product("Panado", PRODUCT_CATEGORY, 1, 1.50f),
                new Product("Empada", PRODUCT_CATEGORY, 1, 1.50f),
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
        return svSavoriesLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savories);

        bindControls();
        super.addProductsToLayout();
        addProductsIfNotExists();
    }
}