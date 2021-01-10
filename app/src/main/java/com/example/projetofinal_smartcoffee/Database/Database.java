package com.example.projetofinal_smartcoffee.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class Database extends SQLiteOpenHelper {

    public Database(Context context, String name) {
        super(context, name, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s (%s);", getTable(), getAtributes()));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public void dropTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + getTable());
    }

    /**
     * Adiciona os atributos passados por v
     * @param v
     */
    protected void addAtributes(ContentValues v) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(v != null) {
            db.insert(getTable(), null, v);
        }
        db.close();
    }

    /**
     *
     * @return Retorna a tabela que esta DB representa
     */
    protected abstract String getTable();

    /**
     *
     * @return Retorna todos os atributos da tabela da DB em formato SQL
     */
    protected abstract String getAtributes();
}
