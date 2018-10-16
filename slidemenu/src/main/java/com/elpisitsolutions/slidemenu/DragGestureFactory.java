package com.elpisitsolutions.slidemenu;

import android.graphics.Rect;
import android.view.ViewGroup;

public class DragGestureFactory {
    public static DragGestureListener create(DragView view, @SlideMenuView.OrientationMode int mode) {
        if (mode == SlideMenuView.HORIZONTAL)
            return new HorizontalDragGesture(view);
        return new VerticalDragGesture(view);
    }

    private static class HorizontalDragGesture extends DragGestureBase {

        HorizontalDragGesture(DragView view) {
            super(view);
        }

        @Override
        public void DragBegin(double x, double y) {

        }

        @Override
        public void DragMove(double x, double y) {

        }

        @Override
        public void DragEnd() {

        }

        @Override
        public Rect getHidePosition() {
            return null;
        }

        @Override
        public Rect getShowPosition() {
            return null;
        }

        @Override
        public void updateSize() {

        }
    }

    static class VerticalDragGesture extends DragGestureBase {
        private double topMax, topMin, bottomMax, bottomMin = 0;
        private boolean isTopToBottom = true;

        VerticalDragGesture(DragView view) {
            super(view);
        }

        public void updateSize() {
            topMax = 0;
            topMin = -(getView().getHeight() - getView().getDraggerButtonHeight());
            bottomMax = getView().getHeight();
            bottomMin = getView().getDraggerButtonHeight();
            if (getDragOrientation() == DragView.BOTTOM_TO_TOP) {
                isTopToBottom = false;
                topMax = screenHeight - getView().getDraggerButtonHeight();
                topMin = screenHeight - getView().getHeight();
                bottomMax = screenHeight + getView().getHeight() - getView().getDraggerButtonHeight();
                bottomMin = screenHeight;
            }
            if (getView().isFullScreen()) {
                left = 0;
                right = screenWidth;
            } else {
                left = getView().getLeft();
                right = getView().getLeft() + screenWidth;
            }
        }

        @Override
        public void DragBegin(double x, double y) {
            oldY = y;
            willShown = true;
        }

        @Override
        public void DragMove(double x, double y) {
            double delta = y - oldY;
            if (delta > -2 && delta < 2) return;
            if (delta > 0)
                willShown = isTopToBottom;
            if (delta < 0)
                willShown = !isTopToBottom;
            top += delta;
            bottom += delta;
            checkUpperBound();
            checkLowerBound();
            onUpdateLayout();
            oldY = y;
        }

        private void checkLowerBound() {
            top = top < topMin ? topMin : top;
            bottom = bottom < bottomMin ? bottomMin : bottom;
        }

        private void checkUpperBound() {
            top = top > topMax ? topMax : top;
            bottom = bottom > bottomMax ? bottomMax : bottom;
        }

        @Override
        public void DragEnd() {
            onStateChanged();
        }

        @Override
        public Rect getHidePosition() {
            return new Rect((int) left,
                    isTopToBottom ? (int) topMin : (int) topMax,
                    (int) right,
                    isTopToBottom ? (int) bottomMin : (int) bottomMax);
        }

        @Override
        public Rect getShowPosition() {
            return new Rect((int) left,
                    isTopToBottom ? (int) topMax : (int) topMin,
                    (int) right,
                    isTopToBottom ? (int) bottomMax : (int) bottomMin);
        }
    }
}
