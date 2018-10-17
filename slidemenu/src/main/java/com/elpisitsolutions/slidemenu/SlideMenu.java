package com.elpisitsolutions.slidemenu;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class SlideMenu extends FrameLayout {
    private SlideMenuView slideMenuView;

    public SlideMenu(@NonNull Context context) {
        super(context);
    }

    public static SlideMenu get(Context context, int res, int parentId) {
        ViewGroup parent = ((Activity) context).findViewById(parentId);
        SlideMenu slideMenu = new SlideMenu(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            View view = inflater.inflate(res, slideMenu, false);
            if (view instanceof SlideMenuView) {
                ((SlideMenuView) view).setParent(slideMenu);
                slideMenu.setSlideMenuView((SlideMenuView) view);
            }
        }
        parent.addView(slideMenu);
        return slideMenu;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (slideMenuView != null) {
            slideMenuView.onParentSizeChanged();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

    }

    public void setSlideMenuView(SlideMenuView slideMenuView) {
        this.slideMenuView = slideMenuView;
        addView(slideMenuView);
    }

    public SlideMenuView getSlideMenuView() {
        return slideMenuView;
    }
}
