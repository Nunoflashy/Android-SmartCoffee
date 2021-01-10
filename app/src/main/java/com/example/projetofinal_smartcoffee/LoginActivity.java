package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.projetofinal_smartcoffee.Database.DatabaseManager;
import com.example.projetofinal_smartcoffee.Database.UserDatabase;
import com.example.projetofinal_smartcoffee.Database.UserType;
import com.example.projetofinal_smartcoffee.Util.AuthenticationManager;
import com.example.projetofinal_smartcoffee.Util.MessageBox;
import com.example.projetofinal_smartcoffee.Util.RegistrationManager;

public class LoginActivity extends AppCompatActivity {

    EditText tbUsername;
    EditText tbPassword;

    UserDatabase userDB = DatabaseManager.GetDB("userDB");

    private void bindControls() {
        tbUsername = findViewById(R.id.tbUsername);
        tbPassword = findViewById(R.id.tbPassword);
    }

    public void StartActivityRegister(View v) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void LoginPerformed(View v) {
        String user = tbUsername.getText().toString();
        String pass = tbPassword.getText().toString();

        AuthenticationManager auth = new AuthenticationManager(userDB);
        auth.setDetails(user, pass);

        auth.setMessage("userEmpty", getString(R.string.authUserEmpty));
        auth.setMessage("passEmpty", getString(R.string.authPassEmpty));
        auth.setMessage("userNoExist", getString(R.string.authUserNoExist));
        auth.setMessage("accountBlocked", getString(R.string.authAccountBlocked));
        auth.setMessage("invalidAuth", getString(R.string.authInvalidAuth));

        MessageBox msg = new MessageBox(this);

        if(auth.isLoginSuccessful()) {
            msg.show("Login", getString(R.string.authLoginSuccess), R.drawable.information_icon_svg,
            (dialogInterface, i) -> {
                User u = userDB.getUserByName(auth.getUser()); // Obter o user a partir do nome
                AuthenticationManager.SetAuthenticatedUser(u); // Torná-lo como o user autenticado

                // Se nao existir conta de admin, tornar esta num admin
                if(!userDB.hasAdminAccount()) {
                    userDB.setUserType(u, UserType.Admin);
                    Log.d("UserDB", String.format("A conta %s é agora uma conta de administrador.", u.getName()));
                }
                // Detectar o tipo de conta e redirecionar para a activity certa
                if(userDB.isUserAdmin(u)) {
                    startActivity(new Intent(this, AdminMenuActivity.class).putExtra("admin", u));
                } else {
                    startActivity(new Intent(this, UserDashboardActivity.class).putExtra("user", u));
                }
                finish();
            });
        } else {
            msg.show(getString(R.string.authErrorTitle), auth.getError(), R.drawable.error_flat);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindControls();
    }
}