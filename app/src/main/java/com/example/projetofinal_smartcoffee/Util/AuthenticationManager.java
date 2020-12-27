package com.example.projetofinal_smartcoffee.Util;

import android.util.Log;

import com.example.projetofinal_smartcoffee.Cliente;
import com.example.projetofinal_smartcoffee.Database.ClienteDB;
import com.example.projetofinal_smartcoffee.Database.IDatabase;
import com.example.projetofinal_smartcoffee.Database.UserDatabase;
import com.example.projetofinal_smartcoffee.R;
import com.example.projetofinal_smartcoffee.User;

import java.util.List;

public class AuthenticationManager {
    private String user, pass;
    private String error = "";

    private UserDatabase db;

    public AuthenticationManager(UserDatabase db) {
        setDatabase(db);
    }

    public void setDatabase(UserDatabase db) {
        this.db = db;
    }

    public UserDatabase getDatabase() {
        return db;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public boolean isEmpty() {
        if(user.isEmpty()) {
            concatError("Insira o nome de utilizador.");
        }
        if(pass.isEmpty()) {
            concatError("Insira a password.");
        }

        return user.isEmpty() || pass.isEmpty();
    }

    public void setDetails(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    private void setError(String msg) {
        error = msg + '\n';
    }

    private void concatError(String msg) {
        error += msg + '\n';
    }

    public String getError() { return error; }

    public boolean isLoginSuccessful() {
        boolean retval = false;

        for(User u : db.getAll()) {
            // TODO: Computar o hash da password no login com o da DB
            if (user.equals(u.getName()) && pass.equals(u.getPass())) {
                // Verificar se a conta se encontra bloqueada
                if(db.isUserBlocked(u.getName())) {
                    setError("Esta conta encontra-se bloqueada.");
                    retval = false;
                    break;
                }
                retval = true;
                break;
            } else {
                retval = false;
                setError("Os detalhes sao invalidos!");
            }
        }
        return retval;
    }

//    public boolean isLoginSuccessful() {
//        //boolean retval = false;
//
//        if(user.isEmpty()) { setError("O username não pode estar em branco!"); }
//        if(pass.isEmpty()) { setError("A password não pode estar em branco!"); }
//
//        if(user.isEmpty() || pass.isEmpty()) { return false; }
//
//
//        for(Cliente c : ClienteDB.GetAll()) {
////            if(!user.equals(c.getNome())) {
////                setError("O utilizador é inválido!");
////                return retval;
////            }
////            if(!pass.equals(c.getPass())) {
////                setError("A password é inválida!");
////            }
////            if(ClienteDB.IsClienteBlocked(c)) {
////                // TODO: Retornar erro de cliente bloqueado na DB
////                setError(String.format("A conta %s está indisponível", c.getNome()));
////                return false;
////            }
//            // TODO: Computar o hash da password no login com o da DB
//            if(user.equals(c.getNome()) && pass.equals(c.getPass())) {
//                return true;
//            } else {
//                setError("Os detalhes estão inválidos!");
//                return false;
//            }
//        }
//        setError("O cliente não existe na base de dados!");
//        return false;
//    }
}
