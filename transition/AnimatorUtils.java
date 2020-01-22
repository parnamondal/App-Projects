package android.support.transition;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import java.util.ArrayList;

class AnimatorUtils {

    interface AnimatorPauseListenerCompat {
        void onAnimationPause(Animator animator);

        void onAnimationResume(Animator animator);
    }

    static void addPauseListener(@NonNull Animator animator, @NonNull AnimatorListenerAdapter listener) {
        if (VERSION.SDK_INT >= 19) {
            animator.addPauseListener(listener);
        }
    }

    static void pause(@NonNull Animator animator) {
        if (VERSION.SDK_INT >= 19) {
            animator.pause();
            return;
        }
        ArrayList<AnimatorListener> listeners = animator.getListeners();
        if (listeners != null) {
            int size = listeners.size();
            for (int i = 0; i < size; i++) {
                AnimatorListener listener = (AnimatorListener) listeners.get(i);
                if (listener instanceof AnimatorPauseListenerCompat) {
                    ((AnimatorPauseListenerCompat) listener).onAnimationPause(animator);
                }
            }
        }
    }

    static void resume(@NonNull Animator animator) {
        if (VERSION.SDK_INT >= 19) {
            animator.resume();
            return;
        }
        ArrayList<AnimatorListener> listeners = animator.getListeners();
        if (listeners != null) {
            int size = listeners.size();
            for (int i = 0; i < size; i++) {
                AnimatorListener listener = (AnimatorListener) listeners.get(i);
                if (listener instanceof AnimatorPauseListenerCompat) {
                    ((AnimatorPauseListenerCompat) listener).onAnimationResume(animator);
                }
            }
        }
    }

    private AnimatorUtils() {
    }
}
