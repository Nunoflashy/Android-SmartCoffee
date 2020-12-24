package com.example.projetofinal_smartcoffee.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projetofinal_smartcoffee.Cliente;
import com.example.projetofinal_smartcoffee.Exception.ClienteNotValidException;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class ClienteDB {
    private static SQLiteDatabase db;
    private static Context _ctx;

    private static boolean isOpen = false;

    /**
     *
     * @return O path da base de dados
     */
    private static String getPath() {
        return String.format("%s//%s", _ctx.getFilesDir().getPath(), "db_SmartCoffee");
    }

    public static SQLiteDatabase GetHandle() {
        return db;
    }

    public static void SetContext(Context ctx) {
        _ctx = ctx;
    }

    public static void Create() {
        db = SQLiteDatabase.openOrCreateDatabase(getPath(), null);
        db.execSQL("CREATE TABLE IF NOT EXISTS cliente(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome VARCHAR NOT NULL, " +
                "mail VARCHAR NOT NULL, " +
                "password VARCHAR NOT NULL);");
        isOpen = true;
    }

    public static void Open() {
        if(!db.isOpen()) {
            db = SQLiteDatabase.openDatabase(getPath(), null, 0);
        }
    }

    public static void Close() {
        if(db.isOpen()) {
            db.close();
            isOpen = false;
        }
    }

    public static void Delete() {
        _ctx.deleteDatabase(getPath());
    }

    public static void AddCliente(Cliente c) {
        if(!c.isValid()) {
            throw new ClienteNotValidException(c);
        }
        ContentValues v = new ContentValues();
        v.put("nome", c.getNome());
        v.put("mail", c.getMail());
        v.put("password", c.getPass());

        db.insert("cliente", null, v);

//        db.execSQL(String.format("INSERT INTO cliente(nome, mail, password) VALUES(%s, %s, %s)",
//                c.getNome(), c.getMail(), c.getPass()));
    }

    public static void DeleteCliente(Cliente c) {
        db.delete("cliente", "id = ?", new String[]{String.valueOf(c.getID())});
    }

    public static List<Cliente> GetAll() {
        if(!isOpen) {
            Create();
        }
        Cursor c = db.rawQuery("SELECT * FROM cliente ORDER BY id", null);
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        while(c.moveToNext()) {
            clientes.add(new Cliente(
                    c.getInt(c.getColumnIndex("id")),
                    c.getString(c.getColumnIndex("nome")),
                    c.getString(c.getColumnIndex("mail")),
                    c.getString(c.getColumnIndex("password"))
            ));
        }
        return clientes;
    }

    public static Cliente GetClienteByID(int id) {
        Cursor cursor = db.rawQuery(String.format("SELECT * FROM cliente WHERE id='%i'", id), null);
        if(cursor.getCount() <= 0) {
            return null;
        }
        //
        return null;
    }

    public static void BlockCliente(Cliente c) {
        db.execSQL("UPDATE cliente SET estado='blocked' WHERE id= " + c.getID());
    }

    public static void UnblockCliente(Cliente c) {
        db.execSQL("UPDATE cliente SET estado='normal' WHERE id= " + c.getID());
    }

    public static boolean IsClienteBlocked(Cliente c) {
        Cursor cursor = db.rawQuery(String.format("SELECT status FROM cliente WHERE id='%i'", c.getID()), null);
        return true;
    }

    public static boolean ClienteExists(Cliente c) {
        Cursor cursor = db.rawQuery(String.format("SELECT * FROM cliente WHERE id='%i'", c.getID()), null);
        boolean clienteExiste = false;
        if(cursor.getCount() > 0) {
            clienteExiste = true;
        }
        cursor.close();
        return clienteExiste;
    }


}
