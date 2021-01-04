package com.example.projetofinal_smartcoffee;

import java.io.Serializable;

public class Cliente implements Serializable {
    private int id;
    private String nome;
    private String mail;
    private String pass;

    public Cliente() {

    }

    public Cliente(String nome) {
        this.nome = nome;
    }

    public Cliente(String nome, String mail) {
        this.nome = nome;
        this.mail = mail;
    }

    public Cliente(String nome, String mail, String pass) {
        this.nome = nome;
        this.mail = mail;
        this.pass = pass;
    }

    public Cliente(int id, String nome, String mail) {
        this.id = id;
        this.nome = nome;
        this.mail = mail;
    }

    public Cliente(int id, String nome, String mail, String pass) {
        this(id, nome, mail);
        this.pass = pass;
    }

    public boolean isValid() {
        return !(nome.isEmpty() && mail.isEmpty() && pass.isEmpty());
    }

    // Getters
    public int getID()      { return id; }
    public String getNome() { return nome; }
    public String getMail() { return mail; }
    public String getPass() { return pass; }

    public String getInfo() {
        return String.format("Username: %s\nMail: %s\nPassword: %s\n", nome, mail, pass);
    }

    // Setters
    public void setNome(String nome) {
        if(nome == null) {
            return;
        }
        this.nome = nome;
    }

    public void setMail(String mail) {
        if(mail == null) {
            return;
        }
        this.mail = mail;
    }

    public void setPassword(String password) {
        if(password == null) {
            return;
        }
        this.pass = password;
    }

    // Util

    /*
        Metodo para mostrar o cliente numa ListView
     */
    @Override
    public String toString() {
        return String.format("%s", nome);
    }

}
