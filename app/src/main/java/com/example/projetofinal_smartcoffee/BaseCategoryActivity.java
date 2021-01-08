package com.example.projetofinal_smartcoffee;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetofinal_smartcoffee.Database.DatabaseManager;
import com.example.projetofinal_smartcoffee.Database.ProductDatabase;
import com.example.projetofinal_smartcoffee.Util.Languages;
import com.example.projetofinal_smartcoffee.Util.Toast;

public abstract class BaseCategoryActivity extends AppCompatActivity {

    protected ProductDatabase productDB = DatabaseManager.GetProductDB("productDB");

    protected void addProductsToLayout() {
        productDB.open();
        for(Product p : productDB.getAllFromCategory(getCategory())) {
            if (productDB.isAvailable(p)) {
                Button product = new Button(this);
                product.setId(p.getID());
                product.setBackgroundColor(Color.argb(255, 46, 46, 46));
                product.setTextColor(Color.WHITE);
                product.setAllCaps(false);
                product.setText(String.format("%s (%.2f€)", p.getName(), p.getPrice()));

                product.setOnClickListener((v) -> {
                    Toast.Show(this, Languages.ProductAddedMsg(p.getName()));
                });

                getLinearLayout().addView(product);
            }
        }
    }

    protected abstract String getCategory();
    protected abstract LinearLayout getLinearLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configurar Linguagem
        Languages.SetLanguage(getString(R.string.language));


    }
}
