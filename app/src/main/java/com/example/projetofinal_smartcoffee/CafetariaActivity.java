package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.projetofinal_smartcoffee.Database.DatabaseManager;
import com.example.projetofinal_smartcoffee.Database.ProductDatabase;
import com.example.projetofinal_smartcoffee.Util.Languages;
import com.example.projetofinal_smartcoffee.Util.MessageBox;
import com.example.projetofinal_smartcoffee.Util.Toast;

public class CafetariaActivity extends BaseCategoryActivity {

    static final String PRODUCT_CATEGORY = "Cafetaria";

    LinearLayout svCafetariaLayout;

    private void bindControls() {
        svCafetariaLayout = findViewById(R.id.svCafetariaLayout);
    }

    @Override
    protected Product[] defaultProducts() {
        return  new Product[]{
                new Product("Café", PRODUCT_CATEGORY, 1, 0.60f),
                new Product("Descafeinado", PRODUCT_CATEGORY, 1, 0.60f),
                new Product("Meia de Leite", PRODUCT_CATEGORY, 1, 1f),
                new Product("Galão", PRODUCT_CATEGORY, 1, 1f),
                new Product("Latte", PRODUCT_CATEGORY, 1, 1.20f),
                new Product("Leite de Chocolate", PRODUCT_CATEGORY, 1, 1.30f)
        };
    }


    @Override
    protected String getCategory() {
        return PRODUCT_CATEGORY;
    }

    @Override
    protected LinearLayout getLinearLayout() {
        return svCafetariaLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafetaria);

        bindControls();
        super.addProductsToLayout();
    }
}