package com.karumi.dexter.listener.multi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.design.widget.Snackbar.Callback;
import android.view.View;
import android.view.View.OnClickListener;
import com.karumi.dexter.MultiplePermissionsReport;

public class SnackbarOnAnyDeniedMultiplePermissionsListener extends BaseMultiplePermissionsListener {
    private final String buttonText;
    private final int duration;
    private final OnClickListener onButtonClickListener;
    private final Callback snackbarCallback;
    private final String text;
    private final View view;

    public static class Builder {
        private String buttonText;
        private int duration = 0;
        private OnClickListener onClickListener;
        private Callback snackbarCallback;
        private final String text;
        /* access modifiers changed from: private */
        public final View view;

        private Builder(View view2, String str) {
            this.view = view2;
            this.text = str;
        }

        public static Builder with(View view2, @StringRes int i) {
            return with(view2, view2.getContext().getString(i));
        }

        public static Builder with(View view2, String str) {
            return new Builder(view2, str);
        }

        public SnackbarOnAnyDeniedMultiplePermissionsListener build() {
            SnackbarOnAnyDeniedMultiplePermissionsListener snackbarOnAnyDeniedMultiplePermissionsListener = new SnackbarOnAnyDeniedMultiplePermissionsListener(this.view, this.text, this.buttonText, this.onClickListener, this.snackbarCallback, this.duration);
            return snackbarOnAnyDeniedMultiplePermissionsListener;
        }

        public Builder withButton(@StringRes int i, OnClickListener onClickListener2) {
            return withButton(this.view.getContext().getString(i), onClickListener2);
        }

        public Builder withButton(String str, OnClickListener onClickListener2) {
            this.buttonText = str;
            this.onClickListener = onClickListener2;
            return this;
        }

        public Builder withCallback(Callback callback) {
            this.snackbarCallback = callback;
            return this;
        }

        public Builder withDuration(int i) {
            this.duration = i;
            return this;
        }

        public Builder withOpenSettingsButton(@StringRes int i) {
            return withOpenSettingsButton(this.view.getContext().getString(i));
        }

        public Builder withOpenSettingsButton(String str) {
            this.buttonText = str;
            this.onClickListener = new OnClickListener() {
                public void onClick(View view) {
                    Context context = Builder.this.view.getContext();
                    StringBuilder sb = new StringBuilder();
                    sb.append("package:");
                    sb.append(context.getPackageName());
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse(sb.toString()));
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setFlags(268435456);
                    context.startActivity(intent);
                }
            };
            return this;
        }
    }

    private SnackbarOnAnyDeniedMultiplePermissionsListener(View view2, String str, String str2, OnClickListener onClickListener, Callback callback, int i) {
        this.view = view2;
        this.text = str;
        this.buttonText = str2;
        this.onButtonClickListener = onClickListener;
        this.snackbarCallback = callback;
        this.duration = i;
    }

    private void showSnackbar() {
        Snackbar make = Snackbar.make(this.view, (CharSequence) this.text, this.duration);
        String str = this.buttonText;
        if (str != null) {
            OnClickListener onClickListener = this.onButtonClickListener;
            if (onClickListener != null) {
                make.setAction((CharSequence) str, onClickListener);
            }
        }
        Callback callback = this.snackbarCallback;
        if (callback != null) {
            make.setCallback(callback);
        }
        make.show();
    }

    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
        super.onPermissionsChecked(multiplePermissionsReport);
        if (!multiplePermissionsReport.areAllPermissionsGranted()) {
            showSnackbar();
        }
    }
}
