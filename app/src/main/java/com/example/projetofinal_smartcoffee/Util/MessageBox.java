package com.example.projetofinal_smartcoffee.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class MessageBox {
    private static Context context;

    public MessageBox(Context ctx) {
        context = ctx;
    }

    public static void SetContext(Context ctx) {
        context = ctx;
    }

    public static void Show(String title, String msg, int icon) {
        if(context != null) {
            AlertDialog.Builder message = new AlertDialog.Builder(context);
            message.setIcon(icon);
            message.setTitle(title);
            message.setMessage(msg);
            message.setPositiveButton("OK", null);
            message.show();
        }
        // Show(title, msg, icon, "OK", null, null, null);
    }

    public static void Show(String title, String msg, int icon, DialogInterface.OnClickListener onClickCallback) {
        Show(title, msg, icon, "OK", null, onClickCallback, null);
    }

    public static void Show(String title, String msg, int icon, String positiveBtn, DialogInterface.OnClickListener onClickCallback) {
        Show(title, msg, icon, positiveBtn, null, onClickCallback, null);
    }

    public static void Show(String title, String msg, int icon, String positiveBtn, String negativeBtn, DialogInterface.OnClickListener onClickPositiveBtn, DialogInterface.OnClickListener onClickNegativeBtn) {
        if(context != null) {
            AlertDialog.Builder message = new AlertDialog.Builder(context);
            message.setIcon(icon);
            message.setTitle(title);
            message.setMessage(msg);

            if (onClickPositiveBtn != null && positiveBtn != null)
                message.setPositiveButton(positiveBtn, onClickPositiveBtn);

            if (onClickNegativeBtn != null && negativeBtn != null)
                message.setNegativeButton(negativeBtn, onClickNegativeBtn);

            message.show();
        }
    }

    public void show(String title, final String msg, int icon) {
        AlertDialog.Builder message = new AlertDialog.Builder(context);
        message.setIcon(icon);
        message.setTitle(title);
        message.setMessage(msg);
        message.setPositiveButton("OK", null);
        message.show();
    }
}
