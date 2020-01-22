package android.support.design.widget;

import android.content.Context;
import android.support.p000v4.math.MathUtils;
import android.support.p000v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;

abstract class HeaderBehavior<V extends View> extends ViewOffsetBehavior<V> {
    private static final int INVALID_POINTER = -1;
    private int activePointerId = -1;
    private Runnable flingRunnable;
    private boolean isBeingDragged;
    private int lastMotionY;
    OverScroller scroller;
    private int touchSlop = -1;
    private VelocityTracker velocityTracker;

    private class FlingRunnable implements Runnable {
        private final V layout;
        private final CoordinatorLayout parent;

        FlingRunnable(CoordinatorLayout parent2, V layout2) {
            this.parent = parent2;
            this.layout = layout2;
        }

        public void run() {
            if (this.layout != null && HeaderBehavior.this.scroller != null) {
                if (HeaderBehavior.this.scroller.computeScrollOffset()) {
                    HeaderBehavior headerBehavior = HeaderBehavior.this;
                    headerBehavior.setHeaderTopBottomOffset(this.parent, this.layout, headerBehavior.scroller.getCurrY());
                    ViewCompat.postOnAnimation(this.layout, this);
                    return;
                }
                HeaderBehavior.this.onFlingFinished(this.parent, this.layout);
            }
        }
    }

    public HeaderBehavior() {
    }

