package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.projetofinal_smartcoffee.Database.ClienteDB;
import com.example.projetofinal_smartcoffee.Database.UserDatabase;
import com.example.projetofinal_smartcoffee.Util.MessageBox;
import com.example.projetofinal_smartcoffee.Util.RegistrationManager;


public class RegisterActivity extends AppCompatActivity {

    EditText tbNome, tbMail, tbPass, tbRepass;

    SQLiteDatabase db = null;

    private void BindControls() {
        tbNome      = findViewById(R.id.tbNome);
        tbMail      = findViewById(R.id.tbMail);
        tbPass      = findViewById(R.id.tbPass);
        tbRepass    = findViewById(R.id.tbRepass);
    }

    private void CreateCliente(Cliente c, String repass) {
        MessageBox msg = new MessageBox(this);

        if(c.getNome().isEmpty()) {
            msg.show("Ocorreu um erro", "Introduza um nome de utilizador", 0);
        }

        if(c.getPass().equals(repass) && !c.getPass().isEmpty()) {
            c.setPassword(c.getPass());

            ContentValues v = new ContentValues();
            v.put("nome", c.getNome());
            v.put("mail", c.getMail());
            v.put("password", c.getPass());

            db.insert("cliente", null, v);

            msg.show("Registo bem sucedido", String.format("Utilizador %s registado com sucesso!", c.getNome()), 0);
        }
        else {
            msg.show("Ocorreu um erro", c.getPass().isEmpty() ? "Introduza uma password!" : "A password não coincide", 0);
        }
    }

    public void EfetuarRegisto(View v) {
        UserDatabase userDB = (UserDatabase)getIntent().getExtras().getSerializable("userDB");
        userDB.setContext(this);
        userDB.open();

        String user     = tbNome.getText().toString();
        String mail     = tbMail.getText().toString();
        String pass     = tbPass.getText().toString();
        String repass   = tbRepass.getText().toString();

        RegistrationManager reg = new RegistrationManager(userDB);
        reg.setDetails(user, mail, pass, repass);

        MessageBox.SetContext(this);
        User u = new User(user, pass, mail);
        if(reg.isRegistrationSuccessful()) {
            // O registo foi bem sucedido
            MessageBox.Show("Registado com sucesso!", String.format("A sua conta %s foi registada com sucesso!\nBem-Vindo à Smart Coffee!", u.getName()), R.drawable.information_icon_svg,
                (dialogInterface, i) -> {
                    userDB.addUser(u);
                    userDB.close();
                    StartActivityLogin();
                });
        } else {
            // O registo falhou
            MessageBox.Show("Erro", reg.getError(), R.drawable.error_flat);
        }
    }

    private void StartActivityLogin() {
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