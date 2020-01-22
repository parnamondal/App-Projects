package android.support.transition;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p000v4.view.ViewCompat;
import android.util.Log;
import android.util.Property;
import android.view.View;
import java.lang.reflect.Field;

class ViewUtils {
    static final Property<View, Rect> CLIP_BOUNDS = new Property<View, Rect>(Rect.class, "clipBounds") {
        public Rect get(View view) {
            return ViewCompat.getClipBounds(view);
        }

        public void set(View view, Rect clipBounds) {
            ViewCompat.setClipBounds(view, clipBounds);
        }
    };
    private static final ViewUtilsBase IMPL;
    private static final String TAG = "ViewUtils";
    static final Property<View, Float> TRANSITION_ALPHA = new Property<View, Float>(Float.class, "translationAlpha") {
        public Float get(View view) {
            return Float.valueOf(ViewUtils.getTransitionAlpha(view));
        }

        public void set(View view, Float alpha) {
            ViewUtils.setTransitionAlpha(view, alpha.floatValue());
        }
    };
    private static final int VISIBILITY_MASK = 12;
    private static Field sViewFlagsField;
    private static boolean sViewFlagsFieldFetched;

    static {
        if (VERSION.SDK_INT >= 22) {
            IMPL = new ViewUtilsApi22();
        } else if (VERSION.SDK_INT >= 21) {
            IMPL = new ViewUtilsApi21();
        } else if (VERSION.SDK_INT >= 19) {
            IMPL = new ViewUtilsApi19();
        } else {
            IMPL = new ViewUtilsBase();
        }
    }

    static ViewOverlayImpl getOverlay(@NonNull View view) {
        if (VERSION.SDK_INT >= 18) {
            return new ViewOverlayApi18(view);
        }
        return ViewOverlayApi14.createFrom(view);
    }

    static WindowIdImpl getWindowId(@NonNull View view) {
        if (VERSION.SDK_INT >= 18) {
            return new WindowIdApi18(view);
        }
        return new WindowIdApi14(view.getWindowToken());
    }

    static void setTransitionAlpha(@NonNull View view, float alpha) {
        IMPL.setTransitionAlpha(view, alpha);
    }

    static float getTransitionAlpha(@NonNull View view) {
        return IMPL.getTransitionAlpha(view);
    }

    static void saveNonTransitionAlpha(@NonNull View view) {
        IMPL.saveNonTransitionAlpha(view);
    }

    static void clearNonTransitionAlpha(@NonNull View view) {
        IMPL.clearNonTransitionAlpha(view);
    }

    static void setTransitionVisibility(@NonNull View view, int visibility) {
        fetchViewFlagsField();
        Field field = sViewFlagsField;
        if (field != null) {
            try {
                sViewFlagsField.setInt(view, (field.getInt(view) & -13) | visibility);
            } catch (IllegalAccessException e) {
            }
        }
    }

    static void transformMatrixToGlobal(@NonNull View view, @NonNull Matrix matrix) {
        IMPL.transformMatrixToGlobal(view, matrix);
    }

    static void transformMatrixToLocal(@NonNull View view, @NonNull Matrix matrix) {
        IMPL.transformMatrixToLocal(view, matrix);
    }

    static void setAnimationMatrix(@NonNull View v, @Nullable Matrix m) {
        IMPL.setAnimationMatrix(v, m);
    }

    static void setLeftTopRightBottom(@NonNull View v, int left, int top, int right, int bottom) {
        IMPL.setLeftTopRightBottom(v, left, top, right, bottom);
    }

    private static void fetchViewFlagsField() {
        if (!sViewFlagsFieldFetched) {
            try {
                sViewFlagsField = View.class.getDeclaredField("mViewFlags");
                sViewFlagsField.setAccessible(true);
            } catch (NoSuchFieldException e) {
                Log.i(TAG, "fetchViewFlagsField: ");
            }
            sViewFlagsFieldFetched = true;
        }
    }

    private ViewUtils() {
    }
}
