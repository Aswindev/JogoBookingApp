package com.crazylabs.jogobookingapp.Utils;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 * Created by aswin on 13/10/2017.
 */

public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.75f;
//    private static final float MIN_ALPHA = 0.5f;
    private float offset = -1;
    private float paddingLeft;



    public ZoomOutPageTransformer(float paddingLeft){
        this.paddingLeft = paddingLeft;
    }


    public void transformPage(View page, float position) {
        Log.d("zoomoutpagetransformer", "position: "+position);
        if (offset == -1) {
            offset = paddingLeft / page.getMeasuredWidth();
        }
        if (position < -1) {
//            page.setAlpha(0);
        } else if (position <= 1) {
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position - offset));
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
//            float alphaFactor = Math.max(MIN_ALPHA, 1 - Math.abs(position - offset));
//            page.setAlpha(alphaFactor);
        } else {
//            page.setAlpha(0);
        }
    }
}
