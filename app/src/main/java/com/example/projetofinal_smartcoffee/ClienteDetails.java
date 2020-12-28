package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.projetofinal_smartcoffee.Database.UserDatabase;
import com.example.projetofinal_smartcoffee.Util.MessageBox;

public class ClienteDetails extends AppCompatActivity {

    TextView tvID, tvUsername, tvMail, tvEstado;
    UserDatabase userDB;
    User u;

    private void BindControls() {
        tvID       = findViewById(R.id.tvID);
        tvUsername = findViewById(R.id.tvUsername);
        tvMail     = findViewById(R.id.tvMail);
        tvEstado   = findViewById(R.id.tvEstado);
    }

    public void ToggleBlockUser(View v) {
        UserDatabase userDB = new UserDatabase(this, "db_SmartCoffee");
        userDB.setContext(this);
        userDB.open();
        // DBManager.GetDatabase("userDB");
        MessageBox.SetContext(this);
        if(!userDB.isUserBlocked(u)) {
            MessageBox.Show("Info", String.format("Tem a certeza que pretende bloquear o utilizador %s?", u.getName()), R.drawable.information_icon_svg, "Bloquear", "Cancelar",
                    (dialogInterface, i) -> {
                        userDB.blockUser(u);
                        MessageBox.Show("Info", String.format("O utilizador %s foi bloqueado no sistema.", u.getName()), R.drawable.information_icon_svg);
                    },
                    (dialogInterface, i) -> {
                        return;
                    });
        } else {
            MessageBox.Show("Info", String.format("Tem a certeza que pretende desbloquear o utilizador %s?", u.getName()), R.drawable.information_icon_svg, "Bloquear", "Cancelar",
                    (dialogInterface, i) -> {
                        userDB.unblockUser(u);
                        MessageBox.Show("Info", String.format("O utilizador %s foi desbloqueado no sistema.", u.getName()), R.drawable.information_icon_svg);
                    },
                    (dialogInterface, i) -> {
                        return;
                    });
        }
        UpdateUser();
    }

    public void RemoveUser(View v) {
        UserDatabase userDB = new UserDatabase(this, "db_SmartCoffee");
        userDB.setContext(this);
        userDB.open();

        MessageBox.Show("Info", String.format("Tem a certeza que pretende remover o utilizador %s?", u.getName()), R.drawable.information_icon_svg, "Bloquear", "Cancelar",
            (dialogInterface, i) -> {
                userDB.removeUser(u);
                MessageBox.Show("Info", String.format("O utilizador %s foi removido do sistema.", u.getName()), R.drawable.information_icon_svg);
            },
            (dialogInterface, i) -> {
                return;
            });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_details);

        BindControls();

        Intent it = getIntent();
        u = (User) it.getExtras().getSerializable("user");

        userDB = new UserDatabase(this, "db_SmartCoffee");
        userDB.setContext(this);
        userDB.open();

        UpdateUser();
    }

    private void UpdateUser() {
        tvID.setText(String.format("ID: %s", u.getID()));
        tvUsername.setText(String.format("Username: %s", u.getName()));
        tvMail.setText(String.format("E-Mail: %s", u.getMail()));
        tvEstado.setText(String.format("Estado: %s", userDB.isUserBlocked(u) ? "Bloqueado" : "Normal"));
    }

    public void StartActivityListClientes(View v) {
        Intent it = new Intent(this, ListClientesActivity.class);
        startActivity(it);
    }
}