    public HeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent ev) {
        if (this.touchSlop < 0) {
            this.touchSlop = ViewConfiguration.get(parent.getContext()).getScaledTouchSlop();
        }
        if (ev.getAction() == 2 && this.isBeingDragged) {
            return true;
        }
        switch (ev.getActionMasked()) {
            case 0:
                this.isBeingDragged = false;
                int x = (int) ev.getX();
                int y = (int) ev.getY();
                if (canDragView(child) && parent.isPointInChildBounds(child, x, y)) {
                    this.lastMotionY = y;
                    this.activePointerId = ev.getPointerId(0);
                    ensureVelocityTracker();
                    break;
                }
            case 1:
            case 3:
                this.isBeingDragged = false;
                this.activePointerId = -1;
                VelocityTracker velocityTracker2 = this.velocityTracker;
                if (velocityTracker2 != null) {
                    velocityTracker2.recycle();
                    this.velocityTracker = null;
                    break;
                }
                break;
            case 2:
                int activePointerId2 = this.activePointerId;
                if (activePointerId2 != -1) {
                    int pointerIndex = ev.findPointerIndex(activePointerId2);
                    if (pointerIndex != -1) {
                        int y2 = (int) ev.getY(pointerIndex);
                        if (Math.abs(y2 - this.lastMotionY) > this.touchSlop) {
                            this.isBeingDragged = true;
                            this.lastMotionY = y2;
                            break;
                        }
                    }
                }
                break;
        }
        VelocityTracker velocityTracker3 = this.velocityTracker;
        if (velocityTracker3 != null) {
            velocityTracker3.addMovement(ev);
        }
        return this.isBeingDragged;
    }

    public boolean onTouchEvent(CoordinatorLayout parent, V child, MotionEvent ev) {
        if (this.touchSlop < 0) {
            this.touchSlop = ViewConfiguration.get(parent.getContext()).getScaledTouchSlop();
        }
        switch (ev.getActionMasked()) {
            case 0:
                int y = (int) ev.getY();
                if (parent.isPointInChildBounds(child, (int) ev.getX(), y) && canDragView(child)) {
                    this.lastMotionY = y;
                    this.activePointerId = ev.getPointerId(0);
                    ensureVelocityTracker();
                    break;
                } else {
                    return false;
                }
            case 1:
                VelocityTracker velocityTracker2 = this.velocityTracker;
                if (velocityTracker2 != null) {
                    velocityTracker2.addMovement(ev);
                    this.velocityTracker.computeCurrentVelocity(1000);
                    fling(parent, child, -getScrollRangeForDragFling(child), 0, this.velocityTracker.getYVelocity(this.activePointerId));
                    break;
                }
                break;
            case 2:
                int activePointerIndex = ev.findPointerIndex(this.activePointerId);
                if (activePointerIndex != -1) {
                    int y2 = (int) ev.getY(activePointerIndex);
                    int dy = this.lastMotionY - y2;
                    if (!this.isBeingDragged) {
                        int abs = Math.abs(dy);
                        int i = this.touchSlop;
                        if (abs > i) {
                            this.isBeingDragged = true;
                            dy = dy > 0 ? dy - i : dy + i;
                        }
                    }
                    if (this.isBeingDragged) {
                        this.lastMotionY = y2;
                        scroll(parent, child, dy, getMaxDragOffset(child), 0);
                        break;
                    }
                } else {
                    return false;
                }
                break;
            case 3:
                break;
        }
        this.isBeingDragged = false;
        this.activePointerId = -1;
        VelocityTracker velocityTracker3 = this.velocityTracker;
        if (velocityTracker3 != null) {
            velocityTracker3.recycle();
            this.velocityTracker = null;
        }
        VelocityTracker velocityTracker4 = this.velocityTracker;
        if (velocityTracker4 != null) {
            velocityTracker4.addMovement(ev);
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public int setHeaderTopBottomOffset(CoordinatorLayout parent, V header, int newOffset) {
        return setHeaderTopBottomOffset(parent, header, newOffset, Integer.MIN_VALUE, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
    }

    /* access modifiers changed from: 0000 */
    public int setHeaderTopBottomOffset(CoordinatorLayout parent, V v, int newOffset, int minOffset, int maxOffset) {
        int curOffset = getTopAndBottomOffset();
        if (minOffset == 0 || curOffset < minOffset || curOffset > maxOffset) {
            return 0;
        }
        int newOffset2 = MathUtils.clamp(newOffset, minOffset, maxOffset);
        if (curOffset == newOffset2) {
            return 0;
        }
        setTopAndBottomOffset(newOffset2);
        return curOffset - newOffset2;
    }

    /* access modifiers changed from: 0000 */
    public int getTopBottomOffsetForScrollingSibling() {
        return getTopAndBottomOffset();
    }

    /* access modifiers changed from: 0000 */
    public final int scroll(CoordinatorLayout coordinatorLayout, V header, int dy, int minOffset, int maxOffset) {
        return setHeaderTopBottomOffset(coordinatorLayout, header, getTopBottomOffsetForScrollingSibling() - dy, minOffset, maxOffset);
    }

    /* access modifiers changed from: 0000 */
    public final boolean fling(CoordinatorLayout coordinatorLayout, V layout, int minOffset, int maxOffset, float velocityY) {
        V v = layout;
        Runnable runnable = this.flingRunnable;
        if (runnable != null) {
            layout.removeCallbacks(runnable);
            this.flingRunnable = null;
        }
        if (this.scroller == null) {
            this.scroller = new OverScroller(layout.getContext());
        }
        this.scroller.fling(0, getTopAndBottomOffset(), 0, Math.round(velocityY), 0, 0, minOffset, maxOffset);
        if (this.scroller.computeScrollOffset()) {
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            this.flingRunnable = new FlingRunnable(coordinatorLayout, layout);
            ViewCompat.postOnAnimation(layout, this.flingRunnable);
            return true;
        }
        CoordinatorLayout coordinatorLayout3 = coordinatorLayout;
        onFlingFinished(coordinatorLayout, layout);
        return false;
    }

    /* access modifiers changed from: 0000 */
    public void onFlingFinished(CoordinatorLayout parent, V v) {
    }

    /* access modifiers changed from: 0000 */
    public boolean canDragView(V v) {
        return false;
    }

    /* access modifiers changed from: 0000 */
    public int getMaxDragOffset(V view) {
        return -view.getHeight();
    }

    /* access modifiers changed from: 0000 */
    public int getScrollRangeForDragFling(V view) {
        return view.getHeight();
    }

    private void ensureVelocityTracker() {
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
    }
}
