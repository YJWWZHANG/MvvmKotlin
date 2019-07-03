package com.zqb.mvvmkotlin.widgets;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.zqb.mvvmkotlin.R;

public class Loading extends Dialog {
    public Loading(@NonNull Context context) {
        this(context, R.style.BaseDialog);
    }

    public Loading(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected Loading(@NonNull Context context, boolean cancelable,  @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_load);
        setCanceledOnTouchOutside(false);
    }
}
