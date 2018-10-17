package com.elpisitsolutions.slidemenu;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;

public class ShadowDrawable {
    private float cornerRadius = 2;
    private float shadowCornerRadius = 2;
    private int shape = GradientDrawable.RECTANGLE;
    private int shadowColor = Color.parseColor("#CABBBBBB");
    private int color = Color.parseColor("#FFFFFFFF");
    private Rect margin = new Rect(0, 0, 0, 2);

    public LayerDrawable createLayerDrawable() {

        GradientDrawable shadowDrawable = new GradientDrawable();
        shadowDrawable.setShape(shape);
        shadowDrawable.setColor(shadowColor);

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(shape);
        gradientDrawable.setColor(color);
        gradientDrawable.setCornerRadius(cornerRadius);


        LayerDrawable drawable = new LayerDrawable(new Drawable[]{shadowDrawable, gradientDrawable});
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawable.setLayerInsetLeft(1, margin.left);
            drawable.setLayerInsetRight(1, margin.right);
            drawable.setLayerInsetTop(1, margin.top);
            drawable.setLayerInsetBottom(1, margin.bottom);
        }
        return drawable;
    }

    public float getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public float getShadowCornerRadius() {
        return shadowCornerRadius;
    }

    public void setShadowCornerRadius(float shadowCornerRadius) {
        this.shadowCornerRadius = shadowCornerRadius;
    }

    public Rect getMargin() {
        return margin;
    }

    public void setMargin(Rect margin) {
        this.margin = margin;
    }

    public int getShape() {
        return shape;
    }

    public void setShape(int shape) {
        this.shape = shape;
    }
}
