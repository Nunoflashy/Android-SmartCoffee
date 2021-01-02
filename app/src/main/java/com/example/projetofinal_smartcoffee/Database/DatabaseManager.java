package com.example.projetofinal_smartcoffee.Database;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class DatabaseManager {
    private static Hashtable<String, UserDatabase> userDatabases;

    public static UserDatabase GetDB(String name) {
        return userDatabases.get(name);
    }

    public static void AddDB(String name, UserDatabase db) {
        if(userDatabases == null) {
            userDatabases = new Hashtable<>();
        }
        userDatabases.put(name, db);
    }

}
