package com.example.projetofinal_smartcoffee.Database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projetofinal_smartcoffee.Util.Contexter;

import java.util.ArrayList;
import java.util.List;

public abstract class Database extends Contexter {
    SQLiteDatabase db;

    public abstract void open();
    public abstract void close();

    public void addAttribute(String table, String attribute) {

    }

    public void updateAttribute(String table, String attribute, String newAttribute) {
        db.execSQL(String.format("UPDATE %s= SET %s='%s' WHERE %s='%s'",
                table, attribute, newAttribute, attribute, attribute));
    }

    public void deleteAttribute(String table, String attribute) {
        db.delete(table, String.format("%s = ?", attribute), new String[]{attribute});
        //db.delete("users", "id = ?", new String[]{String.valueOf(u.getID())});
    }

//    public <DatabaseItem> DatabaseItem[] getColumnsOf(DatabaseItem dbItem, String attribute) {
//        List<DatabaseItem> attributes = new ArrayList<>();
//        Cursor c = db.rawQuery(String.format("SELECT * FROM %s", attribute),null);
//        while(c.moveToNext()) {
//
//        }
//    }
}
