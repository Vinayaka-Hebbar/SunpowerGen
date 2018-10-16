package com.elpisitsolutions.slidemenu;

public interface LayoutListener {
    void onUpdateLayout(double left,double top, double right, double bottom);
    void onStateChanged(boolean needToOpen);
}
