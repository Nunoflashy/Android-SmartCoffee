package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.projetofinal_smartcoffee.Database.DatabaseManager;
import com.example.projetofinal_smartcoffee.Database.UserDatabase;
import com.example.projetofinal_smartcoffee.Database.UserType;
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
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void StartActivityListClientes(View v) {
        startActivity(new Intent(this, ListClientesActivity.class));
    }

    public void LoginPerformed(View v) {
        String user = tbUsername.getText().toString();
        String pass = tbPassword.getText().toString();

        AuthenticationManager auth = new AuthenticationManager(userDB);
        auth.setDetails(user, pass);

        auth.setMessage("userEmpty", "Insira o nome de utilizador.");
        auth.setMessage("passEmpty", "Insira a password");
        auth.setMessage("userNoExist", "O utilizador não existe!");
        auth.setMessage("accountBlocked", "Esta conta encontra-se bloqueada");
        auth.setMessage("invalidAuth", "Os detalhes são invalidos!");

        userDB.open();

        MessageBox msg = new MessageBox(this);

        if(auth.isLoginSuccessful()) {
            msg.setMessage("Login", "O login foi efetuado com sucesso!");
            msg.setIcon(R.drawable.information_icon_svg);
            msg.setPositiveButton((dialogInterface, i) -> {
                // TODO: Detetar tipo de login (Cliente, Admin) e redirecionar para a activity certa.
                User u = userDB.getUserByName(auth.getUser());
                AuthenticationManager.SetAuthenticatedUser(u);
                if(userDB.isUserAdmin(u)) {
                    // Admin Activity
                    startActivity(new Intent(this, AdminMenuActivity.class).putExtra("admin", u));
                } else {
                    // Cliente Activity
                    //startActivity(new Intent(this, DashboardAdminActivity.class));
                }
            });
        } else {
            msg.setTitle("Erro");
            msg.setMessage(auth.getError());
            msg.setIcon(R.drawable.error_flat);
        }
        msg.show();

//        if(auth.isLoginSuccessful()) {
//            MessageBox.Show("Login", "O login foi efetuado com sucesso!", R.drawable.information_icon_svg,
//            (dialogInterface, i) -> {
//                // TODO: Login activity
//
//            });
//        } else {
//            MessageBox.Show("Erro", auth.getError(), R.drawable.error_flat);
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        BindControls();
    }
}