package com.example.projetofinal_smartcoffee.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.projetofinal_smartcoffee.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDatabase2 extends SQLiteOpenHelper {

    public ProductDatabase2(Context ctx, String name) {
        super(ctx, name, null, 2);
        onCreate(this.getReadableDatabase());
    }

    public void dropTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS products2");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS products2(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR NOT NULL," +
                "category VARCHAR NOT NULL," +
                "status INTEGER NOT NULL," +
                "price smallmoney NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public void addProduct(Product p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("name", p.getName());
        v.put("category", p.getCategory());
        v.put("status", p.getAvailability());
        v.put("price", p.getPrice());
        db.insert("products2", null, v);
        db.close();
    }

    public List<Product> getAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Product> products = new ArrayList<>();

        try {
            Cursor c = db.rawQuery("SELECT * FROM products2 ORDER BY id", null);
            while(c.moveToNext()) {
                products.add(new Product(
                        c.getInt(c.getColumnIndex("id")),
                        c.getString(c.getColumnIndex("name")),
                        c.getString(c.getColumnIndex("category")),
                        c.getInt(c.getColumnIndex("status")),
                        c.getFloat(c.getColumnIndex("price"))
                ));
            }

        } catch(SQLiteException ex) {
            Log.d("pd", ex.getMessage());
        }

        return products;
    }

    public List<Product> getAll(String category) {
        List<Product> products = getAll();
        List<Product> categoryProducts = new ArrayList<>();
        for(Product p : products) {
            if(p.getCategory().equals(category)) {
                categoryProducts.add(p);
            }
        }
        return categoryProducts;
    }

    public void setAvailablle(Product p, boolean available) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(String.format("UPDATE products SET status=%d WHERE id=%d",
                available ? PRODUCT_AVAILABLE : PRODUCT_UNAVAILABLE, p.getID()));
        db.close();
    }

    public boolean isAvailable(Product p) {
        SQLiteDatabase db = this.getReadableDatabase();

        int status = PRODUCT_AVAILABLE;
        Cursor c = db.rawQuery(String.format("SELECT * FROM products2 WHERE id='%d'", p.getID()), null);
        if(c.moveToFirst()) {
            status = c.getInt(c.getColumnIndex("status"));
            c.close();
        }
        return status == PRODUCT_AVAILABLE;
    }

    public boolean hasProducts() {
        return getAll().size() != 0;
    }

    public boolean hasProductsFromCategory(String category) {
        return getAll(category).size() != 0;
    }

    public final int PRODUCT_AVAILABLE = 1;
    public final int PRODUCT_UNAVAILABLE = 0;
}
