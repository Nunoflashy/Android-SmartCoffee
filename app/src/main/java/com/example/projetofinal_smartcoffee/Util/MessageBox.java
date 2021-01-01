package com.example.projetofinal_smartcoffee.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class MessageBox extends Contexter {

    public MessageBox(Context ctx) {
        setContext(ctx);
    }

    public static void Show(String title, String msg, int icon) {
        if(GetContext() != null) {
            AlertDialog.Builder message = new AlertDialog.Builder(GetContext());
            message.setIcon(icon);
            message.setTitle(title);
            message.setMessage(msg);
            message.setPositiveButton("OK", null);
            message.show();
        }
    }

    public static void Show(String title, String msg, int icon, DialogInterface.OnClickListener onClickCallback) {
        Show(title, msg, icon, "OK", onClickCallback, null, null);
    }

    public static void Show(String title, String msg, int icon, String positiveBtn, DialogInterface.OnClickListener onClickCallback) {
        Show(title, msg, icon, positiveBtn, null, null, onClickCallback);
    }

    public static void Show(String title, String msg, int icon, String positiveBtn, DialogInterface.OnClickListener onClickPositiveBtn, String negativeBtn, DialogInterface.OnClickListener onClickNegativeBtn) {
        if(GetContext() != null) {
            AlertDialog.Builder message = new AlertDialog.Builder(GetContext());
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
        AlertDialog.Builder message = new AlertDialog.Builder(GetContext());
        message.setIcon(icon);
        message.setTitle(title);
        message.setMessage(msg);
        message.setPositiveButton("OK", null);
        message.show();
    }
}
