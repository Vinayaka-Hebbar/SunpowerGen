package com.elpisitsolutions.slidemenu;

import android.view.View;

interface HideListener{
    void onHide(DragGestureListener listener);
}

interface  ShowListener {
    void onShow(DragGestureListener listener);
}
