package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.projetofinal_smartcoffee.Database.DatabaseManager;
import com.example.projetofinal_smartcoffee.Database.ProductDatabase;
import com.example.projetofinal_smartcoffee.Util.MessageBox;
import com.example.projetofinal_smartcoffee.Util.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {

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

    BottomNavigationView menubar;

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
                finalSv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            } else {
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
        for(Product p : productDB.getAllFromCategory(category)) {
            TextView product = new TextView(this);
            product.setId(p.getID());
            product.setBackgroundColor(Color.argb(255, 46, 46, 46));
            if(productDB.isAvailable(p)) {
                product.setTextColor(Color.WHITE);
            } else {
                product.setTextColor(Color.RED);
            }
            product.setAllCaps(false);
            product.setText(String.format("%s (%.2f€) (id: %d)", p.getName(), p.getPrice(), p.getID()));

            product.setOnClickListener((v) -> {
                productDB.setAvailable(p, !productDB.isAvailable(p));
                MessageBox msg = new MessageBox(this);
                msg.show("Produto", String.format("Product ID: %d\nProduct Name: %s\nProduct Category: %s\nProduct Available: %d\nProduct Price:%.2f",
                        p.getID(), p.getName(), p.getCategory(), p.getAvailability(), p.getPrice()), R.drawable.information_icon_svg);
                if(productDB.isAvailable(p)) {
                    product.setTextColor(Color.WHITE);
                } else {
                    product.setTextColor(Color.RED);
                }
            });

            switch(category) {
                case CafetariaActivity.PRODUCT_CATEGORY: layoutManageCafeteria.addView(product); break;
                case BakeryActivity.PRODUCT_CATEGORY: layoutManageBakery.addView(product); break;
                case SavoriesActivity.PRODUCT_CATEGORY: layoutManageSavories.addView(product); break;
                case DrinksActivity.PRODUCT_CATEGORY: layoutManageDrinks.addView(product); break;
                case TechnologyActivity.PRODUCT_CATEGORY: layoutManageTechnology.addView(product); break;
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

    private void initMenubar() {
        menubar = findViewById(R.id.menubar);

        menubar.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId()) {
                case R.id.nav_overview:
                    startActivity(new Intent(this, AdminDashboardActivity.class));
                break;
                case R.id.nav_listUsers:
                    startActivity(new Intent(this, ListClientesActivity.class));
                    break;
                case R.id.nav_settings: return true;
                case R.id.nav_logout:
                    startActivity(new Intent(this, LoginActivity.class));
                break;
            }
            finish();
            return true;
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        bindControls();
        addToLayout();
        initControls();
        initMenubar();
    }
}