package com.crazylabs.jogobookingapp.Animations;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

/**
 * Created by aswin on 14/10/2017.
 */

public class FadeInAndShowImage {
    public FadeInAndShowImage(final ImageView img) {
        Animation fadeOut = new AlphaAnimation(0, 1);
        fadeOut.setInterpolator(new AccelerateDecelerateInterpolator());
        fadeOut.setDuration(500);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                img.setVisibility(View.VISIBLE);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });

        img.startAnimation(fadeOut);
    }
}
