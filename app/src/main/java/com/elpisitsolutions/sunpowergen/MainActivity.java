package com.elpisitsolutions.sunpowergen;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.elpisitsolutions.slidemenu.ShadowDrawable;
import com.elpisitsolutions.slidemenu.SlideMenu;
import com.elpisitsolutions.slidemenu.SlideMenuView;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SlideMenuView slideMenuView;

    private DrawerArrowDrawable arrowDrawable;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setElevation(1.2f);
            arrowDrawable = new DrawerArrowDrawable(this);
            bar.setHomeAsUpIndicator(arrowDrawable);
        }
        drawerLayout = findViewById(R.id.drawer_main);
        FrameLayout layout = findViewById(R.id.frame);
        slideMenuView = SlideMenu.get(this, R.layout.slide_menu, R.id.drawer_main).getSlideMenuView();
        slideMenuView.setBackground(new ShadowDrawable().createLayerDrawable());
        slideMenuView.init(SlideMenuView.VERTICAL);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.START);
                arrowDrawable.setVerticalMirror(false);
                arrowDrawable.setProgress(0f);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onShow(View view) {
        slideMenuView.show();
    }

    public void onHide(View view) {
        slideMenuView.hide();
    }
}
