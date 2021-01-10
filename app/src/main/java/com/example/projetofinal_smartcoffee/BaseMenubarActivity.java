package com.example.projetofinal_smartcoffee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetofinal_smartcoffee.Util.MessageBox;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BaseMenubarActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView navView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());

        navView = findViewById(R.id.menubar);
        navView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateState();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }


    /**
     * Começa a NavView(menu) e atribui-lhe a funcionalidade
     */
    protected void initNavView() {
        navView = findViewById(R.id.menubar);

        navView.setOnNavigationItemSelectedListener(item -> {
            // Se o item do menu for o mesmo onde nos encontramos, não efetuar nenhuma acção
            if(item.getItemId() == getBottomNavigationMenuItemID()) return true;
            switch(item.getItemId()) {
                case R.id.nav_overview: startActivity(new Intent(getActivity(), AdminDashboardActivity.class)); break;
                case R.id.nav_listUsers: startActivity(new Intent(getActivity(), ListClientesActivity.class)); break;
                case R.id.nav_settings: startActivity(new Intent(getActivity(), SettingsActivity.class)); break;
                case R.id.nav_logout: startActivity(new Intent(getActivity(), LoginActivity.class)); break;
            }
            finish();
            return true;
        });
    }

    /**
     * Faz o get do id do item do menu e em seguida seleciona-o
     */
    private void updateState() {
        int actionID = getBottomNavigationMenuItemID();
        selectItem(actionID);
    }

    /**
     * Método que seleciona o item do menu em que nos encontramos
     * @param id
     */
    private void selectItem(int id) {
        MenuItem item = navView.getMenu().findItem(id);
        item.setChecked(true);
    }

    /**
     *
     * @return Retorna o ID do layout da activity
     */
    protected abstract int getLayoutID();

    /**
     *
     * @return Retorna o id do item do menu que está selecionado
     */
    protected abstract int getBottomNavigationMenuItemID();

    /**
     * Método com a função de retornar a activity derivada
     * para possibilitar detetar em que item do menu nos encontramos
     *
     * @return Retorna a activity
     */
    protected abstract Activity getActivity();
}
