package com.example.haolu.recipemaker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerItemTouchListener implements RecyclerView.OnItemTouchListener {

    private RecyclerViewClickListener mClickListener;
    private GestureDetector mGestureDectector;

    public RecyclerItemTouchListener(Context context, RecyclerViewClickListener clickListener) {
        this.mClickListener = clickListener;
        mGestureDectector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        // Get the child under the touch point
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && mClickListener != null && mGestureDectector.onTouchEvent(e))
            mClickListener.onClick(child, rv.getChildAdapterPosition(child));
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
