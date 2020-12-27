package com.example.projetofinal_smartcoffee.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class AdminDB implements IDatabase {
    public AdminDB() {}

    public AdminDB(Context ctx, String name) {
        setContext(ctx);
        this.name = name;
    }

    public void make() {
        db = SQLiteDatabase.openOrCreateDatabase(getPath(), null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nome VARCHAR NOT NULL, " +
                    "pass VARCHAR NOT NULL, " +
                    "mail VARCHAR NOT NULL, " +
                    "type INTEGER NOT NULL, " +
                    "status INTEGER NOT NULL);");
    }

    public void close() {
        _ctx.deleteDatabase(getPath());
    }

    public void delete() {

    }

    public void addUser() {

    }

    public void removeUser() {

    }

    public void blockUser() {

    }

    public void unblockUser() {

    }

    public <T> T getUserByID(int id) {

    }

    public <T> boolean isUserBlocked(T user) {

    }

    public <T> boolean userExists(T user) {

    }

    public String getName() {

    }

    public String getPath() {
        return String.format("%s//%s", _ctx.getFilesDir().getPath(), name);
    }

    public <T> List<T> getAll() {

    }

    public void setContext(Context ctx) {
        _ctx = ctx;
    }

    private SQLiteDatabase db;
    private Context _ctx;
    private String name;
}
