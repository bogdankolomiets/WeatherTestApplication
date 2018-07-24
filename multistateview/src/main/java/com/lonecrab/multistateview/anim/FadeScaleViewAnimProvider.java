package com.lonecrab.multistateview.anim;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;

import com.lonecrab.multistateview.MultiStateView;

public class FadeScaleViewAnimProvider implements ViewAnimProvider {

  @Override
  public Animation showAnimation() {
    AnimationSet set = new AnimationSet(true);
    Animation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
    Animation scaleAnimation = new ScaleAnimation(0.1f, 1f, 0.1f, 1f,
        Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.5f);

    set.setDuration(MultiStateView.DEFAULT_ANIMATION_DURATION);
    set.setInterpolator(new DecelerateInterpolator());
    set.addAnimation(alphaAnimation);
    set.addAnimation(scaleAnimation);
    return set;
  }

  @Override
  public Animation hideAnimation() {
    AnimationSet set = new AnimationSet(true);
    Animation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
    Animation scaleAnimation = new ScaleAnimation(1.0f, 0.1f, 1.0f, 0.1f,
        Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.5f);

    set.setDuration(MultiStateView.DEFAULT_ANIMATION_DURATION);
    set.setInterpolator(new DecelerateInterpolator());
    set.addAnimation(alphaAnimation);
    set.addAnimation(scaleAnimation);
    return set;
  }
}
