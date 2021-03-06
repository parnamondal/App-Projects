package android.support.transition;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Scene {
    private Context mContext;
    private Runnable mEnterAction;
    private Runnable mExitAction;
    private View mLayout;
    private int mLayoutId = -1;
    private ViewGroup mSceneRoot;

    @NonNull
    public static Scene getSceneForLayout(@NonNull ViewGroup sceneRoot, @LayoutRes int layoutId, @NonNull Context context) {
        SparseArray sparseArray = (SparseArray) sceneRoot.getTag(C0180R.C0182id.transition_scene_layoutid_cache);
        if (sparseArray == null) {
            sparseArray = new SparseArray();
            sceneRoot.setTag(C0180R.C0182id.transition_scene_layoutid_cache, sparseArray);
        }
        Scene scene = (Scene) sparseArray.get(layoutId);
        if (scene != null) {
            return scene;
        }
        Scene scene2 = new Scene(sceneRoot, layoutId, context);
        sparseArray.put(layoutId, scene2);
        return scene2;
    }

    public Scene(@NonNull ViewGroup sceneRoot) {
        this.mSceneRoot = sceneRoot;
    }

    private Scene(ViewGroup sceneRoot, int layoutId, Context context) {
        this.mContext = context;
        this.mSceneRoot = sceneRoot;
        this.mLayoutId = layoutId;
    }

    public Scene(@NonNull ViewGroup sceneRoot, @NonNull View layout) {
        this.mSceneRoot = sceneRoot;
        this.mLayout = layout;
    }

    @NonNull
    public ViewGroup getSceneRoot() {
        return this.mSceneRoot;
    }

    public void exit() {
        if (getCurrentScene(this.mSceneRoot) == this) {
            Runnable runnable = this.mExitAction;
            if (runnable != null) {
                runnable.run();
            }
        }
    }

    public void enter() {
        if (this.mLayoutId > 0 || this.mLayout != null) {
            getSceneRoot().removeAllViews();
            if (this.mLayoutId > 0) {
                LayoutInflater.from(this.mContext).inflate(this.mLayoutId, this.mSceneRoot);
            } else {
                this.mSceneRoot.addView(this.mLayout);
            }
        }
        Runnable runnable = this.mEnterAction;
        if (runnable != null) {
            runnable.run();
        }
        setCurrentScene(this.mSceneRoot, this);
    }

    static void setCurrentScene(View view, Scene scene) {
        view.setTag(C0180R.C0182id.transition_current_scene, scene);
    }

    static Scene getCurrentScene(View view) {
        return (Scene) view.getTag(C0180R.C0182id.transition_current_scene);
    }

    public void setEnterAction(@Nullable Runnable action) {
        this.mEnterAction = action;
    }

    public void setExitAction(@Nullable Runnable action) {
        this.mExitAction = action;
    }

    /* access modifiers changed from: 0000 */
    public boolean isCreatedFromLayoutResource() {
        return this.mLayoutId > 0;
    }
}
