package com.example.projetofinal_smartcoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.projetofinal_smartcoffee.Database.DatabaseManager;
import com.example.projetofinal_smartcoffee.Database.UserDatabase;
import com.example.projetofinal_smartcoffee.Database.UserType;
import com.example.projetofinal_smartcoffee.Util.AuthenticationManager;
import com.example.projetofinal_smartcoffee.Util.MessageBox;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ClienteDetailsActivity extends AppCompatActivity {

    TextView tvID, tvUsername, tvMail, tvEstado, tvBlock, tvRemove;
    RadioGroup radioUserTypes;
    RadioButton radioCustomer, radioAdmin;
    UserDatabase userDB = DatabaseManager.GetDB("userDB");
    User u;

    BottomNavigationView menubar;

    private void BindControls() {
        tvID       = findViewById(R.id.tvID);
        tvUsername = findViewById(R.id.tvUsername);
        tvMail     = findViewById(R.id.tvMail);
        tvEstado   = findViewById(R.id.tvEstado);
        tvBlock    = findViewById(R.id.tvBlock);
        tvRemove   = findViewById(R.id.tvRemove);
        radioUserTypes = findViewById(R.id.radioUserTypes);
        radioCustomer = findViewById(R.id.radioCustomer);
        radioAdmin    = findViewById(R.id.radioAdmin);

        //menubar = findViewById(R.id.menubar);
        //menubar.setOnNavigationItemSelectedListener(onItemSelected);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onItemSelected =
        item -> {
            switch(item.getItemId()) {
                case R.id.nav_overview:
                    startActivity(new Intent(this, LoginActivity.class));
                break;
                case R.id.nav_listUsers:
                    startActivity(new Intent(this, LoginActivity.class));
                break;
                case R.id.nav_settings:
                    startActivity(new Intent(this, LoginActivity.class));
                break;
                case R.id.nav_logout:
                    startActivity(new Intent(this, LoginActivity.class));
                break;

            }
            return true;
        };

    private void updateUserType() {
        if(userDB.isUserAdmin(u)) {
            radioAdmin.setChecked(true);
        } else {
            radioCustomer.setChecked(true);
        }
    }

    private void init() {
        updateUserType();
        radioUserTypes.setOnCheckedChangeListener((userTypes, radioId) -> {
            final int RADIO_CUSTOMER = R.id.radioCustomer;
            final int RADIO_ADMIN    = R.id.radioAdmin;

            if(AuthenticationManager.GetAuthenticatedUser().getID() == u.getID()) {
                // O utilizador selecionado é o utilizador que fez login
                MessageBox msg = new MessageBox(this);
                msg.show("Erro", "Não é possivel mudar o tipo de utilizador da própria conta!", R.drawable.error_flat);
                return;
            }

            switch(radioId) {
                case RADIO_CUSTOMER: userDB.setUserType(u, UserType.Normal); break;
                case RADIO_ADMIN:    userDB.setUserType(u, UserType.Admin); break;
            }
        });
    }

    public void BlockUser_OnClick(View v) {
        if(!userDB.isUserBlocked(u)) {
            // Estado do User: Normal
            if(u.getID() == AuthenticationManager.GetAuthenticatedUser().getID()) {
                MessageBox msg = new MessageBox(this);
                msg.show("Erro", "Não pode bloquear a própria conta!", R.drawable.error_flat);
                return;
            }
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