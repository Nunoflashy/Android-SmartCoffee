package com.example.projetofinal_smartcoffee.Util;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ListViewUtil extends ContextManager {

    private ListView list;


    public static <T> ListView AddItem(Context ctx, T item) {
        ListView lv = null;
        ArrayAdapter<T> adapter = new ArrayAdapter<>(
                ctx,
                android.R.layout.simple_list_item_1
        );
        adapter.add(item);
        lv.setAdapter(adapter);
        return lv;
    }

    public static <T> void AddItems(ListView list, List<T> items) {
        ArrayAdapter<T> adapter = new ArrayAdapter<>(
                GetContext(),
                android.R.layout.simple_list_item_1,
                items
        );

        if(list.getAdapter() == null) {
            list.setAdapter(adapter);
        }
    }
}
