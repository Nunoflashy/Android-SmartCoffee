package com.example.projetofinal_smartcoffee.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.projetofinal_smartcoffee.Product;
import com.example.projetofinal_smartcoffee.User;

import java.util.ArrayList;
import java.util.List;

public class ProductDatabase extends Database {
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
        if(db.isOpen()) {
            db.close();
            isOpen = false;
        }
    }

    public void delete() {
        //ctx.deleteDatabase(getPath());
        open();
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

    public String getPath() {
        return String.format("%s//%s", getContext().getFilesDir().getPath(), name);
    }

    private String name;
    private boolean isOpen = false;

    private final int PRODUCT_AVAILABLE = 1;
    private final int PRODUCT_UNAVAILABLE = 0;

}
