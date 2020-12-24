package com.example.projetofinal_smartcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.projetofinal_smartcoffee.Database.ClienteDB;
import com.example.projetofinal_smartcoffee.Util.ListViewUtil;
import com.example.projetofinal_smartcoffee.Util.MessageBox;

import java.util.ArrayList;

public class ListClientesActivity extends AppCompatActivity {

    ListView list;
    ArrayAdapter<Cliente> adapter;
    ArrayList<Cliente> clientes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_clientes);

        list = findViewById(R.id.list);
        ClienteDB.Create();

        MessageBox msg = new MessageBox(this);
        for(Cliente c : ClienteDB.GetAll()) {
            msg.show("Cliente Info", c.getInfo(), 0);
        }

//        adapter = new ArrayAdapter<>(
//                getApplicationContext(),
//                android.R.layout.simple_list_item_1,
//                ClienteDB.GetAll()
//        );

        ListViewUtil.AddItems(list, ClienteDB.GetAll());
        list.setOnItemClickListener((adapterView, view, i, l) -> {
            Cliente c = (Cliente)list.getItemAtPosition(i);
            Intent it = new Intent(getApplicationContext(), ClienteDetails.class);
            it.putExtra("cliente", c);
            startActivity(it);
        });

        //list.setAdapter(adapter);
    }
}