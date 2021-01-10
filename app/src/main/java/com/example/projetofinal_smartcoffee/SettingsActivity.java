package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.projetofinal_smartcoffee.Database.DatabaseManager;
import com.example.projetofinal_smartcoffee.Database.ProductDatabase;
import com.example.projetofinal_smartcoffee.Util.Languages;
import com.example.projetofinal_smartcoffee.Util.MessageBox;
import com.example.projetofinal_smartcoffee.Util.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends BaseMenubarActivity {

    LinearLayout layoutManageCafeteria;
    LinearLayout layoutManageBakery;
    LinearLayout layoutManageSavories;
    LinearLayout layoutManageDrinks;
    LinearLayout layoutManageTechnology;

    TextView tvManageCafeteria;
    TextView tvManageBakery;
    TextView tvManageSavories;
    TextView tvManageDrinks;
    TextView tvManageTechnology;

    ScrollView svManageCafeteria;
    ScrollView svManageBakery;
    ScrollView svManageSavories;
    ScrollView svManageDrinks;
    ScrollView svManageTechnology;

    ProductDatabase productDB = DatabaseManager.GetProductDB("productDB");

    private void bindControls() {
        layoutManageCafeteria   = findViewById(R.id.layoutManageCafeteria);
        layoutManageBakery      = findViewById(R.id.layoutManageBakery);
        layoutManageSavories    = findViewById(R.id.layoutManageSavories);
        layoutManageDrinks      = findViewById(R.id.layoutManageDrinks);
        layoutManageTechnology  = findViewById(R.id.layoutManageTechnology);

        tvManageCafeteria       = findViewById(R.id.tvManageCafeteria);
        tvManageBakery          = findViewById(R.id.tvManageBakery);
        tvManageSavories        = findViewById(R.id.tvManageSavories);
        tvManageDrinks          = findViewById(R.id.tvManageDrinks);
        tvManageTechnology      = findViewById(R.id.tvManageTechnology);

        svManageCafeteria       = findViewById(R.id.svManageCafeteria);
        svManageBakery          = findViewById(R.id.svManageBakery);
        svManageSavories        = findViewById(R.id.svManageSavories);
        svManageDrinks          = findViewById(R.id.svManageDrinks);
        svManageTechnology      = findViewById(R.id.svManageTechnology);
    }

    private void initControlByCategory(String category) {
        TextView tv = null;
        ScrollView sv = null;
        switch(category) {
            case CafetariaActivity.PRODUCT_CATEGORY:    tv = tvManageCafeteria;  sv = svManageCafeteria; break;
            case BakeryActivity.PRODUCT_CATEGORY:       tv = tvManageBakery;     sv = svManageBakery; break;
            case SavoriesActivity.PRODUCT_CATEGORY:     tv = tvManageSavories;   sv = svManageSavories; break;
            case DrinksActivity.PRODUCT_CATEGORY:       tv = tvManageDrinks;     sv = svManageDrinks; break;
            case TechnologyActivity.PRODUCT_CATEGORY:   tv = tvManageTechnology; sv = svManageTechnology; break;
        }
        ScrollView finalSv = sv;
        tv.setOnClickListener((v) -> {
            if(finalSv.getHeight() == 0) {
                // Expandir Layout
                finalSv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            } else {
                // Colapsar Layout
                finalSv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));
            }
        });
    }

    private void initControls() {
        initControlByCategory(CafetariaActivity.PRODUCT_CATEGORY);
        initControlByCategory(BakeryActivity.PRODUCT_CATEGORY);
        initControlByCategory(SavoriesActivity.PRODUCT_CATEGORY);
        initControlByCategory(DrinksActivity.PRODUCT_CATEGORY);
        initControlByCategory(TechnologyActivity.PRODUCT_CATEGORY);
    }

    private void addToLayoutFromCategory(String category) {
        for(Product p : productDB.getAll(category)) {
            TextView product = new TextView(this);
            product.setId(p.getID());
            product.setBackgroundColor(Color.argb(255, 46, 46, 46));
            Typeface typeface = ResourcesCompat.getFont(this, R.font.sitka_italic);
            product.setTypeface(typeface);
            product.setTextColor(productDB.isAvailable(p) ? Color.WHITE : Color.RED);
            product.setAllCaps(false);
            product.setText(String.format("%s (%.2f€) (id: %d)", p.getName(), p.getPrice(), p.getID()));

            product.setOnClickListener((v) -> {
                productDB.setAvailable(p, !productDB.isAvailable(p));
                if(productDB.isAvailable(p)) {
                    product.setTextColor(Color.WHITE);
                    Toast.Show(this, Languages.ProductInStockMsg(p.getName()));
                } else {
                    product.setTextColor(Color.RED);
                    Toast.Show(this, Languages.ProductOutStockMsg(p.getName()));
                }
            });

            switch(category) {
                case CafetariaActivity.PRODUCT_CATEGORY:    layoutManageCafeteria.addView(product); break;
                case BakeryActivity.PRODUCT_CATEGORY:       layoutManageBakery.addView(product); break;
                case SavoriesActivity.PRODUCT_CATEGORY:     layoutManageSavories.addView(product); break;
                case DrinksActivity.PRODUCT_CATEGORY:       layoutManageDrinks.addView(product); break;
                case TechnologyActivity.PRODUCT_CATEGORY:   layoutManageTechnology.addView(product); break;
            }
        }
    }

    private void addToLayout() {
        addToLayoutFromCategory(CafetariaActivity.PRODUCT_CATEGORY);
        addToLayoutFromCategory(BakeryActivity.PRODUCT_CATEGORY);
        addToLayoutFromCategory(SavoriesActivity.PRODUCT_CATEGORY);
        addToLayoutFromCategory(DrinksActivity.PRODUCT_CATEGORY);
        addToLayoutFromCategory(TechnologyActivity.PRODUCT_CATEGORY);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_settings;
    }

    @Override
    protected int getBottomNavigationMenuItemID() {
        return R.id.nav_settings;
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Configurar Linguagem
        Languages.SetLanguage(getString(R.string.language));

        bindControls();
        addToLayout();
        initControls();
        super.initNavView();
    }
}