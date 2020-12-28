package com.example.projetofinal_smartcoffee.Util;

import com.example.projetofinal_smartcoffee.Database.UserDatabase;
import com.example.projetofinal_smartcoffee.User;

public class RegistrationManager {
    private UserDatabase db;

    private String user, mail, pass, repass;
    private String error = "";

    public RegistrationManager(UserDatabase db) {
        setDatabase(db);
    }

    public void setDatabase(UserDatabase db) {
        this.db = db;
    }

    public void setDetails(String user, String mail, String pass, String repass) {
        this.user = user;
        this.mail = mail;
        this.pass = pass;
        this.repass = repass;
    }

    private void setError(String msg) {
        error = msg + '\n';
    }

    private void concatError(String msg) {
        error += msg + '\n';
    }

    public String getError() { return error; }

    public boolean isEmpty() {
        if(user.isEmpty()) {
            concatError("O user não pode estar vazio!");
        }
        if(mail.isEmpty()) {
            concatError("O mail não pode estar vazio!");
        }
        if(pass.isEmpty()) {
            concatError("A password não pode estar vazia!");
        }
        return user.isEmpty() || mail.isEmpty() || pass.isEmpty();
    }


    public boolean isRegistrationSuccessful() {
        boolean retval = true;

        if(isEmpty()) {
            return false;
        }
        if(!pass.equals(repass)) {
            concatError("As passwords não coincidem!");
            return false;
        }
        if(db.userExists(user)) {
            setError("Este user já se encontra registado!");
            retval = false;
        }

        return retval;
    }
}
