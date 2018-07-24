package com.lonecrab.multistateview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import com.lonecrab.multistateview.anim.FadeScaleViewAnimProvider;
import com.lonecrab.multistateview.anim.FadeViewAnimProvider;
import com.lonecrab.multistateview.anim.OnContentShowAnimationListener;
import com.lonecrab.multistateview.anim.ViewAnimProvider;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * View that contains 4 different states: Content, Error, Empty, and Loading.<br>
 * Each state has their own separate layout which can be shown/hidden by setting
 * the {@link ViewState} accordingly
 * Every MultiStateView <b><i>MUST</i></b> contain a content view. The content view
 * is obtained from whatever is inside of the tags of the view via its XML declaration
 */
public class MultiStateView extends FrameLayout {

    public static final int DEFAULT_ANIMATION_DURATION = 200;

    public static final int VIEW_STATE_UNKNOWN = -1;
    public static final int VIEW_STATE_CONTENT = 0;
    public static final int VIEW_STATE_ERROR = 1;
    public static final int VIEW_STATE_EMPTY = 2;
    public static final int VIEW_STATE_PROGRESS = 3;
    public static final int VIEW_STATE_BLOCKING_PROGRESS = 4;
    public static final int VIEW_STATE_NO_CONNECTION = 5;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            VIEW_STATE_UNKNOWN,
            VIEW_STATE_CONTENT,
            VIEW_STATE_ERROR,
            VIEW_STATE_EMPTY,
            VIEW_STATE_PROGRESS,
            VIEW_STATE_BLOCKING_PROGRESS,
            VIEW_STATE_NO_CONNECTION
    })
    public @interface ViewState {
    }

    public static final int ANIMATION_DISABLED = -1;
    public static final int ANIMATION_FADE = 0;
    public static final int ANIMATION_FADE_SCALE = 1;
    private static final int ANIMATION_CUSTOM = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            ANIMATION_DISABLED,
            ANIMATION_FADE,
            ANIMATION_FADE_SCALE,
            ANIMATION_CUSTOM
    })
    public @interface StateAnimation {
    }

    private LayoutInflater mInflater;
    private View mContentView;
    private View mProgressView;
    private View mBlockingProgressView;
    private View mErrorView;
    private View mEmptyView;
    private View mNoConnectionView;

    private int mActionButtonResId;

    @ViewState
    private int mViewState = VIEW_STATE_CONTENT;
    @StateAnimation
    private int mStateAnimation = ANIMATION_DISABLED;

    private int mLayoutHideVisibilityMode = INVISIBLE;

    private ViewAnimProvider mCustomAnimation;

    private OnContentShowAnimationListener mOnContentShowAnimationListener;
    private OnActionButtonClickListener mOnActionButtonClickListener;

    public MultiStateView(Context context) {
        this(context, null);
    }

    public MultiStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MultiStateView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MultiStateView);
        mInflater = LayoutInflater.from(getContext());

        mActionButtonResId = a.getResourceId(R.styleable.MultiStateView_msv_actionButtonResId, -1);

        int emptyViewResId = a.getResourceId(R.styleable.MultiStateView_msv_emptyView, -1);
        if (emptyViewResId > -1) {
            mEmptyView = mInflater.inflate(emptyViewResId, this, false);
            initOnActionButtonClickListener(mEmptyView);
            addView(mEmptyView, mEmptyView.getLayoutParams());
        }

        int errorViewResId = a.getResourceId(R.styleable.MultiStateView_msv_errorView, -1);
        if (errorViewResId > -1) {
            mErrorView = mInflater.inflate(errorViewResId, this, false);
            initOnActionButtonClickListener(mErrorView);
            addView(mErrorView, mErrorView.getLayoutParams());
        }

        int noConnectionViewResId = a.getResourceId(R.styleable.MultiStateView_msv_noConnectionView, -1);
        if (noConnectionViewResId > -1) {
            mNoConnectionView = mInflater.inflate(noConnectionViewResId, this, false);
            initOnActionButtonClickListener(mNoConnectionView);
            addView(mNoConnectionView, mNoConnectionView.getLayoutParams());
        }

        int progressViewResId = a.getResourceId(R.styleable.MultiStateView_msv_progressView, -1);
        if (progressViewResId > -1) {
            mProgressView = mInflater.inflate(progressViewResId, this, false);
            addView(mProgressView, mProgressView.getLayoutParams());
        } else if (a.getBoolean(R.styleable.MultiStateView_msv_useDefaultLoadingView, false)) {
            mProgressView = mInflater.inflate(R.layout.default_loading_view, this, false);
            addView(mProgressView);
        }

        int blockingProgressViewResId = a.getResourceId(R.styleable.MultiStateView_msv_blockingProgressView, -1);
        if (blockingProgressViewResId > -1) {
            mBlockingProgressView = mInflater.inflate(blockingProgressViewResId, this, false);
            addView(mBlockingProgressView, mBlockingProgressView.getLayoutParams());
        } else if (a.getBoolean(R.styleable.MultiStateView_msv_useDefaultLoadingView, false)) {
            mBlockingProgressView = mInflater.inflate(R.layout.default_loading_view, this, false);
            addView(mBlockingProgressView);
        }

        mLayoutHideVisibilityMode = a.getInt(R.styleable.MultiStateView_msv_layoutHideVisibilityMode, INVISIBLE);

        int stateAnimation = a.getInt(R.styleable.MultiStateView_msv_animateViewChanges, -1);
        switch (stateAnimation) {
            case ANIMATION_FADE:
                mStateAnimation = ANIMATION_FADE;
                break;
            case ANIMATION_FADE_SCALE:
                mStateAnimation = ANIMATION_FADE_SCALE;
                break;
            case ANIMATION_DISABLED:
            default:
                mStateAnimation = ANIMATION_DISABLED;
        }

        a.recycle();
    }

    /**
     * Sets onActionClickListener event for views (except loading and content views)
     * that has child with action button id
     */
    private void initOnActionButtonClickListener(@NonNull View view) {
        if (mActionButtonResId == -1) {
            return;
        }

        View actionView = view.findViewById(mActionButtonResId);
        if (actionView != null) {
            actionView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnActionButtonClickListener != null) {
                        mOnActionButtonClickListener.onClick();
                    }
                }
            });
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mContentView == null) {
            throw new IllegalArgumentException("Content view is not defined");
        }
        setView(VIEW_STATE_UNKNOWN);
    }

    /**
     * All of the addView methods have been overridden so that it can obtain the content view via XML
     * It is NOT recommended to add views into MultiStateView via the addView methods, but rather use
     * any of the setViewForState methods to set views for their given ViewState accordingly
     */
    @Override
    public void addView(View child) {
        if (isValidContentView(child)) {
            mContentView = child;
            super.addView(child, 0);
        } else {
            super.addView(child);
        }
    }

    @Override
    public void addView(View child, int index) {
        if (isValidContentView(child)) {
            mContentView = child;
            super.addView(child, 0);
        } else {
            super.addView(child, index);
        }
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (isValidContentView(child)) {
            mContentView = child;
            super.addView(child, 0, params);
        } else {
            super.addView(child, index, params);
        }
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (isValidContentView(child)) {
            mContentView = child;
            super.addView(child, 0, params);
        } else {
            super.addView(child, params);
        }
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params) {
        if (isValidContentView(child)) {
            mContentView = child;
            return super.addViewInLayout(child, 0, params);
        } else {
            return super.addViewInLayout(child, index, params);
        }
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params, boolean preventRequestLayout) {
        if (isValidContentView(child)) {
            mContentView = child;
            return super.addViewInLayout(child, 0, params, preventRequestLayout);
        } else {
            return super.addViewInLayout(child, index, params, preventRequestLayout);
        }
    }

    /**
     * Returns the {@link View} associated with the {@link ViewState}
     *
     * @param state The {@link ViewState} with to return the view
     *              for
     * @return The {@link View} associated with the {@link ViewState},
     * null if no view is present
     */
    @SuppressLint("SwitchIntDef")
    @Nullable
    public View getView(@ViewState int state) {
        switch (state) {
            case VIEW_STATE_PROGRESS:
                return mProgressView;
            case VIEW_STATE_BLOCKING_PROGRESS:
                return mBlockingProgressView;
            case VIEW_STATE_CONTENT:
                return mContentView;
            case VIEW_STATE_EMPTY:
                return mEmptyView;
            case VIEW_STATE_ERROR:
                return mErrorView;
            case VIEW_STATE_NO_CONNECTION:
                return mNoConnectionView;
            default:
                return null;
        }
    }

    /**
     * Returns the current {@link ViewState}
     */
    @ViewState
    public int getViewState() {
        return mViewState;
    }

    /**
     * Sets the current {@link ViewState}
     *
     * @param state The {@link ViewState} to set {@link MultiStateView}
     */
    public void setViewState(@ViewState int state) {
        setViewState(state, true);
    }

    /**
     * Sets the current {@link ViewState}
     *
     * @param state   The {@link ViewState} to set {@link MultiStateView}
     * @param animate Defines if layout will be changed with animation or not
     */
    public void setViewState(@ViewState int state, boolean animate) {
        if (state != mViewState) {
            int previous = mViewState;
            mViewState = state;
            setView(previous, animate);
        }
    }

    /**
     * Shows the {@link View} based on the {@link ViewState}
     */
    @SuppressLint("SwitchIntDef")
    private void setView(@ViewState int previousState) {
        setView(previousState, true);
    }

    /**
     * Shows the {@link View} based on the {@link ViewState}
     */
    private void setView(@ViewState int previousState, boolean animate) {
        if (mStateAnimation != ANIMATION_DISABLED && animate) {
            View previousView = getView(previousState);
            if (mContentView != previousView) {
                mContentView.setVisibility(mLayoutHideVisibilityMode);
            }
            if (mErrorView != null && mErrorView != previousView) {
                mErrorView.setVisibility(mLayoutHideVisibilityMode);
            }
            if (mEmptyView != null && mEmptyView != previousView) {
                mEmptyView.setVisibility(mLayoutHideVisibilityMode);
            }
            if (mNoConnectionView != null && mNoConnectionView != previousView) {
                mNoConnectionView.setVisibility(mLayoutHideVisibilityMode);
            }
            if (mProgressView != null && mProgressView != previousView) {
                mProgressView.setVisibility(mLayoutHideVisibilityMode);
            }
            if (mBlockingProgressView != null && mBlockingProgressView != previousView) {
                mBlockingProgressView.setVisibility(mLayoutHideVisibilityMode);
            }
            animateLayoutChange(previousView);
        } else {
            mContentView.setVisibility(mLayoutHideVisibilityMode);

            if (mErrorView != null) mErrorView.setVisibility(mLayoutHideVisibilityMode);
            if (mEmptyView != null) mEmptyView.setVisibility(mLayoutHideVisibilityMode);
            if (mNoConnectionView != null)
                mNoConnectionView.setVisibility(mLayoutHideVisibilityMode);
            if (mProgressView != null) mProgressView.setVisibility(mLayoutHideVisibilityMode);
            if (mBlockingProgressView != null)
                mBlockingProgressView.setVisibility(mLayoutHideVisibilityMode);

            View newView = getView(mViewState);
            if (newView != null) {
                //If blocking progress show content behind it
                if (mViewState == VIEW_STATE_BLOCKING_PROGRESS) {
                    showContentView();
                }
                newView.setVisibility(VISIBLE);
            }
        }
    }

    /**
     * Checks if the given {@link View} is valid for the Content View
     *
     * @param view The {@link View} to check
     */
    private boolean isValidContentView(View view) {
        return !(mContentView != null && mContentView != view)
                && view != mProgressView
                && view != mBlockingProgressView
                && view != mErrorView
                && view != mEmptyView
                && view != mNoConnectionView;
    }

    /**
     * Sets the view for the given view state
     *
     * @param view          The {@link View} to use
     * @param state         The {@link ViewState}to set
     * @param switchToState If the {@link ViewState} should be
     *                      switched
     *                      to
     */
    public void setViewForState(View view, @ViewState int state, boolean switchToState) {
        if (state == VIEW_STATE_PROGRESS) {
            if (mProgressView != null) removeView(mProgressView);
            mProgressView = view;
            addView(mProgressView);
        } else if (state == VIEW_STATE_BLOCKING_PROGRESS) {
            if (mBlockingProgressView != null) removeView(mBlockingProgressView);
            mBlockingProgressView = view;
            addView(mBlockingProgressView);
        } else if (state == VIEW_STATE_EMPTY) {
            if (mEmptyView != null) removeView(mEmptyView);
            mEmptyView = view;
            initOnActionButtonClickListener(view);
            addView(mEmptyView);
        } else if (state == VIEW_STATE_ERROR) {
            if (mErrorView != null) removeView(mErrorView);
            mErrorView = view;
            initOnActionButtonClickListener(view);
            addView(mErrorView);
        } else if (state == VIEW_STATE_CONTENT) {
            if (mContentView != null) removeView(mContentView);
            mContentView = view;
            addView(mContentView);
        } else if (state == VIEW_STATE_NO_CONNECTION) {
            if (mNoConnectionView != null) removeView(mNoConnectionView);
            mNoConnectionView = view;
            initOnActionButtonClickListener(view);
            addView(mNoConnectionView);
        }

        setView(VIEW_STATE_UNKNOWN);
        if (switchToState) setViewState(state);
    }

    /**
     * Sets the {@link View} for the given {@link ViewState}
     *
     * @param view  The {@link View} to use
     * @param state The {@link ViewState} to set
     */
    public void setViewForState(View view, @ViewState int state) {
        setViewForState(view, state, false);
    }

    /**
     * Sets the {@link View} for the given {@link ViewState}
     *
     * @param layoutRes     Layout resource id
     * @param state         The {@link ViewState} to set
     * @param switchToState If the {@link ViewState} should be
     *                      switched
     *                      to
     */
    public void setViewForState(@LayoutRes int layoutRes, @ViewState int state, boolean switchToState) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(getContext());
        }
        View view = mInflater.inflate(layoutRes, this, false);
        setViewForState(view, state, switchToState);
    }

    /**
     * Sets the {@link View} for the given {@link ViewState}
     *
     * @param layoutRes Layout resource id
     * @param state     The {@link View} state to set
     */
    public void setViewForState(@LayoutRes int layoutRes, @ViewState int state) {
        setViewForState(layoutRes, state, false);
    }

    /**
     * Sets custom animation for changing between {@link ViewState}
     */
    public void setAnimation(@Nullable ViewAnimProvider animation) {
        if (animation == null) {
            mStateAnimation = ANIMATION_DISABLED;
        } else {
            mStateAnimation = ANIMATION_CUSTOM;
        }
        mCustomAnimation = animation;
    }

    /**
     * Sets animation for changing between {@link ViewState}
     */
    public void setAnimation(@StateAnimation int stateAnimation) {
        mStateAnimation = stateAnimation;
    }

    /**
     * Sets onContentShowAnimation listener to allow display enter animation for content after
     */
    public void setOnContentShowAnimationListener(OnContentShowAnimationListener listener) {
        mOnContentShowAnimationListener = listener;
    }

    /**
     * Sets setOnActionButtonClickListener listener to manage on action clicks
     * {@link ViewState} show animation end
     */
    public void setOnActionButtonClickListener(OnActionButtonClickListener listener) {
        mOnActionButtonClickListener = listener;
    }

    /**
     * Animates the layout changes between {@link ViewState}
     *
     * @param previousView The view that it was currently on
     */
    @SuppressLint("SwitchIntDef")
    private void animateLayoutChange(@Nullable final View previousView) {
        if (previousView == null) {
            View view = getView(mViewState);

            //If blocking progress show content behind it
            if (mViewState == VIEW_STATE_BLOCKING_PROGRESS) {
                showContentView();
            }

            if (view != null) {
                view.setVisibility(View.VISIBLE);
            }
            return;
        }

        previousView.setVisibility(View.VISIBLE);

        final ViewAnimProvider animProvider;
        switch (mStateAnimation) {
            case ANIMATION_CUSTOM:
                animProvider = mCustomAnimation;
                break;
            case ANIMATION_FADE_SCALE:
                animProvider = new FadeScaleViewAnimProvider();
                break;
            case ANIMATION_FADE:
            default:
                animProvider = new FadeViewAnimProvider();
        }

        Animation hideAnimation = animProvider.hideAnimation();
        hideAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //no-op
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                previousView.setVisibility(mLayoutHideVisibilityMode);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //no-op
            }
        });
        hideAnimation.setFillAfter(false);
        previousView.startAnimation(hideAnimation);

        View newView = getView(mViewState);
        if (newView != null) {
            //If blocking progress show content behind it
            if (mViewState == VIEW_STATE_BLOCKING_PROGRESS) {
                showContentView();
            }
            newView.setVisibility(View.VISIBLE);
            Animation showAnimation = animProvider.showAnimation();
            if (mOnContentShowAnimationListener != null) {
                showAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        mOnContentShowAnimationListener.onContentShowAnimation();
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        //no-op
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        //no-op
                    }
                });
            }
            showAnimation.setFillAfter(false);
            newView.startAnimation(showAnimation);
        }
    }

    private void showContentView() {
        if (mContentView == null) {
            throw new IllegalStateException("No content view!");
        }

        mContentView.setVisibility(VISIBLE);
    }

    public interface OnActionButtonClickListener {
        void onClick();
    }
}
