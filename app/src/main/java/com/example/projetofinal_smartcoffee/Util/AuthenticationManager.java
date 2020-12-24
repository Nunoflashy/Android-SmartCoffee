package com.example.projetofinal_smartcoffee.Util;

import com.example.projetofinal_smartcoffee.Cliente;
import com.example.projetofinal_smartcoffee.Database.ClienteDB;

public class AuthenticationManager {
    private String user, pass;
    private String error = "";

    public AuthenticationManager() {}
    public AuthenticationManager(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public void setDetails(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    private void setError(String msg) {
        error += msg + '\n';
    }
    public String getError() { return error; }

    public boolean isLoginSuccessful() {
        //boolean retval = false;

        if(user.isEmpty()) { setError("O username não pode estar em branco!"); }
        if(pass.isEmpty()) { setError("A password não pode estar em branco!"); }

        if(user.isEmpty() || pass.isEmpty()) { return false; }

//        if(user.isEmpty() || pass.isEmpty()) {
//            setError(user.isEmpty() ? "O username não pode estar em branco!" : "A password não pode estar em branco!");
//            return false;
//        }
        for(Cliente c : ClienteDB.GetAll()) {
//            if(!user.equals(c.getNome())) {
//                setError("O utilizador é inválido!");
//                return retval;
//            }
//            if(!pass.equals(c.getPass())) {
//                setError("A password é inválida!");
//            }
//            if(ClienteDB.IsClienteBlocked(c)) {
//                // TODO: Retornar erro de cliente bloqueado na DB
//                setError(String.format("A conta %s está indisponível", c.getNome()));
//                return false;
//            }
            // TODO: Computar o hash da password no login com o da DB
            if(user.equals(c.getNome()) && pass.equals(c.getPass())) {
                return true;
            } else {
                setError("Os detalhes estão inválidos!");
                return false;
            }
        }
        setError("O cliente não existe na base de dados!");
        return false;
    }
}
