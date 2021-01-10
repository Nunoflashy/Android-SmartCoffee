package com.example.projetofinal_smartcoffee.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.projetofinal_smartcoffee.Exception.UserNotFoundException;
import com.example.projetofinal_smartcoffee.R;
import com.example.projetofinal_smartcoffee.User;
import com.example.projetofinal_smartcoffee.Util.Contexter;
import com.example.projetofinal_smartcoffee.Util.MessageBox;

import java.util.ArrayList;
import java.util.List;

public class UserDatabase extends Database {

    public UserDatabase(Context ctx, String name) {
        super(ctx, name);
        super.onCreate(this.getReadableDatabase());
    }

    @Override
    protected String getAtributes() {
        return  "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR NOT NULL, " +
                "pass VARCHAR NOT NULL, " +
                "mail VARCHAR NOT NULL, " +
                "type INTEGER NOT NULL, " +
                "status INTEGER NOT NULL";
    }

    @Override
    protected String getTable() {
        return "users";
    }

    /**
     * Adiciona um user a esta db
     * @param u
     */
    public void addUser(User u) {
        ContentValues v = new ContentValues();
        v.put("name", u.getName());
        v.put("pass", u.getPass());
        v.put("mail", u.getMail());
        v.put("type", USERTYPE_NORMAL);
        v.put("status", USERSTATE_NORMAL);
        super.addAtributes(v);
    }

    public void updateUser(User u, String newName) {
        if(!userExists(u.getName())) {
            throw new UserNotFoundException();
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("name", newName);
        db.update("users", v, "id = ?", new String[]{String.valueOf(u.getID())});

        // Atualizar o objeto para evitar desync com a db
        refreshUser(u);
        db.close();
    }

    public void updateMail(User u, String newMail) {
        if(!userExists(u.getName())) {
            throw new UserNotFoundException();
        }
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(String.format("UPDATE users SET mail='%s' WHERE id='%d'",
                newMail, u.getID()));

        // Atualizar o objeto para evitar desync com a db
        refreshUser(u);
        db.close();
    }

    public void updatePassword(User u, String password) {
        if(!userExists(u.getName())) {
            throw new UserNotFoundException();
        }
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(String.format("UPDATE users SET pass='%s' WHERE id='%d'",
                password, u.getID()));
        db.close();
    }

    /**
     * Remove um user da db
     * @param u
     */
    public void removeUser(User u) {
        if(!userExists(u.getName())) {
            throw new UserNotFoundException();
        }
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("users", "id = ?", new String[]{String.valueOf(u.getID())});
        db.close();
    }

    /**
     * Bloqueia o user da db, removendo a possibilidade de login
     * @param u
     */
    public void blockUser(User u) {
        if(!userExists(u.getName())) {
            throw new UserNotFoundException();
        }
        if(!isUserBlocked(u.getName())) {
            // Bloquear user
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(String.format("UPDATE users SET status=%d WHERE id=%d", USERSTATE_BLOCKED, u.getID()));
            db.close();
        }
    }

    /**
     * Desbloqueia o user da db
     * @param u
     */
    public void unblockUser(User u) {
        if(!userExists(u.getName())) {
            throw new UserNotFoundException();
        }
        if(isUserBlocked(u.getName())) {
            // Desbloquear user
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(String.format("UPDATE users SET status=%d WHERE id=%d", USERSTATE_NORMAL, u.getID()));
            db.close();
        }
    }

    /**
     * Permite alterar o tipo de user, este pode ser cliente ou admin
     * @param u Utilizador
     * @param type Tipo do utilizador
     */
    public void setUserType(User u, UserType type) {
        if(!userExists(u.getName())) {
            throw new UserNotFoundException("User not found!");
        }
        int typeValue = USERTYPE_NORMAL;
        switch(type) {
            case Normal: typeValue = USERTYPE_NORMAL; break;
            case Admin:  typeValue = USERTYPE_ADMIN; break;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(String.format("UPDATE users SET type=%s WHERE id=%d", typeValue, u.getID()));
        db.close();
    }


    /**
     * Retorna um user a partir do seu username de registo
     * throws UserNotFoundException se o utilizador nao existir
     * @param name
     * @return
     */
    public User getUserByName(String name) {
        for(User u : getAll()) {
            if(u.getName().equals(name)) {
                return u;
            }
        }
        throw new UserNotFoundException();
    }

    public User getUserByID(int id) {
        for(User u : getAll()) {
            if(u.getID() == id) {
                return u;
            }
        }
        throw new UserNotFoundException();
    }

    /**
     * Retorna o estado de conta do user, se esta bloqueado ou nao a partir de um objeto
     * @param u
     * @return
     */
    public boolean isUserBlocked(User u) {
        return isUserBlocked(u.getName());
    }

    /**
     * Retorna o estado de conta do user, se esta bloqueado ou nao a partir do seu username
     * @param username
     * @return
     */
    public boolean isUserBlocked(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        int status = USERSTATE_NORMAL;
        Cursor c = db.rawQuery(String.format("SELECT * FROM users WHERE name='%s';", username),null);
        Log.d("UserDB", String.valueOf(c.getCount()));
        if(c.moveToFirst()) {
            status = c.getInt(c.getColumnIndex("status"));
            c.close();
        }
        db.close();
        return status == USERSTATE_BLOCKED;
    }

    /**
     * Verifica se o user existe na db
     * @param u
     * @return
     */
    public boolean userExists(String u) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
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
            //MessageBox.Show("Exception", e.getMessage(), 0);
        }
        return false;
    }

