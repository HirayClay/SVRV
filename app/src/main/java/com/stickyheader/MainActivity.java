package com.stickyheader;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    FrameLayout frameLayout;
    View filterHeader;
    View underlyingFilterHeader;
    NestedScrollView scrollView;

    boolean pending = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.framelayout);
        filterHeader = findViewById(R.id.filter_header);
        scrollView = findViewById(R.id.scrollView);
        RecyclerView rv = findViewById(R.id.rv);
//        rv.setHasFixedSize(true);
        rv.setFocusableInTouchMode(false);
        rv.setAdapter(new DummyAdapter());
        underlyingFilterHeader = getLayoutInflater().inflate(R.layout.filter_item, null, false);
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (filterHeader.getTop() - scrollY <= 0)
                    add();
                else remove();
            }
        });
    }

    private void remove() {
        if (pending)
            return;

        ViewGroup parent = (ViewGroup) underlyingFilterHeader.getParent();
        if (parent == null)
            return;
        pending = true;
        frameLayout.removeView(underlyingFilterHeader);
        pending = false;
    }

    private void add() {
        if (pending)
            return;
        ViewGroup parent = (ViewGroup) underlyingFilterHeader.getParent();
        if (parent != null)
            return;
        pending = true;
        frameLayout.addView(underlyingFilterHeader);
        pending = false;
    }
}
