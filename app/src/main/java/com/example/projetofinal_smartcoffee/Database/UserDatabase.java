package com.example.projetofinal_smartcoffee.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.projetofinal_smartcoffee.User;
import com.example.projetofinal_smartcoffee.Util.Contexter;
import com.example.projetofinal_smartcoffee.Util.MessageBox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserDatabase extends Contexter implements IDatabase, Serializable {
    public UserDatabase() {}

    public UserDatabase(String dbName) {
        this.name = dbName;
    }

    public UserDatabase(Context ctx, String name) {
        setContext(ctx);
        this.name = name;
    }

    public void open() {
        db = SQLiteDatabase.openOrCreateDatabase(getPath(), null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name VARCHAR NOT NULL, " +
                    "pass VARCHAR NOT NULL, " +
                    "mail VARCHAR NOT NULL, " +
                    "type INTEGER NOT NULL, " +
                    "status INTEGER NOT NULL);");
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
        db.execSQL("DROP TABLE IF EXISTS users");
    }

    private String passToHash(String pw) {
        // TODO: Implementar hash function na password
        return pw;
    }

    public void addUser(User u) {
        if(!isOpen) {
            open();
        }
        ContentValues v = new ContentValues();
        v.put("name", u.getName());
        v.put("pass", passToHash(u.getPass()));
        v.put("mail", u.getMail());
        v.put("type", USERTYPE_NORMAL);
        v.put("status", USERSTATE_NORMAL);
        db.insert("users", null, v);

    }

    public void removeUser(User u) {
        db.delete("users", "id = ?", new String[]{String.valueOf(u.getID())});
    }

    public void blockUser(User u) {
        if(!isUserBlocked(u.getName())) {
            // Bloquear user
            db.execSQL(String.format("UPDATE users SET status=%d WHERE id=%d", USERSTATE_BLOCKED, u.getID()));
        }
    }

    public void unblockUser(User u) {
        if(isUserBlocked(u.getName())) {
            // Desbloquear user
            db.execSQL(String.format("UPDATE users SET status=%d WHERE id=%d", USERSTATE_NORMAL, u.getID()));
        }
    }

    public User getUserByID(int id) {
        return null;
    }

    public boolean isUserBlocked(User u) {
        return isUserBlocked(u.getName());
    }

    public boolean isUserBlocked(String username) {
        int status = USERSTATE_NORMAL;
        Cursor c = db.rawQuery(String.format("SELECT * FROM users WHERE name='%s';", username),null);
        Log.d("UserDB", String.valueOf(c.getCount()));
        if(c.moveToFirst()) {
            status = c.getInt(c.getColumnIndex("status"));
            c.close();
        }
        return status == USERSTATE_BLOCKED;
    }

    public boolean userExists(String u) {
        try {
            //Cursor c = db.query("users", new String[] {"id", "name", "pass", "mail"}, "name=?", new String[] {u.getName()}, null, null, null);
            Cursor c = db.rawQuery(String.format("SELECT name FROM users WHERE name='%s';", u),null);
            Log.d("UserDB", String.valueOf(c.getCount()));
            if(c.getCount() > 0) {
                Log.d("UserDB", "User existe");
                Log.d("UserDB", String.format("\n%s", u));
                c.close();
                return true;
            }
            Log.d("UserDB", "User nao existe");
            c.close();
            return false;
        }
        catch(Exception e) {
            MessageBox.Show("Exception", e.getMessage(), 0);
        }
        return false;
    }

    public String getName() {
        return name;
    }


    public String getPath() {
        return String.format("%s//%s", getContext().getFilesDir().getPath(), name);
    }

    public List<User> getAll() {
        Cursor c = db.rawQuery("SELECT * FROM users ORDER BY id", null);
        ArrayList<User> users = new ArrayList<>();

        while(c.moveToNext()) {
            users.add(new User(
                c.getInt(c.getColumnIndex("id")),
                c.getString(c.getColumnIndex("name")),
                c.getString(c.getColumnIndex("pass")),
                c.getString(c.getColumnIndex("mail"))
            ));

            Log.d("UserDB", c.getString(c.getColumnIndex("pass")));
        }
        return users;
    }

    public SQLiteDatabase getHandle() { return db; }

    private SQLiteDatabase db;
    private String name;

    private boolean isOpen = false;

    private final int USERTYPE_NORMAL = 1;
    private final int USERTYPE_ADMIN  = 2;

    private final int USERSTATE_NORMAL  = 1;
    private final int USERSTATE_BLOCKED = 0;
}
