package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.projetofinal_smartcoffee.Database.DatabaseManager;
import com.example.projetofinal_smartcoffee.Database.UserDatabase;

public class ListClientesActivity extends AppCompatActivity {

    ListView list;
    ArrayAdapter<User> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_clientes);

        UserDatabase userDB = DatabaseManager.GetDB("userDB");
        userDB.open();

        list = findViewById(R.id.list);

//        MessageBox msg = new MessageBox(this);
//        for(User u : users.getAll()) {
//            msg.show("Cliente Info", u.getInfo(), 0);
//        }

        adapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                userDB.getAll()
        );

        list.setOnItemClickListener((adapterView, view, i, l) -> {
            User u = (User)list.getItemAtPosition(i);
            Intent it = new Intent(this, ClienteDetailsActivity.class);
            it.putExtra("user", u);
            startActivity(it);
        });

        list.setAdapter(adapter);
    }
}