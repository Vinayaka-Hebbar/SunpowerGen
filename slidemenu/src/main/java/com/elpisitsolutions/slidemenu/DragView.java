package com.elpisitsolutions.slidemenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class DragView extends FrameLayout implements View.OnTouchListener {

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        isFullScreen = fullScreen;
    }

    public int getAnimationDuration() {
        return animationDuration;
    }

    public void setAnimationDuration(int animationDuration) {
        this.animationDuration = animationDuration;
    }

    public int getOverlayAlpha() {
        return overlayAlpha;
    }

    public void setOverlayAlpha(int overlayAlpha) {
        this.overlayAlpha = overlayAlpha;
    }

    public View getOverlayView() {
        return overlayView;
    }

    @Override
    public abstract boolean onTouch(View view, MotionEvent motionEvent);

    @IntDef({LEFT_TO_RIGHT, RIGHT_TO_LEFT, TOP_TO_BOTTOM, BOTTOM_TO_TOP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DragOrientation {
    }

    private @DragOrientation
    int orientation;


    public static final int LEFT_TO_RIGHT = 0;
    public static final int RIGHT_TO_LEFT = 1;
    public static final int TOP_TO_BOTTOM = 2;
    public static final int BOTTOM_TO_TOP = 3;

    public DragView(Context context) {
        super(context);
        overlayView = new View(context);
        overlayView.setOnTouchListener(this);
    }


    private double draggerButtonHeight, draggerButtonWidth;
    private boolean isFullScreen;
    private int animationDuration;
    private int overlayAlpha;
    private final View overlayView;

    public DragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DragView, 0, 0);
        try {
            draggerButtonHeight = typedArray.getDimension(R.styleable.DragView_draggerButtonHeight, 0.0f);
            draggerButtonWidth = typedArray.getDimension(R.styleable.DragView_draggerButtonWidth, 0.0f);
            isFullScreen = typedArray.getBoolean(R.styleable.DragView_isFullScreen, false);
            animationDuration = typedArray.getInt(R.styleable.DragView_animationDuration, 1000);
            overlayAlpha = typedArray.getInt(R.styleable.DragView_overlayAlpha, 10);
        } finally {
            typedArray.recycle();
        }
        overlayView = new View(context);
        overlayView.setBackgroundColor(Color.parseColor("#C1EFECED"));
        overlayView.setOnTouchListener(this);
    }

    public double getDraggerButtonWidth() {
        return draggerButtonWidth;
    }

    public void setDraggerButtonWidth(double draggerButtonWidth) {
        this.draggerButtonWidth = draggerButtonWidth;
    }

    public double getDraggerButtonHeight() {
        return draggerButtonHeight;
    }

    public void setDraggerButtonHeight(double draggerButtonHeight) {
        this.draggerButtonHeight = draggerButtonHeight;
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        return generateDefaultLayoutParams();
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        LayoutParams layoutParams = new LayoutParams(getContext(), attrs);
        return layoutParams;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
