package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.projetofinal_smartcoffee.Database.ClienteDB;
import com.example.projetofinal_smartcoffee.Util.AuthenticationManager;
import com.example.projetofinal_smartcoffee.Util.MessageBox;
import com.example.projetofinal_smartcoffee.Util.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText tbUsername;
    EditText tbPassword;

    private void BindControls() {
        tbUsername = findViewById(R.id.tbUsername);
        tbPassword = findViewById(R.id.tbPassword);
    }

    public void StartActivityRegister(View v) {
        Intent it = new Intent(this, RegisterActivity.class);
        startActivity(it);
    }

    public void StartActivityListClientes(View v) {
        Intent it = new Intent(this, ListClientesActivity.class);
        startActivity(it);
    }

    public void LoginPerformed(View v) {
        AuthenticationManager auth = new AuthenticationManager();
        auth.setDetails(tbUsername.getText().toString(), tbPassword.getText().toString());

        if(auth.isLoginSuccessful()) {
            // Login com sucesso
            MessageBox.Show("Login", "O login foi efetuado com sucesso!", R.drawable.information_icon_svg,
            (dialogInterface, i) -> {
                // TODO: Login activity

            });
        } else {
            MessageBox.Show("Erro", auth.getError(), R.drawable.error_flat);
        }

//        for(Cliente c : ClienteDB.GetAll()) {
//            // TODO: Computar o hash da password no login com o da DB
//            if(tbUsername.getText().toString().equals(c.getNome()) &&
//               tbPassword.getText().toString().equals(c.getPass())) {
//                // Login com sucesso
//                MessageBox.Show("Login", "O login foi efetuado com sucesso!", R.drawable.information_icon_svg,
//                (dialogInterface, i) -> {
//                    // TODO: Login activity
//
//                });
//            }
//            else if(tbUsername.getText().toString().equals(c.getNome())) {
//                MessageBox.Show("Erro", "A password digitada é invalida!", R.drawable.error_flat);
//            }
//        }
    }

    public void DeleteDB(View v) {
        ClienteDB.Delete();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MessageBox.SetContext(this);

        BindControls();
    }
}