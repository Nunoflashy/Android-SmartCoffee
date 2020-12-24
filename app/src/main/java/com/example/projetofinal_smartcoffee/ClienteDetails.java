package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ClienteDetails extends AppCompatActivity {

    TextView tvID, tvUsername, tvMail, tvEstado;

    private void BindControls() {
        tvID       = findViewById(R.id.tvID);
        tvUsername = findViewById(R.id.tvUsername);
        tvMail     = findViewById(R.id.tvMail);
        tvEstado   = findViewById(R.id.tvEstado);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_details);

        BindControls();

        Intent it = getIntent();
        Cliente c = (Cliente) it.getExtras().getSerializable("cliente");

        tvID.setText(String.format("ID: %s", c.getID()));
        tvUsername.setText(String.format("Username: %s", c.getNome()));
        tvMail.setText(String.format("E-Mail: %s", c.getMail()));
    }

    public void StartActivityListClientes(View v) {
        Intent it = new Intent(this, ListClientesActivity.class);
        startActivity(it);
    }
}