package com.example.projetofinal_smartcoffee.Database;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class DatabaseManager {
    private static Hashtable<String, UserDatabase> userDatabases;
    private static Hashtable<String, ProductDatabase> productDatabases;

    public static UserDatabase GetDB(String name) {
        return userDatabases.get(name);
    }
    public static ProductDatabase GetProductDB(String name) {
        return productDatabases.get(name);
    }

    public static void AddDB(String name, UserDatabase db) {
        if(userDatabases == null) {
            userDatabases = new Hashtable<>();
        }
        userDatabases.put(name, db);
    }

    public static void AddDB(String name, ProductDatabase db) {
        if(productDatabases == null) {
            productDatabases = new Hashtable<>();
        }
        productDatabases.put(name, db);
    }

}
