package com.example.projetofinal_smartcoffee.Util;

import android.content.Context;
import android.graphics.Point;
import android.view.Gravity;

public class Toast {
    public static void Show(Context context, String msg, Point location) {
        final android.widget.Toast toast = android.widget.Toast.makeText(context, msg, android.widget.Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, location.x, location.y);
        toast.show();
    }

    public static void Show(Context context, String msg) {
        Show(context, msg, new Point(0, 0));
    }
}
