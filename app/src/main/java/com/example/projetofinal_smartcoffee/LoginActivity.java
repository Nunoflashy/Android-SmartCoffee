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

        //userDB.open();

        MessageBox msg = new MessageBox(this);

        if(auth.isLoginSuccessful()) {
            msg.setMessage("Login", getString(R.string.authLoginSuccess));
            msg.setIcon(R.drawable.information_icon_svg);
            msg.setPositiveButton((dialogInterface, i) -> {
                // TODO: Detetar tipo de login (Cliente, Admin) e redirecionar para a activity certa.
                User u = userDB.getUserByName(auth.getUser());
                AuthenticationManager.SetAuthenticatedUser(u);
                if(!hasAdminAccount()) {
                    userDB.setUserType(u, UserType.Admin);
                    msg.show("Admin", "Admin Set", R.drawable.information_icon_svg);
                }
                if(userDB.isUserAdmin(u)) {
                    // Admin Activity
                    finish();
                    startActivity(new Intent(this, AdminMenuActivity.class).putExtra("admin", u));
                } else {
                    // Cliente Activity
                    finish();
                    startActivity(new Intent(this, UserDashboardActivity.class).putExtra("user", u));
                }
            });
        } else {
            msg.setTitle(getString(R.string.authErrorTitle));
            msg.setMessage(auth.getError());
            msg.setIcon(R.drawable.error_flat);
        }
        msg.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindControls();
    }

    private boolean hasAdminAccount() {
        for(User u : userDB.getAll()) {
            if(userDB.isUserAdmin(u)) return true;
        }
        return false;
    }
}