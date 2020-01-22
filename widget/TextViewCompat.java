package android.support.p000v4.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.drawable.Drawable;
import android.icu.text.DecimalFormatSymbols;
import android.os.Build.VERSION;
import android.support.annotation.C0017Px;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StyleRes;
import android.support.p000v4.text.PrecomputedTextCompat;
import android.support.p000v4.text.PrecomputedTextCompat.Params;
import android.support.p000v4.text.PrecomputedTextCompat.Params.Builder;
import android.support.p000v4.util.Preconditions;
import android.text.Editable;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/* renamed from: android.support.v4.widget.TextViewCompat */
public final class TextViewCompat {
    public static final int AUTO_SIZE_TEXT_TYPE_NONE = 0;
    public static final int AUTO_SIZE_TEXT_TYPE_UNIFORM = 1;
    private static final int LINES = 1;
    private static final String LOG_TAG = "TextViewCompat";
    private static Field sMaxModeField;
    private static boolean sMaxModeFieldFetched;
    private static Field sMaximumField;
    private static boolean sMaximumFieldFetched;
    private static Field sMinModeField;
    private static boolean sMinModeFieldFetched;
    private static Field sMinimumField;
    private static boolean sMinimumFieldFetched;

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.widget.TextViewCompat$AutoSizeTextType */
    public @interface AutoSizeTextType {
    }

    @RequiresApi(26)
    /* renamed from: android.support.v4.widget.TextViewCompat$OreoCallback */
    private static class OreoCallback implements Callback {
        private static final int MENU_ITEM_ORDER_PROCESS_TEXT_INTENT_ACTIONS_START = 100;
        private final Callback mCallback;
        private boolean mCanUseMenuBuilderReferences;
        private boolean mInitializedMenuBuilderReferences = false;
        private Class mMenuBuilderClass;
        private Method mMenuBuilderRemoveItemAtMethod;
        private final TextView mTextView;

        OreoCallback(Callback callback, TextView textView) {
            this.mCallback = callback;
            this.mTextView = textView;
        }

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return this.mCallback.onCreateActionMode(mode, menu);
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            recomputeProcessTextMenuItems(menu);
            return this.mCallback.onPrepareActionMode(mode, menu);
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return this.mCallback.onActionItemClicked(mode, item);
        }

        public void onDestroyActionMode(ActionMode mode) {
            this.mCallback.onDestroyActionMode(mode);
        }

