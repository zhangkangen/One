package com.example.one.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.one.R;

/**
 * Created by Administrator on 2015/1/9.
 */
public class CustomProgressDialog extends ProgressDialog {
    public CustomProgressDialog(Context context) {
        super(context);
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog, null);
        setContentView(view);
    }
}
