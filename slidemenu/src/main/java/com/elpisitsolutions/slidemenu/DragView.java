package com.elpisitsolutions.slidemenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DragView extends FrameLayout {

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
    }


    private double draggerButtonHeight, draggerButtonWidth;
    private boolean isFullScreen;
    private int animationDuration = 1000;

    public DragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DragView, 0, 0);
        try {
            draggerButtonHeight = typedArray.getDimension(R.styleable.DragView_draggerButtonHeight, 0.0f);
            draggerButtonWidth = typedArray.getDimension(R.styleable.DragView_draggerButtonWidth, 0.0f);
            isFullScreen = typedArray.getBoolean(R.styleable.DragView_isFullScreen, false);
            animationDuration = typedArray.getInt(R.styleable.DragView_animationDuration, 1000);
        } finally {
            typedArray.recycle();
        }
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
