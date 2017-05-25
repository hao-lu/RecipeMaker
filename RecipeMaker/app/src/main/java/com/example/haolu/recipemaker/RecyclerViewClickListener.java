package com.example.haolu.recipemaker;

import android.view.View;

// Interface definition for a callback to nbe invoked when a RecyclerView item is clicked on
public interface RecyclerViewClickListener {
    public void onClick(View view, int position);
    public void onLongClick(View view, int position);
}
