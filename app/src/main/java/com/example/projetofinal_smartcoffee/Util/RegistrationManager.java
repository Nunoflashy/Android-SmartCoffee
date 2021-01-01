package com.example.projetofinal_smartcoffee.Util;

import com.example.projetofinal_smartcoffee.Database.UserDatabase;
import com.example.projetofinal_smartcoffee.User;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class RegistrationManager extends Authenticator {
    private String user, mail, pass, repass;

    public RegistrationManager(UserDatabase db) {
        super.setDatabase(db);
    }

    public void setDetails(String user, String mail, String pass, String repass) {
        this.user = user;
        this.mail = mail;
        this.pass = pass;
        this.repass = repass;
    }

    private boolean isEmpty() {
        if(user.isEmpty()) {
            concatError(getMessage("userEmpty"));
        }
        if(mail.isEmpty()) {
            concatError(getMessage("mailEmpty"));
        }
        if(pass.isEmpty()) {
            concatError(getMessage("passEmpty"));
        }
        return user.isEmpty() || mail.isEmpty() || pass.isEmpty();
    }


    public boolean isRegistrationSuccessful() {
        boolean retval = true;

        if(isEmpty()) {
            return false;
        }
        if(!pass.equals(repass)) {
            concatError(getMessage("passNoMatch"));
            return false;
        }
        if(db.userExists(user)) {
            setError(getMessage("userExists"));
            retval = false;
        }

        return retval;
    }
}
