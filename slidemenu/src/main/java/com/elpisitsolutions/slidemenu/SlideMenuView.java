package com.elpisitsolutions.slidemenu;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SlideMenuView extends DragView implements LayoutListener {
    private ColorDrawable layerBackground;
    private int restoreAlpha;

    public void setOrientationMode(@OrientationMode int orientationMode) {
        this.orientationMode = orientationMode;
        gestureListener = DragGestureFactory.create(this, orientationMode);
        gestureListener.setLayoutListener(this);
    }

    public @OrientationMode
    int getOrientationMode() {
        return orientationMode;
    }

    private ViewGroup parent;

    @Override
    public void onUpdateLayout(double left, double top, double right, double bottom) {
        setX((float) left);
        setY((float) top);
    }

    @Override
    public void onStateChanged(boolean needToOpen) {
        if (needToOpen) {
            show();
        } else {
            hide();
        }
    }

    public void setParent(ViewGroup parent) {
        this.parent = parent;
    }

    public void onParentSizeChanged() {
        getOverlayView().measure(View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.EXACTLY));
        getOverlayView().layout(0, 0, parent.getWidth(), parent.getHeight());
        if (gestureListener != null) {
            gestureListener.updateSize();
            Rect rect = gestureListener.getHidePosition();
            layout(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @IntDef({HORIZONTAL, VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    @interface OrientationMode {
    }

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    private @OrientationMode
    int orientationMode;
    private DragGestureListener gestureListener;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        hide();
        return false;
    }

    public SlideMenuView(Context context) {
        super(context);
    }

    public SlideMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(final ViewGroup parent, @OrientationMode int orientationMode) {
        this.parent = parent;
        setOrientationMode(orientationMode);
        setVisibility(INVISIBLE);
    }

    public void init(@OrientationMode int orientationMode) {
        init(parent, orientationMode);
    }

    public void show() {
        int height = getHeight();
        Rect rect = gestureListener.getShowPosition();
        animate().x(rect.left)
                .y(rect.top)
                .setListener(new ViewAnimatorListener(this, true))
                .setDuration(getAnimationDuration())
                .start();
    }

    public void show(ShowListener listener) {
        listener.onShow(gestureListener);
    }

    public void hide() {
        Rect rect = gestureListener.getHidePosition();
        animate().x(rect.left)
                .y(rect.top)
                .setListener(new ViewAnimatorListener(this, false))
                .setDuration(getAnimationDuration())
                .start();
    }

    public void Hide(HideListener listener) {
        listener.onHide(gestureListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction() & 255;
        if (action == 0) {
            gestureListener.DragBegin(event.getRawX(), event.getRawY());
        } else if (action == 2) {
            gestureListener.DragMove(event.getRawX(), event.getRawY());
        } else if (action == 1) {
            gestureListener.DragEnd();
        }
        return true;
    }

    class ViewAnimatorListener implements Animator.AnimatorListener {
        private final SlideMenuView view;
        private final boolean isVisible;

        ViewAnimatorListener(SlideMenuView view, boolean isVisible) {
            this.view = view;
            this.isVisible = isVisible;
            if (isVisible) {
                view.initOverlay();
            }
        }

        @Override
        public void onAnimationStart(Animator animator) {
            view.setVisibility(VISIBLE);
        }

        @Override
        public void onAnimationEnd(Animator animator) {
            if (!isVisible) {
                view.clearOverlay();
                view.setVisibility(INVISIBLE);
            }
        }

        @Override
        public void onAnimationCancel(Animator animator) {
            view.setVisibility(INVISIBLE);
        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    }

    private void clearOverlay() {
        parent.removeView(getOverlayView());
    }

    private void initOverlay() {
        if (getOverlayView().getParent() == null)
            parent.addView(getOverlayView());
        bringToFront();
    }

    private void restoreOverlayBackground() {
        layerBackground.setAlpha(restoreAlpha);
    }
}
