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
import com.example.projetofinal_smartcoffee.Database.DatabaseManager;
import com.example.projetofinal_smartcoffee.Database.UserDatabase;
import com.example.projetofinal_smartcoffee.Util.MessageBox;
import com.example.projetofinal_smartcoffee.Util.RegistrationManager;

import java.util.Dictionary;


public class RegisterActivity extends AppCompatActivity {

    EditText tbNome, tbMail, tbPass, tbRepass;

    //UserDatabase userDB = null;
    UserDatabase userDB = DatabaseManager.GetDB("userDB");

    private void BindControls() {
        tbNome      = findViewById(R.id.tbNome);
        tbMail      = findViewById(R.id.tbMail);
        tbPass      = findViewById(R.id.tbPass);
        tbRepass    = findViewById(R.id.tbRepass);
    }

    public void EfetuarRegisto(View v) {
        String user     = tbNome.getText().toString();
        String mail     = tbMail.getText().toString();
        String pass     = tbPass.getText().toString();
        String repass   = tbRepass.getText().toString();

        RegistrationManager reg = new RegistrationManager(userDB);
        reg.setDetails(user, mail, pass, repass);

        reg.setMessage("userEmpty","O user não pode estar vazio!");
        reg.setMessage("mailEmpty","O mail não pode estar vazio!");
        reg.setMessage("passEmpty","A password não pode estar vazia!");
        reg.setMessage("passNoMatch","As passwords não coincidem!");
        reg.setMessage("userExists","Este user já se encontra registado!");

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

//    @Override
//    public void onBackPressed() {
//        userDB.close();
//        super.onBackPressed();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        MessageBox.SetContext(this);

        //userDB = (UserDatabase)getIntent().getExtras().getSerializable("userDB");
        userDB.setContext(this);
        userDB.open();

        BindControls();


    }
}