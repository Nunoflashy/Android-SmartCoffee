package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.projetofinal_smartcoffee.Database.ClienteDB;
import com.example.projetofinal_smartcoffee.Database.UserDatabase;
import com.example.projetofinal_smartcoffee.Util.ListViewUtil;
import com.example.projetofinal_smartcoffee.Util.MessageBox;

import java.util.ArrayList;

public class ListClientesActivity extends AppCompatActivity {

    ListView list;
    //ArrayAdapter<Cliente> adapter;
    ArrayList<Cliente> clientes = new ArrayList<>();

    ArrayAdapter<User> adapter;
    ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_clientes);

        UserDatabase users = new UserDatabase(this, "db_SmartCoffee");
        users.open();

        list = findViewById(R.id.list);

//        MessageBox msg = new MessageBox(this);
//        for(User u : users.getAll()) {
//            msg.show("Cliente Info", u.getInfo(), 0);
//        }

//        adapter = new ArrayAdapter<>(
//                getApplicationContext(),
//                android.R.layout.simple_list_item_1,
//                users.getAll()
//        );

        ListViewUtil.AddItems(list, users.getAll());
        list.setOnItemClickListener((adapterView, view, i, l) -> {
            User u = (User)list.getItemAtPosition(i);
            Intent it = new Intent(getApplicationContext(), ClienteDetails.class);
            it.putExtra("user", u);
            startActivity(it);
        });

        //list.setAdapter(adapter);
    }
}