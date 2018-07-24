package com.lonecrab.multistateview.anim;

import android.view.animation.Animation;

public interface ViewAnimProvider {
  Animation showAnimation();

  Animation hideAnimation();
}
