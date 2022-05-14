package com.finedustApp.finedustalarm;

import android.app.Dialog;
import android.content.Context;

public class ProgressDialog extends Dialog {
    public ProgressDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_progress);
        setCancelable(false);
    }
}
