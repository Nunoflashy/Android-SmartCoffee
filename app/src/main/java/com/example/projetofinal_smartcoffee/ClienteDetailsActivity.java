package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.projetofinal_smartcoffee.Database.DatabaseManager;
import com.example.projetofinal_smartcoffee.Database.UserDatabase;
import com.example.projetofinal_smartcoffee.Database.UserType;
import com.example.projetofinal_smartcoffee.Exception.InvalidUserException;
import com.example.projetofinal_smartcoffee.Exception.UserNotFoundException;
import com.example.projetofinal_smartcoffee.Util.AuthenticationManager;
import com.example.projetofinal_smartcoffee.Util.Languages;
import com.example.projetofinal_smartcoffee.Util.MessageBox;
import com.example.projetofinal_smartcoffee.Util.RegistrationManager;

import java.util.concurrent.ExecutionException;

public class ClienteDetailsActivity extends AppCompatActivity {

    TextView tvID, tvUsername, tvMail, tvEstado, tvBlock, tvRemove;
    RadioGroup radioUserTypes;
    RadioButton radioCustomer, radioAdmin;

    EditText etUsername;
    EditText etMail;
    EditText etChangePass, etConfirmChangePass;
    Button btnOkChangePass;

    ImageView editUserButton, saveUserButton;
    ImageView editMailButton, saveMailButton;
    ImageView imgBlock, imgRemove;

    LinearLayout linearLayoutID = null;
    LinearLayout linearLayoutUsername = null;
    LinearLayout linearLayoutMail = null;
    LinearLayout linearLayoutState = null;
    LinearLayout linearLayoutUserType = null;

    UserDatabase userDB = DatabaseManager.GetDB("userDB");
    User u;

    private void bindControls() {
        tvID                = findViewById(R.id.tvID);
        tvUsername          = findViewById(R.id.tvUsername);
        tvMail              = findViewById(R.id.tvMail);
        tvEstado            = findViewById(R.id.tvEstado);
        tvBlock             = findViewById(R.id.tvBlock);
        tvRemove            = findViewById(R.id.tvRemove);
        radioUserTypes      = findViewById(R.id.radioUserTypes);
        radioCustomer       = findViewById(R.id.radioCustomer);
        radioAdmin          = findViewById(R.id.radioAdmin);
        etUsername          = findViewById(R.id.etUsername);
        etMail              = findViewById(R.id.etMail);
        etChangePass        = findViewById(R.id.etChangePass);
        etConfirmChangePass = findViewById(R.id.etConfirmChangePass);
        btnOkChangePass     = findViewById(R.id.btnOkChangePass);
        editUserButton      = findViewById(R.id.editUserButton);
        saveUserButton      = findViewById(R.id.saveUserButton);
        editMailButton      = findViewById(R.id.editMailButton);
        saveMailButton      = findViewById(R.id.saveMailButton);
        linearLayoutID      = findViewById(R.id.linearLayoutID);
        linearLayoutUsername= findViewById(R.id.linearLayoutUsername);
        linearLayoutMail    = findViewById(R.id.linearLayoutMail);
        linearLayoutState   = findViewById(R.id.linearLayoutState);
        linearLayoutUserType= findViewById(R.id.linearLayoutUserType);
        imgBlock            = findViewById(R.id.imgBlock);
        imgRemove           = findViewById(R.id.imgRemove);
    }

    private void updateUserType() {
        if(userDB.isUserAdmin(u)) {
            radioAdmin.setChecked(true);
        } else {
            radioCustomer.setChecked(true);
        }
    }

