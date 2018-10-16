package com.elpisitsolutions.slidemenu;

import android.graphics.Rect;
import android.util.Size;

public interface DragGestureListener {
    void DragBegin(double x, double y);
    void DragMove(double x, double y);
    void DragEnd();
    void setLayoutListener(LayoutListener listener);
    @DragView.DragOrientation int getDragOrientation();
    Rect getHidePosition();
    Rect getShowPosition();
    void updateSize();
}
