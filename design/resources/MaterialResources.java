package android.support.design.resources;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StyleableRes;
import android.support.p003v7.content.res.AppCompatResources;

@RestrictTo({Scope.LIBRARY_GROUP})
public class MaterialResources {
    private MaterialResources() {
    }

    @Nullable
    public static ColorStateList getColorStateList(Context context, TypedArray attributes, @StyleableRes int index) {
        if (attributes.hasValue(index)) {
            int resourceId = attributes.getResourceId(index, 0);
            if (resourceId != 0) {
                ColorStateList value = AppCompatResources.getColorStateList(context, resourceId);
                if (value != null) {
                    return value;
                }
            }
        }
        return attributes.getColorStateList(index);
    }

    @Nullable
    public static Drawable getDrawable(Context context, TypedArray attributes, @StyleableRes int index) {
        if (attributes.hasValue(index)) {
            int resourceId = attributes.getResourceId(index, 0);
            if (resourceId != 0) {
                Drawable value = AppCompatResources.getDrawable(context, resourceId);
                if (value != null) {
                    return value;
                }
            }
        }
        return attributes.getDrawable(index);
    }

    @Nullable
    public static TextAppearance getTextAppearance(Context context, TypedArray attributes, @StyleableRes int index) {
        if (attributes.hasValue(index)) {
            int resourceId = attributes.getResourceId(index, 0);
            if (resourceId != 0) {
                return new TextAppearance(context, resourceId);
            }
        }
        return null;
    }

    @StyleableRes
    static int getIndexWithValue(TypedArray attributes, @StyleableRes int a, @StyleableRes int b) {
        if (attributes.hasValue(a)) {
            return a;
        }
        return b;
    }
}
