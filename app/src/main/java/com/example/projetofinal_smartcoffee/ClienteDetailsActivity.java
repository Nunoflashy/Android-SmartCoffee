package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.projetofinal_smartcoffee.Database.DatabaseManager;
import com.example.projetofinal_smartcoffee.Database.UserDatabase;
import com.example.projetofinal_smartcoffee.Util.MessageBox;

public class ClienteDetailsActivity extends AppCompatActivity {

    TextView tvID, tvUsername, tvMail, tvEstado, tvBlock, tvRemove;
    Spinner spUserType;
    UserDatabase userDB = DatabaseManager.GetDB("userDB");
    User u;

    private void BindControls() {
        tvID       = findViewById(R.id.tvID);
        tvUsername = findViewById(R.id.tvUsername);
        tvMail     = findViewById(R.id.tvMail);
        tvEstado   = findViewById(R.id.tvEstado);
        tvBlock    = findViewById(R.id.tvBlock);
        tvRemove   = findViewById(R.id.tvRemove);
        spUserType = findViewById(R.id.spUserType);
    }

    private String[] userTypes = new String[] {"Cliente", "Administrador"};


    private void init() {
        ArrayAdapter<String> userAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, userTypes);
        spUserType.setAdapter(userAdapter);
//        spUserType.setOnItemClickListener((parent, view, i, id) -> {
//            String type = (String)spUserType.getSelectedItem();
//        });
    }

    public void BlockUser_OnClick(View v) {
        if(!userDB.isUserBlocked(u)) {
            // Estado do User: Normal
            MessageBox.Show("Bloquear", String.format("Tem a certeza que pretende bloquear o utilizador %s?", u.getName()), R.drawable.blockiconred,
            "Bloquear", (dialogInterface, i) -> {
                userDB.blockUser(u);
                MessageBox.Show("Bloquear", String.format("O utilizador %s foi bloqueado do sistema.", u.getName()), R.drawable.blockiconred);
                update();
            },
            "Cancelar", (dialogInterface, i) -> {
                return;
            });
        } else {
            // Estado do User: Bloqueado
            MessageBox.Show("Desbloquear", String.format("Tem a certeza que pretende desbloquear o utilizador %s?", u.getName()), R.drawable.blockicon,
            "Desbloquear", (dialogInterface, i) -> {
                userDB.unblockUser(u);
                MessageBox.Show("Desbloquear", String.format("O utilizador %s foi desbloqueado do sistema.", u.getName()), R.drawable.blockicon);
                update();
            },
            "Cancelar", (dialogInterface, i) -> {
                return;
            });
        }
    }

    public void RemoveUser_OnClick(View v) {
        MessageBox.Show("Remover", String.format("Tem a certeza que pretende remover o utilizador %s?", u.getName()), R.drawable.removeicon,
        "Remover", (dialogInterface, i) -> {
            userDB.removeUser(u);
            MessageBox.Show("Remover", String.format("O utilizador %s foi removido do sistema.", u.getName()), R.drawable.removeicon);
        },
        "Cancelar", (dialogInterface, i) -> {
            return;
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_details);

        BindControls();

        Intent it = getIntent();
        u = (User)it.getExtras().getSerializable("user");

        userDB.open();

        MessageBox.SetContext(this);
        init();
        update();
    }

    private void update() {
        tvID.setText(String.format("ID: %s", u.getID()));
        tvUsername.setText(String.format("Username: %s", u.getName()));
        tvMail.setText(String.format("E-Mail: %s", u.getMail()));
        tvEstado.setText(String.format("Estado: %s", userDB.isUserBlocked(u) ? "Bloqueado" : "Normal"));
        tvBlock.setText(userDB.isUserBlocked(u) ? "Desbloquear" : "Bloquear");
    }

    public void StartActivityListClientes(View v) {
        Intent it = new Intent(this, ListClientesActivity.class);
        startActivity(it);
    }
}