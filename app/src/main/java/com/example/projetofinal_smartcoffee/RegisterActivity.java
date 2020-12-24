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
import com.example.projetofinal_smartcoffee.Util.MessageBox;


public class RegisterActivity extends AppCompatActivity {

    EditText tbNome, tbMail, tbPass, tbRepass;

    SQLiteDatabase db = null;

    private void BindControls() {
        tbNome      = findViewById(R.id.tbNome);
        tbMail      = findViewById(R.id.tbMail);
        tbPass      = findViewById(R.id.tbPass);
        tbRepass    = findViewById(R.id.tbRepass);
    }

    private void InitDatabase() {
        db = openOrCreateDatabase("db_SmartCoffee", Context.MODE_PRIVATE, null);
        CreateDatabase();
    }

    private void CreateDatabase() {
        db.execSQL("CREATE TABLE IF NOT EXISTS cliente(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome VARCHAR NOT NULL, " +
                "mail VARCHAR NOT NULL, " +
                "password VARCHAR NOT NULL);");
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
        ClienteDB.Create();

        String nome     = tbNome.getText().toString();
        String mail     = tbMail.getText().toString();
        String pass     = tbPass.getText().toString();
        String repass   = tbRepass.getText().toString();

        Cliente c = new Cliente(nome, mail, pass);

        MessageBox msg = new MessageBox(this);

        boolean nomeEmpty = c.getNome().isEmpty();
        boolean mailEmpty = c.getMail().isEmpty();
        boolean passEmpty = c.getPass().isEmpty();
        boolean passwordsMatch = c.getPass().equals(repass) && !c.getPass().isEmpty();

        if(nomeEmpty) {
            MessageBox.Show("Erro", "O username não pode ser deixado em branco!", R.drawable.error_flat);
            return;
        }

        if(mailEmpty) {
            MessageBox.Show("Erro", "O mail não pode ser deixado em branco!", R.drawable.error_flat);
            return;
        }

        if(passEmpty) {
            MessageBox.Show("Erro", "A password não pode ser deixada em branco!", R.drawable.error_flat);
            return;
        }

        if(!passwordsMatch) {
            MessageBox.Show("Erro", "As passwords não coincidem!", R.drawable.error_flat);
            return;
        }


        ClienteDB.AddCliente(c);
        MessageBox.Show("Registado com sucesso!", String.format("A sua conta %s foi registada com sucesso!\nBem-Vindo à Smart Coffee!", c.getNome()), R.drawable.information_icon_svg,
            (dialogInterface, i) -> {
                ClienteDB.Close();
                StartActivityLogin();
        });
//        if(passwordsMatch) {
//            try {
//                ClienteDB.AddCliente(c);
//            }
//            catch(ClienteExistsException ex) {
//                msg.show("Cliente já existe", ex.getMessage(), 0);
//                //msg.show("Cliente já existe", ex.getMessage(), 0);
//            }
//            finally {
//                ClienteDB.Close();
//            }
//        }
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
        InitDatabase();
    }
}