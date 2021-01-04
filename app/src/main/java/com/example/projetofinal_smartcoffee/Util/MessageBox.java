package com.example.projetofinal_smartcoffee.Util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.function.Function;

public class MessageBox extends Contexter {

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

    // ----------------------------------------------------------
    private DialogInterface.OnClickListener onClickPositiveBtn = null;
    private DialogInterface.OnClickListener onClickNegativeBtn = null;
    private String positiveBtnText = "OK";
    private String negativeBtnText = null;
    private String title, msg;
    private int icon;

    public MessageBox(Context ctx) {
        setContext(ctx);
    }

    public void show(String title, final String msg, int icon) {
        setInfo(title, msg, icon);
        show();
    }

    public void show() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setIcon(icon);
        dialog.setTitle(title);
        dialog.setMessage(msg);

        if(positiveBtnText != null) {
            dialog.setPositiveButton(positiveBtnText, onClickPositiveBtn);
        }

        if(negativeBtnText != null) {
            dialog.setNegativeButton(negativeBtnText, onClickPositiveBtn);
        }
        dialog.show();
    }

    public void show(String title, String message, int icon, DialogInterface.OnClickListener onClickPositiveBtn) {
        setInfo(title, message, icon);
        setPositiveButton(onClickPositiveBtn);
        show();
    }

    public void show(String title, String message, int icon, String positiveBtnText, DialogInterface.OnClickListener onClickPositiveBtn) {
        this.positiveBtnText = positiveBtnText;
        show(title, message, icon, onClickPositiveBtn);
    }

    public void show(String title, String message, int icon,
                     String positiveBtnText, DialogInterface.OnClickListener onClickPositiveBtn,
                     String negativeBtnText, DialogInterface.OnClickListener onClickNegativeBtn)
    {
        setNegativeButton(negativeBtnText, onClickNegativeBtn);
        show(title, message, icon, positiveBtnText, onClickPositiveBtn);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public void setMessage(String title, String message) {
        this.title = title;
        this.msg = message;
    }

    public void setInfo(String title, String message, int icon) {
        setMessage(title, message);
        setIcon(icon);
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setPositiveButton(DialogInterface.OnClickListener onClick) {
        onClickPositiveBtn = onClick;
    }

    public void setPositiveButton(String text, DialogInterface.OnClickListener onClick) {
        if(text != null) {
            positiveBtnText = text;
        }
        setPositiveButton(onClick);
    }

    public void setNegativeButton(DialogInterface.OnClickListener onClick) {
        onClickNegativeBtn = onClick;
    }

    public void setNegativeButton(String text, DialogInterface.OnClickListener onClick) {
        if(text != null) {
            negativeBtnText = text;
        }
        setNegativeButton(onClick);
    }
}
