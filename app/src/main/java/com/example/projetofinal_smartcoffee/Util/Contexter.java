package com.example.projetofinal_smartcoffee.Util;

import android.content.Context;

public class Contexter {
    private static Context Ctx;
    public static void SetContext(Context ctx) {
        Ctx = ctx;
    }
    protected static Context GetContext() {
        return Ctx;
    }

    private Context ctx;
    public void setContext(Context ctx) {
        this.ctx = ctx;
    }
    protected Context getContext() {
        return ctx;
    }
}