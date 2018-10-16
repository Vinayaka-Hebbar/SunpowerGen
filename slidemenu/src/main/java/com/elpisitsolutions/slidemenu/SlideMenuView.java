package com.elpisitsolutions.slidemenu;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SlideMenuView extends DragView implements LayoutListener {

    private final int DOWN = 0;
    private final int UP = 1;
    private final int MOVE = 2;
    private final int MASK = 255;
    private final Context context;

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

    @IntDef({HORIZONTAL, VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface OrientationMode {
    }

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    private @OrientationMode
    int orientationMode;
    private DragGestureListener gestureListener;

    public SlideMenuView(Context context) {
        super(context);
        this.context = context;
    }

    public SlideMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void init(final ViewGroup parent, @OrientationMode int orientationMode) {
        this.parent = parent;
        setOrientationMode(orientationMode);
        post(new Runnable() {
            @Override
            public void run() {
                //I Don't Know what is this
                gestureListener.updateSize();
                Rect rect = gestureListener.getHidePosition();
                layout(rect.left, rect.top, rect.right, rect.bottom);
            }
        });
        parent.addView(this);
        parent.bringChildToFront(SlideMenuView.this);
    }

    public void init(int parentId, @OrientationMode int orientationMode) {
        ViewGroup parent = ((Activity) context).findViewById(parentId);
        init(parent, orientationMode);
    }

    public void show() {
        Rect rect = gestureListener.getShowPosition();
        animate().x(rect.left)
                .y(rect.top)
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
                .setDuration(getAnimationDuration())
                .start();
    }

    public void Hide(HideListener listener) {
        listener.onHide(gestureListener);
    }

    public static SlideMenuView get(Context context, int res, int parentId) {
        ViewGroup parent = ((Activity) context).findViewById(parentId);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            View view = inflater.inflate(res, parent, false);
            if (view instanceof SlideMenuView) {
                return (SlideMenuView) view;
            }
        }
        return null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction() & MASK;
        if (action == DOWN) {
            gestureListener.DragBegin(event.getRawX(), event.getRawY());
        } else if (action == MOVE) {
            gestureListener.DragMove(event.getRawX(), event.getRawY());
        } else if (action == UP) {
            gestureListener.DragEnd();
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (gestureListener != null) {
            gestureListener.updateSize();
            Rect rect = gestureListener.getHidePosition();
            layout(rect.left, rect.top, rect.right, rect.bottom);
        }
    }
}
