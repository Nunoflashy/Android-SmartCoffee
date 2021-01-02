package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projetofinal_smartcoffee.Database.DatabaseManager;
import com.example.projetofinal_smartcoffee.Database.UserDatabase;
import com.example.projetofinal_smartcoffee.Util.MessageBox;

public class ClienteDetails extends AppCompatActivity {

    TextView tvID, tvUsername, tvMail, tvEstado, tvBlock, tvRemove;
    UserDatabase userDB = DatabaseManager.GetDB("userDB");
    User u;

    private void BindControls() {
        tvID       = findViewById(R.id.tvID);
        tvUsername = findViewById(R.id.tvUsername);
        tvMail     = findViewById(R.id.tvMail);
        tvEstado   = findViewById(R.id.tvEstado);
        tvBlock    = findViewById(R.id.tvBlock);
        tvRemove   = findViewById(R.id.tvRemove);
    }

    public void BlockUser_OnClick(View v) {
        if(!userDB.isUserBlocked(u)) {
            // Estado do User: Normal
            MessageBox.Show("Info", String.format("Tem a certeza que pretende bloquear o utilizador %s?", u.getName()), R.drawable.information_icon_svg,
            "Bloquear", (dialogInterface, i) -> {
                userDB.blockUser(u);
                MessageBox.Show("Info", String.format("O utilizador %s foi bloqueado do sistema.", u.getName()), R.drawable.information_icon_svg);
                UpdateUser();
            },
            "Cancelar", (dialogInterface, i) -> {
                return;
            });
        } else {
            // Estado do User: Bloqueado
            MessageBox.Show("Info", String.format("Tem a certeza que pretende desbloquear o utilizador %s?", u.getName()), R.drawable.information_icon_svg,
            "Desbloquear", (dialogInterface, i) -> {
                userDB.unblockUser(u);
                MessageBox.Show("Info", String.format("O utilizador %s foi desbloqueado do sistema.", u.getName()), R.drawable.information_icon_svg);
                UpdateUser();
            },
            "Cancelar", (dialogInterface, i) -> {
                return;
            });
        }

    }

    public void RemoveUser_OnClick(View v) {
        MessageBox.Show("Info", String.format("Tem a certeza que pretende remover o utilizador %s?", u.getName()), R.drawable.information_icon_svg,
        "Remover", (dialogInterface, i) -> {
            userDB.removeUser(u);
            MessageBox.Show("Info", String.format("O utilizador %s foi removido do sistema.", u.getName()), R.drawable.information_icon_svg);
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

        //userDB = (UserDatabase)getIntent().getExtras().getSerializable("userDB");
        userDB.setContext(this);
        userDB.open();

        MessageBox.SetContext(this);

        UpdateUser();
    }

    private void UpdateUser() {
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