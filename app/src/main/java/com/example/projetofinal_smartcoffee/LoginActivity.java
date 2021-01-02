package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.projetofinal_smartcoffee.Database.DatabaseManager;
import com.example.projetofinal_smartcoffee.Database.UserDatabase;
import com.example.projetofinal_smartcoffee.Database.ClienteDB;
import com.example.projetofinal_smartcoffee.Database.IDatabase;
import com.example.projetofinal_smartcoffee.Util.AuthenticationManager;
import com.example.projetofinal_smartcoffee.Util.MessageBox;

public class LoginActivity extends AppCompatActivity {

    EditText tbUsername;
    EditText tbPassword;

    UserDatabase userDB = DatabaseManager.GetDB("userDB");

    private void BindControls() {
        tbUsername = findViewById(R.id.tbUsername);
        tbPassword = findViewById(R.id.tbPassword);
    }

    public void StartActivityRegister(View v) {
        Intent it = new Intent(this, RegisterActivity.class);
//        userDB = (UserDatabase)getIntent().getExtras().getSerializable("userDB");
//        it.putExtra("userDB", userDB);
        startActivity(it);
    }

    public void StartActivityListClientes(View v) {
        Intent it = new Intent(this, ListClientesActivity.class);
//        userDB = (UserDatabase)getIntent().getExtras().getSerializable("userDB");
//        it.putExtra("userDB", userDB);
        startActivity(it);
    }

    public void LoginPerformed(View v) {
        //userDB = (UserDatabase)getIntent().getExtras().getSerializable("userDB");
//        userDB.setContext(this);
//        userDB.open();
        MessageBox.SetContext(this);
        String user = tbUsername.getText().toString();
        String pass = tbPassword.getText().toString();

        AuthenticationManager auth = new AuthenticationManager(userDB);
        auth.setDetails(user, pass);

        auth.setMessage("userEmpty", "Insira o nome de utilizador.");
        auth.setMessage("passEmpty", "Insira a password");
        auth.setMessage("userNoExist", "O utilizador não existe!");
        auth.setMessage("accountBlocked", "Esta conta encontra-se bloqueada");
        auth.setMessage("invalidAuth", "Os detalhes são invalidos!");


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

        //userDB = (UserDatabase)getIntent().getExtras().getSerializable("userDB");
        //userDB = DatabaseManager.GetUserDB("userDB");
        userDB.setContext(this);
        userDB.open();

        BindControls();
    }
}