    /**
     * Retorna todos os users na db
     * @return
     */
    public List<User> getAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<User> users = new ArrayList<>();

        try {
            Cursor c = db.rawQuery("SELECT * FROM users ORDER BY id", null);
            while (c.moveToNext()) {
                users.add(new User(
                        c.getInt(c.getColumnIndex("id")),
                        c.getString(c.getColumnIndex("name")),
                        c.getString(c.getColumnIndex("pass")),
                        c.getString(c.getColumnIndex("mail"))
                ));

                Log.d("UserDB", c.getString(c.getColumnIndex("pass")));
            }
        } catch(SQLiteException ex) {
            if(ex.getMessage().contains("no such table")) {
                super.onCreate(db);
                //throw new RuntimeException("The database " + getDatabaseName() + " could not create the table " + getTable());
            }
        }
        return users;
    }

    /**
     * Verifica se o user se encontra na posicao de admin
     * @param u
     * @return
     */
    public boolean isUserAdmin(User u) {
        if(!userExists(u.getName())) {
            throw new UserNotFoundException();
        }
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(String.format("SELECT * FROM users WHERE id='%s';", u.getID()),null);
        int type = USERTYPE_NORMAL;
        if(c.moveToFirst()) {
            type = c.getInt(c.getColumnIndex("type"));
            c.close();
        }
        return type == USERTYPE_ADMIN;
    }

    /**
     * Verifica se existe alguma conta de admin na db
     * @return
     */
    public boolean hasAdminAccount() {
        for(User u : getAll()) {
            if(isUserAdmin(u)) return true;
        }
        return false;
    }

    public boolean hasUsers() {
        return getAll().size() != 0;
    }

    /**
     * Atualiza o user u a partir dos novos valores da db
     * @param u
     */
    private void refreshUser(User u) {
        User user = this.getUserByID(u.getID());
        u.setName(user.getName());
        u.setPass(user.getPass());
        u.setMail(user.getMail());
    }

    public final int USERTYPE_NORMAL = 1;
    public final int USERTYPE_ADMIN  = 2;

    private final int USERSTATE_NORMAL  = 1;
    private final int USERSTATE_BLOCKED = 0;
}
