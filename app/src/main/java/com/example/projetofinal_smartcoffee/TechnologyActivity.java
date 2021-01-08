package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.projetofinal_smartcoffee.Database.DatabaseManager;
import com.example.projetofinal_smartcoffee.Database.ProductDatabase;
import com.example.projetofinal_smartcoffee.Util.Toast;

public class TechnologyActivity extends AppCompatActivity {

    static final String PRODUCT_CATEGORY = "Tecnologia";

    LinearLayout svTechnologyLayout;
    ProductDatabase productDB = DatabaseManager.GetProductDB("productDB");

    private void bindControls() {
        svTechnologyLayout = findViewById(R.id.svTechnologyLayout);
    }

    private void addProductsIfNotExists() {

        if(!productDB.hasProductsFromCategory(PRODUCT_CATEGORY)) {
            Product[] products = new Product[] {
                new Product("Filme 2D/3D", PRODUCT_CATEGORY, 1, 9f),
                new Product("Gaming", PRODUCT_CATEGORY, 1, 5f),
                new Product("Simulador/Carro", PRODUCT_CATEGORY, 1, 2.00f),
                new Product("Realidade Virtual", PRODUCT_CATEGORY, 1, 40.0f),
            };
            for(Product p : products) {
                productDB.addProduct(p);
            }
        }
    }

    private void addProductsToLayout() {
        productDB.open();
        for(Product p : productDB.getAllFromCategory("Tecnologia")) {
            Button product = new Button(this);
            product.setId(p.getID());
            product.setBackgroundColor(Color.argb(255, 46, 46, 46));
            product.setTextColor(Color.WHITE);
            product.setAllCaps(false);
            product.setText(String.format("%s (%.2f€)", p.getName(), p.getPrice()));

            product.setOnClickListener((v) -> {
                Toast.Show(this, String.format("Produto %s adicionado!", p.getName()));
            });

            svTechnologyLayout.addView(product);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technology);

        bindControls();
        addProductsToLayout();
        addProductsIfNotExists();
    }
}