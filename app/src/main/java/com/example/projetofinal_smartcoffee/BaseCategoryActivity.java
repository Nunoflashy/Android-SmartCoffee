package com.example.projetofinal_smartcoffee;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.projetofinal_smartcoffee.Database.DatabaseManager;
import com.example.projetofinal_smartcoffee.Database.ProductDatabase;
import com.example.projetofinal_smartcoffee.Util.Languages;
import com.example.projetofinal_smartcoffee.Util.Toast;

public abstract class BaseCategoryActivity extends AppCompatActivity {

    protected ProductDatabase productDB = DatabaseManager.GetProductDB("productDB");

    /**
     * Adiciona os produtos em defaultProducts() de cada activity derivada
     * à database se nao existirem produtos
     */
    private void addProductsIfNotExists() {
        if(!productDB.hasProductsFromCategory(getCategory())) {
            for(Product p : defaultProducts()) {
                productDB.addProduct(p);
            }
        }
    }

    /**
     * Adiciona os produtos ao layout derivado desta class,
     * permitindo assim a todas as activities das categorias do cafe
     * a possibilidade de partilhar a funcionalidade.
     */
    protected void addProductsToLayout() {
        for(Product p : productDB.getAll(getCategory())) {
            if (productDB.isAvailable(p)) {
                Button product = new Button(this);
                product.setId(p.getID());
                product.setBackgroundColor(Color.argb(255, 46, 46, 46));
                Typeface typeface = ResourcesCompat.getFont(this, R.font.sitka_italic);
                product.setTypeface(typeface);
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

    /**
     *
     * @return Retorna os produtos default de cada activity derivada
     */
    protected abstract Product[] defaultProducts();

    /**
     *
     * @return Retorna a categoria do cafe da activity
     */
    protected abstract String getCategory();

    /**
     *
     * @return Retorna o layout de cada activity derivada para permitir
     * adicionar os items.
     */
    protected abstract LinearLayout getLinearLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addProductsIfNotExists();

        // Configurar Linguagem
        Languages.SetLanguage(getString(R.string.language));
    }
}
