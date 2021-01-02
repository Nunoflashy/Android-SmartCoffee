package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.projetofinal_smartcoffee.Database.ClienteDB;
import com.example.projetofinal_smartcoffee.Database.DatabaseManager;
import com.example.projetofinal_smartcoffee.Database.UserDatabase;
import com.example.projetofinal_smartcoffee.Util.ListViewUtil;
import com.example.projetofinal_smartcoffee.Util.MessageBox;

import java.util.ArrayList;

public class ListClientesActivity extends AppCompatActivity {

    ListView list;
    ArrayAdapter<User> adapter;
    ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_clientes);

        UserDatabase userDB = DatabaseManager.GetDB("userDB");
        //UserDatabase userDB = new UserDatabase(this, "db_SmartCoffee");
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



        //ListViewUtil.AddItems(list, userDB.getAll());
        list.setOnItemClickListener((adapterView, view, i, l) -> {
            User u = (User)list.getItemAtPosition(i);
            Intent it = new Intent(getApplicationContext(), ClienteDetails.class);
            //it.putExtra("userDB", getIntent().getExtras().getSerializable("userDB"));
            it.putExtra("user", u);
            startActivity(it);
        });

        list.setAdapter(adapter);
    }
}