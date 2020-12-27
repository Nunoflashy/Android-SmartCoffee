package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.projetofinal_smartcoffee.Database.UserDatabase;
import com.example.projetofinal_smartcoffee.Database.ClienteDB;
import com.example.projetofinal_smartcoffee.Database.IDatabase;
import com.example.projetofinal_smartcoffee.Util.AuthenticationManager;
import com.example.projetofinal_smartcoffee.Util.MessageBox;

public class LoginActivity extends AppCompatActivity {

    EditText tbUsername;
    EditText tbPassword;

    UserDatabase userDB;

    private void BindControls() {
        tbUsername = findViewById(R.id.tbUsername);
        tbPassword = findViewById(R.id.tbPassword);
    }

    public void StartActivityRegister(View v) {
        userDB = (UserDatabase)getIntent().getExtras().getSerializable("userDB");
        Intent it = new Intent(this, RegisterActivity.class);
        it.putExtra("userDB", userDB);
        startActivity(it);
    }

    public void StartActivityListClientes(View v) {
        Intent it = new Intent(this, ListClientesActivity.class);
        startActivity(it);
    }

//    public void LoginPerformed(View v) {
//        UserDatabase users = new UserDatabase(this, "db_SmartCoffee");
//        users.open();
//        for(User u : users.getAll()) {
//            Log.d("UserDB", String.format("\nUser: %s\nPass: %s\nMail:%s\n", u.getName(), u.getPass(), u.getMail()));
//        }
//
//        AuthenticationManager auth = new AuthenticationManager(users);
//        auth.setDetails(tbUsername.getText().toString(), tbPassword.getText().toString());
//
//        if(auth.isLoginSuccessful()) {
//            // Login com sucesso
//            MessageBox.Show("Login", "O login foi efetuado com sucesso!", R.drawable.information_icon_svg,
//            (dialogInterface, i) -> {
//                // TODO: Login activity
//
//            });
//        } else {
//            MessageBox.Show("Erro", auth.getError(), R.drawable.error_flat);
//        }
//    }

//    public void LoginPerformed(View v) {
//        AuthenticationManager auth = new AuthenticationManager();
//        auth.setDetails(tbUsername.getText().toString(), tbPassword.getText().toString());
//
//        if(auth.isLoginSuccessful()) {
//            // Login com sucesso
//            MessageBox.Show("Login", "O login foi efetuado com sucesso!", R.drawable.information_icon_svg,
//            (dialogInterface, i) -> {
//                // TODO: Login activity
//
//            });
//        } else {
//            MessageBox.Show("Erro", auth.getError(), R.drawable.error_flat);
//        }
//
////        for(Cliente c : ClienteDB.GetAll()) {
////            // TODO: Computar o hash da password no login com o da DB
////            if(tbUsername.getText().toString().equals(c.getNome()) &&
////               tbPassword.getText().toString().equals(c.getPass())) {
////                // Login com sucesso
////                MessageBox.Show("Login", "O login foi efetuado com sucesso!", R.drawable.information_icon_svg,
////                (dialogInterface, i) -> {
////                    // TODO: Login activity
////
////                });
////            }
////            else if(tbUsername.getText().toString().equals(c.getNome())) {
////                MessageBox.Show("Erro", "A password digitada é invalida!", R.drawable.error_flat);
////            }
////        }
//    }

    public void LoginPerformed(View v) {
        userDB = (UserDatabase)getIntent().getExtras().getSerializable("userDB");
        userDB.setContext(this);
        userDB.open();

        String user = tbUsername.getText().toString();
        String pass = tbPassword.getText().toString();

        AuthenticationManager auth = new AuthenticationManager(userDB);
        auth.setDetails(user, pass);

        MessageBox.SetContext(this);

        if(auth.isEmpty()) {
            MessageBox.Show("Erro", auth.getError(), R.drawable.error_flat);
            return;
        }

        if(!userDB.userExists(auth.getUser())) {
            MessageBox.Show("Erro", "O utilizador não existe!", R.drawable.error_flat);
            return;
        }

        if(auth.isLoginSuccessful()) {
            MessageBox.Show("Login", "O login foi efetuado com sucesso!", R.drawable.information_icon_svg,
            (dialogInterface, i) -> {
                // TODO: Login activity

            });
        } else {
            MessageBox.Show("Erro", auth.getError(), R.drawable.error_flat);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MessageBox.SetContext(this);

        BindControls();
    }
}