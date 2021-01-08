package com.example.projetofinal_smartcoffee.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.projetofinal_smartcoffee.Product;
import com.example.projetofinal_smartcoffee.User;
import com.example.projetofinal_smartcoffee.Util.Contexter;

import java.util.ArrayList;
import java.util.List;

public class ProductDatabase extends Contexter {
    public ProductDatabase(String dbName) {
        this.name = name;
    }

    public ProductDatabase(Context ctx, String name) {
        this(name);
        setContext(ctx);
    }

    public void open() {
        db = SQLiteDatabase.openOrCreateDatabase(getPath(), null);
        db.execSQL("CREATE TABLE IF NOT EXISTS products(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name VARCHAR NOT NULL," +
                    "category VARCHAR NOT NULL," +
                    "status INTEGER NOT NULL," +
                    "price smallmoney NOT NULL);");
        isOpen = true;
    }

    public void close() {
        if(isOpen) {
            db.close();
            isOpen = false;
        }
    }

    public void delete() {
        if(!isOpen) {
            open();
        }
        db.execSQL("DROP TABLE IF EXISTS products");
    }

    public void addProduct(Product p) {
        if(!isOpen) {
            open();
        }
        ContentValues v = new ContentValues();
        v.put("name", p.getName());
        v.put("category", p.getCategory());
        v.put("status", p.getAvailability());
        v.put("price", p.getPrice());
        db.insert("products", null, v);
    }

    public void removeAllFromCategory(String category) {
        if(!isOpen) {
            open();
        }
        db.execSQL(String.format("DELETE FROM products WHERE category=%s", category));
    }

    public void setAvailable(Product p, boolean available) {
        if(!isOpen) {
            open();
        }
        db.execSQL(String.format("UPDATE products SET status=%d WHERE id=%d",
                available ? PRODUCT_AVAILABLE : PRODUCT_UNAVAILABLE, p.getID()));
    }

    public boolean isAvailable(Product p) {
        int status = PRODUCT_UNAVAILABLE;
        Cursor c = db.rawQuery(String.format("SELECT * FROM products WHERE id='%d';", p.getID()),null);
        if(c.moveToFirst()) {
            status = c.getInt(c.getColumnIndex("status"));
            c.close();
        }
        return status == PRODUCT_AVAILABLE;
    }

    public List<Product> getAll() {
        if(!isOpen) {
            open();
        }
        Cursor c = db.rawQuery("SELECT * FROM products ORDER BY id", null);
        ArrayList<Product> products = new ArrayList<>();

        while(c.moveToNext()) {
            products.add(new Product(
                    c.getInt(c.getColumnIndex("id")),
                    c.getString(c.getColumnIndex("name")),
                    c.getString(c.getColumnIndex("category")),
                    c.getInt(c.getColumnIndex("status")),
                    c.getFloat(c.getColumnIndex("price"))
            ));
        }
        return products;
    }

    public List<Product> getAllFromCategory(String category) {
        List<Product> products = getAll();
        List<Product> categoryProducts = new ArrayList<>();
        for(Product p : products) {
            if(p.getCategory().equals(category)) {
                categoryProducts.add(p);
            }
        }
        return categoryProducts;
    }

    public boolean hasProducts() {
        return getAll().size() != 0;
    }

    public boolean hasProductsFromCategory(String category) {
        return getAllFromCategory(category).size() != 0;
    }

    public String getPath() {
        return String.format("%s//%s", getContext().getFilesDir().getPath(), name);
    }

    private SQLiteDatabase db;
    private String name;
    private boolean isOpen = false;

    public final int PRODUCT_AVAILABLE = 1;
    public final int PRODUCT_UNAVAILABLE = 0;

}
