package com.example.projetofinal_smartcoffee.Util;

import android.content.Context;

public class ContextManager {
    private static Context _ctx;

    public static void SetContext(Context ctx) {
        _ctx = ctx;
    }

    protected static Context GetContext() {
        return _ctx;
    }
}