        /* JADX WARNING: Removed duplicated region for block: B:17:0x0060 A[Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00b9 }] */
        /* JADX WARNING: Removed duplicated region for block: B:26:0x0098 A[LOOP:1: B:24:0x0092->B:26:0x0098, LOOP_END] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void recomputeProcessTextMenuItems(android.view.Menu r10) {
            /*
                r9 = this;
                android.widget.TextView r0 = r9.mTextView
                android.content.Context r0 = r0.getContext()
                android.content.pm.PackageManager r1 = r0.getPackageManager()
                boolean r2 = r9.mInitializedMenuBuilderReferences
                r3 = 0
                r4 = 1
                if (r2 != 0) goto L_0x0036
                r9.mInitializedMenuBuilderReferences = r4
                java.lang.String r2 = "com.android.internal.view.menu.MenuBuilder"
                java.lang.Class r2 = java.lang.Class.forName(r2)     // Catch:{ ClassNotFoundException | NoSuchMethodException -> 0x002d }
                r9.mMenuBuilderClass = r2     // Catch:{ ClassNotFoundException | NoSuchMethodException -> 0x002d }
                java.lang.Class r2 = r9.mMenuBuilderClass     // Catch:{ ClassNotFoundException | NoSuchMethodException -> 0x002d }
                java.lang.String r5 = "removeItemAt"
                java.lang.Class[] r6 = new java.lang.Class[r4]     // Catch:{ ClassNotFoundException | NoSuchMethodException -> 0x002d }
                java.lang.Class r7 = java.lang.Integer.TYPE     // Catch:{ ClassNotFoundException | NoSuchMethodException -> 0x002d }
                r6[r3] = r7     // Catch:{ ClassNotFoundException | NoSuchMethodException -> 0x002d }
                java.lang.reflect.Method r2 = r2.getDeclaredMethod(r5, r6)     // Catch:{ ClassNotFoundException | NoSuchMethodException -> 0x002d }
                r9.mMenuBuilderRemoveItemAtMethod = r2     // Catch:{ ClassNotFoundException | NoSuchMethodException -> 0x002d }
                r9.mCanUseMenuBuilderReferences = r4     // Catch:{ ClassNotFoundException | NoSuchMethodException -> 0x002d }
                goto L_0x0037
            L_0x002d:
                r2 = move-exception
                r5 = 0
                r9.mMenuBuilderClass = r5
                r9.mMenuBuilderRemoveItemAtMethod = r5
                r9.mCanUseMenuBuilderReferences = r3
                goto L_0x0037
            L_0x0036:
            L_0x0037:
                boolean r2 = r9.mCanUseMenuBuilderReferences     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00b9 }
                if (r2 == 0) goto L_0x0047
                java.lang.Class r2 = r9.mMenuBuilderClass     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00b9 }
                boolean r2 = r2.isInstance(r10)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00b9 }
                if (r2 == 0) goto L_0x0046
                java.lang.reflect.Method r2 = r9.mMenuBuilderRemoveItemAtMethod     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00b9 }
                goto L_0x0058
            L_0x0046:
                goto L_0x0048
            L_0x0047:
            L_0x0048:
                java.lang.Class r2 = r10.getClass()     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00b9 }
                java.lang.String r5 = "removeItemAt"
                java.lang.Class[] r6 = new java.lang.Class[r4]     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00b9 }
                java.lang.Class r7 = java.lang.Integer.TYPE     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00b9 }
                r6[r3] = r7     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00b9 }
                java.lang.reflect.Method r2 = r2.getDeclaredMethod(r5, r6)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00b9 }
            L_0x0058:
                int r5 = r10.size()     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00b9 }
                int r5 = r5 - r4
            L_0x005e:
                if (r5 < 0) goto L_0x008b
                android.view.MenuItem r6 = r10.getItem(r5)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00b9 }
                android.content.Intent r7 = r6.getIntent()     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00b9 }
                if (r7 == 0) goto L_0x0087
                java.lang.String r7 = "android.intent.action.PROCESS_TEXT"
                android.content.Intent r8 = r6.getIntent()     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00b9 }
                java.lang.String r8 = r8.getAction()     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00b9 }
                boolean r7 = r7.equals(r8)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00b9 }
                if (r7 == 0) goto L_0x0086
                java.lang.Object[] r7 = new java.lang.Object[r4]     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00b9 }
                java.lang.Integer r8 = java.lang.Integer.valueOf(r5)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00b9 }
                r7[r3] = r8     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00b9 }
                r2.invoke(r10, r7)     // Catch:{ IllegalAccessException | NoSuchMethodException | InvocationTargetException -> 0x00b9 }
                goto L_0x0088
            L_0x0086:
                goto L_0x0088
            L_0x0087:
            L_0x0088:
                int r5 = r5 + -1
                goto L_0x005e
            L_0x008b:
                java.util.List r2 = r9.getSupportedActivities(r0, r1)
                r5 = 0
            L_0x0092:
                int r6 = r2.size()
                if (r5 >= r6) goto L_0x00b8
                java.lang.Object r6 = r2.get(r5)
                android.content.pm.ResolveInfo r6 = (android.content.pm.ResolveInfo) r6
                int r7 = r5 + 100
                java.lang.CharSequence r8 = r6.loadLabel(r1)
                android.view.MenuItem r7 = r10.add(r3, r3, r7, r8)
                android.widget.TextView r8 = r9.mTextView
                android.content.Intent r8 = r9.createProcessTextIntentForResolveInfo(r6, r8)
                android.view.MenuItem r7 = r7.setIntent(r8)
                r7.setShowAsAction(r4)
                int r5 = r5 + 1
                goto L_0x0092
            L_0x00b8:
                return
            L_0x00b9:
                r2 = move-exception
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.p000v4.widget.TextViewCompat.OreoCallback.recomputeProcessTextMenuItems(android.view.Menu):void");
        }

        private List<ResolveInfo> getSupportedActivities(Context context, PackageManager packageManager) {
            List<ResolveInfo> supportedActivities = new ArrayList<>();
            if (!(context instanceof Activity)) {
                return supportedActivities;
            }
            for (ResolveInfo info : packageManager.queryIntentActivities(createProcessTextIntent(), 0)) {
                if (isSupportedActivity(info, context)) {
                    supportedActivities.add(info);
                }
            }
            return supportedActivities;
        }

        private boolean isSupportedActivity(ResolveInfo info, Context context) {
            boolean z = true;
            if (context.getPackageName().equals(info.activityInfo.packageName)) {
                return true;
            }
            if (!info.activityInfo.exported) {
                return false;
            }
            if (!(info.activityInfo.permission == null || context.checkSelfPermission(info.activityInfo.permission) == 0)) {
                z = false;
            }
            return z;
        }

        private Intent createProcessTextIntentForResolveInfo(ResolveInfo info, TextView textView11) {
            return createProcessTextIntent().putExtra("android.intent.extra.PROCESS_TEXT_READONLY", !isEditable(textView11)).setClassName(info.activityInfo.packageName, info.activityInfo.name);
        }

        private boolean isEditable(TextView textView11) {
            return (textView11 instanceof Editable) && textView11.onCheckIsTextEditor() && textView11.isEnabled();
        }

        private Intent createProcessTextIntent() {
            return new Intent().setAction("android.intent.action.PROCESS_TEXT").setType("text/plain");
        }
    }

    private TextViewCompat() {
    }

    private static Field retrieveField(String fieldName) {
        Field field = null;
        try {
            field = TextView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            String str = LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Could not retrieve ");
            sb.append(fieldName);
            sb.append(" field.");
            Log.e(str, sb.toString());
            return field;
        }
    }

    private static int retrieveIntFromField(Field field, TextView textView) {
        try {
            return field.getInt(textView);
        } catch (IllegalAccessException e) {
            String str = LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Could not retrieve value of ");
            sb.append(field.getName());
            sb.append(" field.");
            Log.d(str, sb.toString());
            return -1;
        }
    }

    public static void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end, @Nullable Drawable bottom) {
        if (VERSION.SDK_INT >= 18) {
            textView.setCompoundDrawablesRelative(start, top, end, bottom);
        } else if (VERSION.SDK_INT >= 17) {
            boolean z = true;
            if (textView.getLayoutDirection() != 1) {
                z = false;
            }
            boolean rtl = z;
            textView.setCompoundDrawables(rtl ? end : start, top, rtl ? start : end, bottom);
        } else {
            textView.setCompoundDrawables(start, top, end, bottom);
        }
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end, @Nullable Drawable bottom) {
        if (VERSION.SDK_INT >= 18) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
        } else if (VERSION.SDK_INT >= 17) {
            boolean z = true;
            if (textView.getLayoutDirection() != 1) {
                z = false;
            }
            boolean rtl = z;
            textView.setCompoundDrawablesWithIntrinsicBounds(rtl ? end : start, top, rtl ? start : end, bottom);
        } else {
            textView.setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom);
        }
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @DrawableRes int start, @DrawableRes int top, @DrawableRes int end, @DrawableRes int bottom) {
        if (VERSION.SDK_INT >= 18) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
        } else if (VERSION.SDK_INT >= 17) {
            boolean z = true;
            if (textView.getLayoutDirection() != 1) {
                z = false;
            }
            boolean rtl = z;
            textView.setCompoundDrawablesWithIntrinsicBounds(rtl ? end : start, top, rtl ? start : end, bottom);
        } else {
            textView.setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom);
        }
    }

    public static int getMaxLines(@NonNull TextView textView) {
        if (VERSION.SDK_INT >= 16) {
            return textView.getMaxLines();
        }
        if (!sMaxModeFieldFetched) {
            sMaxModeField = retrieveField("mMaxMode");
            sMaxModeFieldFetched = true;
        }
        Field field = sMaxModeField;
        if (field != null && retrieveIntFromField(field, textView) == 1) {
            if (!sMaximumFieldFetched) {
                sMaximumField = retrieveField("mMaximum");
                sMaximumFieldFetched = true;
            }
            Field field2 = sMaximumField;
            if (field2 != null) {
                return retrieveIntFromField(field2, textView);
            }
        }
        return -1;
    }

    public static int getMinLines(@NonNull TextView textView) {
        if (VERSION.SDK_INT >= 16) {
            return textView.getMinLines();
        }
        if (!sMinModeFieldFetched) {
            sMinModeField = retrieveField("mMinMode");
            sMinModeFieldFetched = true;
        }
        Field field = sMinModeField;
        if (field != null && retrieveIntFromField(field, textView) == 1) {
            if (!sMinimumFieldFetched) {
                sMinimumField = retrieveField("mMinimum");
                sMinimumFieldFetched = true;
            }
            Field field2 = sMinimumField;
            if (field2 != null) {
                return retrieveIntFromField(field2, textView);
            }
        }
        return -1;
    }

    public static void setTextAppearance(@NonNull TextView textView, @StyleRes int resId) {
        if (VERSION.SDK_INT >= 23) {
            textView.setTextAppearance(resId);
        } else {
            textView.setTextAppearance(textView.getContext(), resId);
        }
    }

    @NonNull
    public static Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
        if (VERSION.SDK_INT >= 18) {
            return textView.getCompoundDrawablesRelative();
        }
        if (VERSION.SDK_INT < 17) {
            return textView.getCompoundDrawables();
        }
        boolean z = true;
        if (textView.getLayoutDirection() != 1) {
            z = false;
        }
        boolean rtl = z;
        Drawable[] compounds = textView.getCompoundDrawables();
        if (rtl) {
            Drawable start = compounds[2];
            Drawable end = compounds[0];
            compounds[0] = start;
            compounds[2] = end;
        }
        return compounds;
    }

    public static void setAutoSizeTextTypeWithDefaults(@NonNull TextView textView, int autoSizeTextType) {
        if (VERSION.SDK_INT >= 27) {
            textView.setAutoSizeTextTypeWithDefaults(autoSizeTextType);
        } else if (textView instanceof AutoSizeableTextView) {
            ((AutoSizeableTextView) textView).setAutoSizeTextTypeWithDefaults(autoSizeTextType);
        }
    }

    public static void setAutoSizeTextTypeUniformWithConfiguration(@NonNull TextView textView, int autoSizeMinTextSize, int autoSizeMaxTextSize, int autoSizeStepGranularity, int unit) throws IllegalArgumentException {
        if (VERSION.SDK_INT >= 27) {
            textView.setAutoSizeTextTypeUniformWithConfiguration(autoSizeMinTextSize, autoSizeMaxTextSize, autoSizeStepGranularity, unit);
        } else if (textView instanceof AutoSizeableTextView) {
            ((AutoSizeableTextView) textView).setAutoSizeTextTypeUniformWithConfiguration(autoSizeMinTextSize, autoSizeMaxTextSize, autoSizeStepGranularity, unit);
        }
    }

    public static void setAutoSizeTextTypeUniformWithPresetSizes(@NonNull TextView textView, @NonNull int[] presetSizes, int unit) throws IllegalArgumentException {
        if (VERSION.SDK_INT >= 27) {
            textView.setAutoSizeTextTypeUniformWithPresetSizes(presetSizes, unit);
        } else if (textView instanceof AutoSizeableTextView) {
            ((AutoSizeableTextView) textView).setAutoSizeTextTypeUniformWithPresetSizes(presetSizes, unit);
        }
    }

    public static int getAutoSizeTextType(@NonNull TextView textView) {
        if (VERSION.SDK_INT >= 27) {
            return textView.getAutoSizeTextType();
        }
        if (textView instanceof AutoSizeableTextView) {
            return ((AutoSizeableTextView) textView).getAutoSizeTextType();
        }
        return 0;
    }

    public static int getAutoSizeStepGranularity(@NonNull TextView textView) {
        if (VERSION.SDK_INT >= 27) {
            return textView.getAutoSizeStepGranularity();
        }
        if (textView instanceof AutoSizeableTextView) {
            return ((AutoSizeableTextView) textView).getAutoSizeStepGranularity();
        }
        return -1;
    }

    public static int getAutoSizeMinTextSize(@NonNull TextView textView) {
        if (VERSION.SDK_INT >= 27) {
            return textView.getAutoSizeMinTextSize();
        }
        if (textView instanceof AutoSizeableTextView) {
            return ((AutoSizeableTextView) textView).getAutoSizeMinTextSize();
        }
        return -1;
    }

    public static int getAutoSizeMaxTextSize(@NonNull TextView textView) {
        if (VERSION.SDK_INT >= 27) {
            return textView.getAutoSizeMaxTextSize();
        }
        if (textView instanceof AutoSizeableTextView) {
            return ((AutoSizeableTextView) textView).getAutoSizeMaxTextSize();
        }
        return -1;
    }

    @NonNull
    public static int[] getAutoSizeTextAvailableSizes(@NonNull TextView textView) {
        if (VERSION.SDK_INT >= 27) {
            return textView.getAutoSizeTextAvailableSizes();
        }
        if (textView instanceof AutoSizeableTextView) {
            return ((AutoSizeableTextView) textView).getAutoSizeTextAvailableSizes();
        }
        return new int[0];
    }

    public static void setCustomSelectionActionModeCallback(@NonNull TextView textView, @NonNull Callback callback) {
        textView.setCustomSelectionActionModeCallback(wrapCustomSelectionActionModeCallback(textView, callback));
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @NonNull
    public static Callback wrapCustomSelectionActionModeCallback(@NonNull TextView textView, @NonNull Callback callback) {
        if (VERSION.SDK_INT < 26 || VERSION.SDK_INT > 27 || (callback instanceof OreoCallback)) {
            return callback;
        }
        return new OreoCallback(callback, textView);
    }

    public static void setFirstBaselineToTopHeight(@NonNull TextView textView, @C0017Px @IntRange(from = 0) int firstBaselineToTopHeight) {
        int fontMetricsTop;
        Preconditions.checkArgumentNonnegative(firstBaselineToTopHeight);
        if (VERSION.SDK_INT >= 28) {
            textView.setFirstBaselineToTopHeight(firstBaselineToTopHeight);
            return;
        }
        FontMetricsInt fontMetrics = textView.getPaint().getFontMetricsInt();
        if (VERSION.SDK_INT < 16 || textView.getIncludeFontPadding()) {
            fontMetricsTop = fontMetrics.top;
        } else {
            fontMetricsTop = fontMetrics.ascent;
        }
        if (firstBaselineToTopHeight > Math.abs(fontMetricsTop)) {
            textView.setPadding(textView.getPaddingLeft(), firstBaselineToTopHeight - (-fontMetricsTop), textView.getPaddingRight(), textView.getPaddingBottom());
        }
    }

    public static void setLastBaselineToBottomHeight(@NonNull TextView textView, @C0017Px @IntRange(from = 0) int lastBaselineToBottomHeight) {
        int fontMetricsBottom;
        Preconditions.checkArgumentNonnegative(lastBaselineToBottomHeight);
        FontMetricsInt fontMetrics = textView.getPaint().getFontMetricsInt();
        if (VERSION.SDK_INT < 16 || textView.getIncludeFontPadding()) {
            fontMetricsBottom = fontMetrics.bottom;
        } else {
            fontMetricsBottom = fontMetrics.descent;
        }
        if (lastBaselineToBottomHeight > Math.abs(fontMetricsBottom)) {
            textView.setPadding(textView.getPaddingLeft(), textView.getPaddingTop(), textView.getPaddingRight(), lastBaselineToBottomHeight - fontMetricsBottom);
        }
    }

    public static int getFirstBaselineToTopHeight(@NonNull TextView textView) {
        return textView.getPaddingTop() - textView.getPaint().getFontMetricsInt().top;
    }

    public static int getLastBaselineToBottomHeight(@NonNull TextView textView) {
        return textView.getPaddingBottom() + textView.getPaint().getFontMetricsInt().bottom;
    }

    public static void setLineHeight(@NonNull TextView textView, @C0017Px @IntRange(from = 0) int lineHeight) {
        Preconditions.checkArgumentNonnegative(lineHeight);
        int fontHeight = textView.getPaint().getFontMetricsInt(null);
        if (lineHeight != fontHeight) {
            textView.setLineSpacing((float) (lineHeight - fontHeight), 1.0f);
        }
    }

    @NonNull
    public static Params getTextMetricsParams(@NonNull TextView textView) {
        if (VERSION.SDK_INT >= 28) {
            return new Params(textView.getTextMetricsParams());
        }
        Builder builder = new Builder(new TextPaint(textView.getPaint()));
        if (VERSION.SDK_INT >= 23) {
            builder.setBreakStrategy(textView.getBreakStrategy());
            builder.setHyphenationFrequency(textView.getHyphenationFrequency());
        }
        if (VERSION.SDK_INT >= 18) {
            builder.setTextDirection(getTextDirectionHeuristic(textView));
        }
        return builder.build();
    }

    public static void setTextMetricsParams(@NonNull TextView textView, @NonNull Params params) {
        if (VERSION.SDK_INT >= 18) {
            textView.setTextDirection(getTextDirection(params.getTextDirection()));
        }
        if (VERSION.SDK_INT < 23) {
            float paintTextScaleX = params.getTextPaint().getTextScaleX();
            textView.getPaint().set(params.getTextPaint());
            if (paintTextScaleX == textView.getTextScaleX()) {
                textView.setTextScaleX((paintTextScaleX / 2.0f) + 1.0f);
            }
            textView.setTextScaleX(paintTextScaleX);
            return;
        }
        textView.getPaint().set(params.getTextPaint());
        textView.setBreakStrategy(params.getBreakStrategy());
        textView.setHyphenationFrequency(params.getHyphenationFrequency());
    }

    public static void setPrecomputedText(@NonNull TextView textView, @NonNull PrecomputedTextCompat precomputed) {
        if (VERSION.SDK_INT >= 28) {
            textView.setText(precomputed.getPrecomputedText());
        } else if (getTextMetricsParams(textView).equals(precomputed.getParams())) {
            textView.setText(precomputed);
        } else {
            throw new IllegalArgumentException("Given text can not be applied to TextView.");
        }
    }

    @RequiresApi(18)
    private static TextDirectionHeuristic getTextDirectionHeuristic(@NonNull TextView textView) {
        TextDirectionHeuristic textDirectionHeuristic;
        if (textView.getTransformationMethod() instanceof PasswordTransformationMethod) {
            return TextDirectionHeuristics.LTR;
        }
        boolean z = false;
        if (VERSION.SDK_INT < 28 || (textView.getInputType() & 15) != 3) {
            if (textView.getLayoutDirection() == 1) {
                z = true;
            }
            boolean defaultIsRtl = z;
            switch (textView.getTextDirection()) {
                case 2:
                    return TextDirectionHeuristics.ANYRTL_LTR;
                case 3:
                    return TextDirectionHeuristics.LTR;
                case 4:
                    return TextDirectionHeuristics.RTL;
                case 5:
                    return TextDirectionHeuristics.LOCALE;
                case 6:
                    return TextDirectionHeuristics.FIRSTSTRONG_LTR;
                case 7:
                    return TextDirectionHeuristics.FIRSTSTRONG_RTL;
                default:
                    if (defaultIsRtl) {
                        textDirectionHeuristic = TextDirectionHeuristics.FIRSTSTRONG_RTL;
                    } else {
                        textDirectionHeuristic = TextDirectionHeuristics.FIRSTSTRONG_LTR;
                    }
                    return textDirectionHeuristic;
            }
        } else {
            byte digitDirection = Character.getDirectionality(DecimalFormatSymbols.getInstance(textView.getTextLocale()).getDigitStrings()[0].codePointAt(0));
            if (digitDirection == 1 || digitDirection == 2) {
                return TextDirectionHeuristics.RTL;
            }
            return TextDirectionHeuristics.LTR;
        }
    }

    @RequiresApi(18)
    private static int getTextDirection(@NonNull TextDirectionHeuristic heuristic) {
        if (heuristic == TextDirectionHeuristics.FIRSTSTRONG_RTL || heuristic == TextDirectionHeuristics.FIRSTSTRONG_LTR) {
            return 1;
        }
        if (heuristic == TextDirectionHeuristics.ANYRTL_LTR) {
            return 2;
        }
        if (heuristic == TextDirectionHeuristics.LTR) {
            return 3;
        }
        if (heuristic == TextDirectionHeuristics.RTL) {
            return 4;
        }
        if (heuristic == TextDirectionHeuristics.LOCALE) {
            return 5;
        }
        if (heuristic == TextDirectionHeuristics.FIRSTSTRONG_LTR) {
            return 6;
        }
        if (heuristic == TextDirectionHeuristics.FIRSTSTRONG_RTL) {
            return 7;
        }
        return 1;
    }
}
