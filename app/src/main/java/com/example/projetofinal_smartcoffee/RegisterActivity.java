package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.projetofinal_smartcoffee.Database.DatabaseManager;
import com.example.projetofinal_smartcoffee.Database.UserDatabase;
import com.example.projetofinal_smartcoffee.Util.MessageBox;
import com.example.projetofinal_smartcoffee.Util.RegistrationManager;


public class RegisterActivity extends AppCompatActivity {

    EditText tbNome, tbMail, tbPass, tbRepass;

    UserDatabase userDB = DatabaseManager.GetDB("userDB");

    private void BindControls() {
        tbNome      = findViewById(R.id.tbNome);
        tbMail      = findViewById(R.id.tbMail);
        tbPass      = findViewById(R.id.tbPass);
        tbRepass    = findViewById(R.id.tbRepass);
    }

    public void PerformRegistration(View v) {
        String user     = tbNome.getText().toString();
        String mail     = tbMail.getText().toString();
        String pass     = tbPass.getText().toString();
        String repass   = tbRepass.getText().toString();

        RegistrationManager reg = new RegistrationManager(userDB);
        reg.setDetails(user, mail, pass, repass);

        reg.setMessage("userEmpty", getString(R.string.regUserEmpty));
        reg.setMessage("mailEmpty", getString(R.string.regMailEmpty));
        reg.setMessage("passEmpty", getString(R.string.regPassEmpty));
        reg.setMessage("passNoMatch", getString(R.string.regPassNoMatch));
        reg.setMessage("userExists", getString(R.string.regUserExists));

        User u = new User(user, pass, mail);

        userDB.open();

        MessageBox msg = new MessageBox(this);

        if(reg.isRegistrationSuccessful()) {
            msg.show("Registo efetuado com sucesso!", String.format("A sua conta %s foi registada com sucesso!\nBem-Vindo à Smart Coffee!", u.getName()), R.drawable.information_icon_svg,
            (dialogInterface, i) -> {
                userDB.addUser(u);
                userDB.close();
                StartActivityLogin();
            });
        } else {
            msg.show("Erro", reg.getError(), R.drawable.error_flat);
        }
    }

    private void StartActivityLogin() {
        Intent it = new Intent(this, LoginActivity.class);
        startActivity(it);
    }

    public void StartActivityLogin(View v) {
        Intent it = new Intent(this, LoginActivity.class);
        startActivity(it);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        BindControls();
    }
}