    private void initControls() {
        // User Events
        editUserButton.setOnClickListener((v) -> {
            etUsername.setEnabled(true);
            etUsername.requestFocus();

            // Posicionar a caret no fim do texto
            int len = etUsername.getText().length();
            etUsername.setSelection(len);

            // Mostrar o teclado
            InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etUsername, InputMethodManager.SHOW_IMPLICIT);

            editUserButton.setVisibility(View.GONE);
            saveUserButton.setVisibility(View.VISIBLE);
        });
        saveUserButton.setOnClickListener((v) -> {
            editUserButton.setVisibility(View.VISIBLE);
            saveUserButton.setVisibility(View.GONE);

            etUsername.setEnabled(false);

            // Esconder o teclado
            InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etUsername.getWindowToken(), 0);

            // Update ao user se o username for alterado no edittext
            if(!getNewUsername().equals(u.getName())) {
                if(userDB.userExists(getNewUsername())) {
                    MessageBox msg = new MessageBox(this);
                    msg.show(getString(R.string.msgError), Languages.UserAlreadyRegisteredMsg(getNewUsername()), R.drawable.error_flat);
                    etUsername.setText(u.getName());
                    return;
                }
                userDB.updateUser(u, getNewUsername());
            }
        });

        // Mail Events
        editMailButton.setOnClickListener((v) -> {
            etMail.setEnabled(true);
            etMail.requestFocus();

            // Posicionar a caret no fim do texto
            int len = etMail.getText().length();
            etMail.setSelection(len);

            // Mostrar o teclado
            InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etMail, InputMethodManager.SHOW_IMPLICIT);

            editMailButton.setVisibility(View.GONE);
            saveMailButton.setVisibility(View.VISIBLE);
        });
        saveMailButton.setOnClickListener((v) -> {
            editMailButton.setVisibility(View.VISIBLE);
            saveMailButton.setVisibility(View.GONE);

            etMail.setEnabled(false);

            // Esconder o teclado
            InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etMail.getWindowToken(), 0);

            // Update ao user se o mail for alterado no edittext
            if(!getNewMail().equals(u.getMail())) {
                userDB.updateMail(u, getNewMail());
            }
        });

        // User Type Events (RadioGroup)
        radioUserTypes.setOnCheckedChangeListener((userTypes, radioId) -> {
            final int RADIO_CUSTOMER = R.id.radioCustomer;
            final int RADIO_ADMIN    = R.id.radioAdmin;

            if(AuthenticationManager.GetAuthenticatedUser().getID() == u.getID()) {
                // O utilizador selecionado é o utilizador que fez login
                MessageBox msg = new MessageBox(this);
                updateUserType();
                msg.show(getString(R.string.msgError), getString(R.string.unableToChangeOwnAccountType), R.drawable.error_flat);
                return;
            }
            try {
                switch(radioId) {
                    case RADIO_CUSTOMER: userDB.setUserType(u, UserType.Normal); break;
                    case RADIO_ADMIN:    userDB.setUserType(u, UserType.Admin); break;
                }
            } catch(UserNotFoundException ex) {
                Log.d("UserDB", ex.getMessage());
            }
        });

        // Password Events
        btnOkChangePass.setOnClickListener((v) -> {
            String pass   = etChangePass.getText().toString();
            String repass = etConfirmChangePass.getText().toString();

            MessageBox msg = new MessageBox(this);

            if(pass.equals(repass) && !pass.isEmpty()) {
                msg.show(getString(R.string.password), getString(R.string.passwordChanged), R.drawable.information_icon_svg);
                userDB.updatePassword(u, pass);
            } else {
                msg.show(getString(R.string.msgError), getString(R.string.passwordNotMatchOrEmpty), R.drawable.error_flat);
            }
        });

        // Block User Events
        View.OnClickListener blockListener = (v) -> {
            if(!userDB.isUserBlocked(u)) {
                // Estado do User: Normal
                if(u.getID() == AuthenticationManager.GetAuthenticatedUser().getID()) {
                    MessageBox msg = new MessageBox(this);
                    msg.show(getString(R.string.msgError), getString(R.string.unableToBlockOwnAccount), R.drawable.error_flat);
                    return;
                }
                MessageBox.Show(getString(R.string.block), Languages.BlockConfirmMsg(u.getName()), R.drawable.blockiconred,
                            getString(R.string.block), (dialogInterface, i) -> {
                            userDB.blockUser(u);
                            MessageBox.Show(getString(R.string.block), Languages.BlockedMsg(u.getName()), R.drawable.blockiconred);
                            update();
                        },
                            getString(R.string.cancel), (dialogInterface, i) -> {
                            return;
                        });
            } else {
                // Estado do User: Bloqueado
                MessageBox.Show(getString(R.string.unblock), Languages.UnblockConfirmMsg(u.getName()), R.drawable.blockicon,
                            getString(R.string.unblock), (dialogInterface, i) -> {
                            userDB.unblockUser(u);
                            MessageBox.Show(getString(R.string.unblock), Languages.UnblockedMsg(u.getName()), R.drawable.blockicon);
                            update();
                        },
                            getString(R.string.cancel), (dialogInterface, i) -> {
                            return;
                        });
            }
        };
        imgBlock.setOnClickListener(blockListener);
        tvBlock.setOnClickListener(blockListener);

        // Remove User Events
        View.OnClickListener removeListener = (v) -> {
            if(u.getID() == AuthenticationManager.GetAuthenticatedUser().getID()) {
                MessageBox msg = new MessageBox(this);
                msg.show(getString(R.string.msgError), getString(R.string.unableToDeleteOwnAccount), R.drawable.error_flat);
                return;
            }
            MessageBox.Show(getString(R.string.remove), Languages.RemoveConfirmMsg(u.getName()), R.drawable.removeicon,
                    getString(R.string.remove), (dialogInterface, i) -> {
                        userDB.removeUser(u);
                        MessageBox.Show(getString(R.string.remove), Languages.RemovedMsg(u.getName()), R.drawable.removeicon);
                    },
                        getString(R.string.cancel), (dialogInterface, i) -> {
                        return;
                    });
        };
        imgRemove.setOnClickListener(removeListener);
        tvRemove.setOnClickListener(removeListener);
    }

    private void init() {
        if(AuthenticationManager.GetAuthenticatedUser().getID() == u.getID()) {
            editUserButton.setVisibility(View.GONE);
            saveUserButton.setVisibility(View.GONE);
        }

        updateUserType();
        initControls();

    }

    private String getNewUsername() {
        return etUsername.getText().toString();
    }

    private String getNewMail() {
        return etMail.getText().toString();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_details);

        // Configurar Linguagem
        Languages.SetLanguage(getString(R.string.language));

        bindControls();

        Intent it = getIntent();
        u = (User)it.getExtras().getSerializable("user");

        MessageBox.SetContext(this);
        init();
        update();
    }

    private void update() {
        tvID.setText(String.format("ID: %s", u.getID()));
        etUsername.setText(u.getName());
        etMail.setText(u.getMail());
        tvEstado.setText(String.format(getString(R.string.status) + " %s", userDB.isUserBlocked(u) ? getString(R.string.blocked) : "Normal"));
        tvEstado.setTextColor(userDB.isUserBlocked(u) ? Color.RED : Color.argb(255, 46, 46, 46));

        String block    = getString(R.string.block);
        String unblock  = getString(R.string.unblock);
        tvBlock.setText(userDB.isUserBlocked(u) ? unblock : block);
    }

    public void StartActivityListClientes(View v) {
        Intent it = new Intent(this, ListClientesActivity.class);
        startActivity(it);
    }
}