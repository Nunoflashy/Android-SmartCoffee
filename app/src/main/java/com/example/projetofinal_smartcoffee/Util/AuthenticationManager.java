package com.example.projetofinal_smartcoffee.Util;

import com.example.projetofinal_smartcoffee.Database.UserDatabase;
import com.example.projetofinal_smartcoffee.Exception.InvalidUserException;
import com.example.projetofinal_smartcoffee.Exception.UserNotFoundException;
import com.example.projetofinal_smartcoffee.User;

public class AuthenticationManager extends Authenticator {
    private String user, pass;

    public AuthenticationManager(UserDatabase db) {
        super.setDatabase(db);
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public static User GetAuthenticatedUser() {
        if(authUser == null) {
            throw new InvalidUserException("There is no authenticated user!");
        }
        return authUser;
    }
    private static User authUser = null;

    public static void SetAuthenticatedUser(User u) {
        authUser = u;
    }

    public boolean isEmpty() {
        if(user.isEmpty()) {
            concatError(getMessage("userEmpty"));
        }
        if(pass.isEmpty()) {
            concatError(getMessage("passEmpty"));
        }

        return user.isEmpty() || pass.isEmpty();
    }

    public void setDetails(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public boolean isLoginSuccessful() {
        boolean retval = false;

        if(isEmpty()) {
            return false;
        }

        if(!db.isOpen()) {
            db.open();
        }

        if(!db.userExists(user)) {
            setError(getMessage("userNoExist"));
            return false;
        }

        for(User u : db.getAll()) {
            // TODO: Computar o hash da password no login com o da DB
            if (user.equals(u.getName()) && pass.equals(u.getPass())) {
                // Verificar se a conta se encontra bloqueada
                if(db.isUserBlocked(u.getName())) {
                    setError(getMessage("accountBlocked"));
                    retval = false;
                    break;
                }
                retval = true;
                break;
            } else {
                retval = false;
                setError(getMessage("invalidAuth"));
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
