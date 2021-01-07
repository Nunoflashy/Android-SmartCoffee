package com.example.projetofinal_smartcoffee;

import com.example.projetofinal_smartcoffee.Database.UserType;

import java.io.Serializable;

public class User implements Serializable {
    public User() {}

    public User(String name, String pass, String mail) {
        this.name = name;
        this.pass = pass;
        this.mail = mail;
    }

    public User(int id, String name, String pass, String mail) {
        this(name, pass, mail);
        this.id = id;
    }

    public boolean isValid() {
        return !(name.isEmpty() && mail.isEmpty() && pass.isEmpty());
    }

    // Getters
    public int getID() { return id; }
    public String getName() { return name; }
    public String getPass() { return pass; }
    public String getMail() { return mail; }

    public String getInfo() {
        return String.format("Username: %s\nMail: %s\nPassword: %s\n", name, mail, pass);
    }

    // Util
    /*
        Metodo para mostrar o user numa ListView
     */
    @Override
    public String toString() {
        return String.format("%s", name);
    }

    private int id;
    private String name;
    private String pass;
    private String mail;
}
