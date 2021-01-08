package com.example.projetofinal_smartcoffee.Util;

import com.example.projetofinal_smartcoffee.Database.UserDatabase;

import java.util.Hashtable;


public class Authenticator {
    private String error = "";
    private Hashtable<String, String> messages = new Hashtable<>();

    protected UserDatabase db;

    protected void setError(String msg) {
        error = msg;
    }

    protected void concatError(String msg) {
        error += msg + '\n';
    }

    public String getError() {
        return error;
    }

    public void setDatabase(UserDatabase db) {
        this.db = db;
    }

    public UserDatabase getDatabase() {
        return db;
    }

    public void setMessage(String key, String message) {
        messages.put(key, message);
    }

    protected String getMessage(String key) {
        return messages.get(key);
    }
}
