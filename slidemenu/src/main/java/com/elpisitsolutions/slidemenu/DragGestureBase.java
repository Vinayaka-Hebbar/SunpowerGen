package com.elpisitsolutions.slidemenu;

import android.app.Activity;
import android.util.DisplayMetrics;

abstract class DragGestureBase implements DragGestureListener {
    private final DragView view;
    protected double oldX, oldY, left, right, top, bottom = 0;
    protected double screenWidth, screenHeight, density;
    protected boolean willShown = false;
    private LayoutListener listener;

    DragGestureBase(DragView view) {
        this.view = view;
        initScreen();
    }

    protected void initScreen() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (view.getContext() instanceof Activity) {
            ((Activity) view.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        }
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
        density = displayMetrics.density;
    }

    @Override
    public abstract void DragBegin(double x, double y);

    @Override
    public abstract void DragMove(double x, double y);

    @Override
    public abstract void DragEnd();

    @Override
    public void setLayoutListener(LayoutListener listener) {
        this.listener = listener;
    }

    protected DragView getView() {
        return view;
    }

    protected void onUpdateLayout() {
        if (listener != null) {
            listener.onUpdateLayout(left, top, right, bottom);
        }
    }

    protected void onStateChanged() {
        if (listener != null) {
            listener.onStateChanged(willShown);
        }
    }

    @Override
    public int getDragOrientation() {
        return view.getOrientation();
    }
}
