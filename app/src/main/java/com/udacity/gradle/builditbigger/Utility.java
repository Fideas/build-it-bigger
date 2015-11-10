package com.udacity.gradle.builditbigger;

import android.view.View;

/**
 * Utility methods
 */
public class Utility {
    public static void swapVisibility(View[] views){
        for (View view : views){
            int visibility = view.getVisibility();
            switch (visibility) {
                case View.VISIBLE:
                    view.setVisibility(View.GONE);
                    break;
                default:
                    view.setVisibility(View.VISIBLE);
            }
        }
    }
}
