package com.attlib.attpromodialogx;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.attlib.attpromodialogx.R;

public class IndicatorView extends View {
    private static final long DEFAULT_ANIMATE_DURATION = 200;
    private static final int DEFAULT_RADIUS_SELECTED = 20;
    private static final int DEFAULT_RADIUS_UNSELECTED = 15;
    private static final int DEFAULT_DISTANCE = 40;

    private ViewPager viewPager;

    private Dot[] dots;

    private long animateDuration = DEFAULT_ANIMATE_DURATION;
    private int radiusSelected = DEFAULT_RADIUS_SELECTED;
    private int radiusUnselected = DEFAULT_RADIUS_UNSELECTED;
    private int distance = DEFAULT_DISTANCE;

    private int colorSelected;
    private int colorUnselected;
    private int currentPosition;
    private int beforePosition;

    private ValueAnimator animatorZoomIn;
    private ValueAnimator animatorZoomOut;

    public IndicatorView(Context context) {
        super(context);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IndicatorView);

        this.radiusSelected = typedArray.getDimensionPixelSize(R.styleable.IndicatorView_radius_selected, DEFAULT_RADIUS_SELECTED);
        this.radiusUnselected = typedArray.getDimensionPixelSize(R.styleable.IndicatorView_radius_unselected, DEFAULT_RADIUS_UNSELECTED);
        this.distance = typedArray.getDimensionPixelSize(R.styleable.IndicatorView_distance, DEFAULT_DISTANCE);
        this.colorSelected = typedArray.getColor(R.styleable.IndicatorView_color_selected, Color.parseColor("#ffffff"));
        this.colorUnselected = typedArray.getColor(R.styleable.IndicatorView_color_unselected, Color.parseColor("#ffffff"));

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int desiredHeight = 2 * radiusSelected;

        int width;
        int height;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = widthSize;
        } else {
            width = 0;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        updateDots();
    }

    private void updateDots() {
        if (dots != null) {
            float yCenter = getHeight() / 2;
            int d = distance + 2 * radiusUnselected;
            float firstXCenter = (getWidth() / 2) - ((dots.length - 1) * d / 2);

            for (int i = 0; i < dots.length; i++) {
                dots[i].setCenter(i == 0 ? firstXCenter : firstXCenter + d * i, yCenter);
                dots[i].setCurrentRadius(i == currentPosition ? radiusSelected : radiusUnselected);
                dots[i].setColor(i == currentPosition ? colorSelected : colorUnselected);
                dots[i].setAlpha(i == currentPosition ? 255 : radiusUnselected * 255 / radiusSelected);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (dots != null) {
            for (Dot dot : dots) {
                dot.draw(canvas);
            }
        }
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(listener);
        if (viewPager.getAdapter() != null) {
            initDot(viewPager.getAdapter().getCount());
            listener.onPageSelected(viewPager.getCurrentItem());
        } else {
            Log.e("MYLOG", "ViewPager Adapter is NULL");
        }

    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setAnimateDuration(long duration) {
        this.animateDuration = duration;
    }

    public void setRadiusSelected(int radius) {
        this.radiusSelected = radius;
    }

    public void setRadiusUnselected(int radius) {
        this.radiusUnselected = radius;
    }

    public void setDistanceDot(int distance) {
        this.distance = distance;
    }

    private void initDot(int count) {
        dots = new Dot[count];
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new Dot();
        }
    }

    private void changeNewRadius(int positionPerform, int newRadius) {
        if (dots[positionPerform].getCurrentRadius() != newRadius) {
            dots[positionPerform].setCurrentRadius(newRadius);
            dots[positionPerform].setAlpha(newRadius * 255 / radiusSelected);
            invalidate();
        }
    }

    public ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int position) {
            if (dots.length < 2) return;

            beforePosition = currentPosition;
            currentPosition = position;

            if (beforePosition == currentPosition) {
                beforePosition = currentPosition + 1;
            }

            Log.d("MYLOG", "beforePosition: " + beforePosition);
            Log.d("MYLOG", "currentPosition: " + currentPosition);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(animateDuration);

            animatorZoomIn = ValueAnimator.ofInt(radiusUnselected, radiusSelected);
            animatorZoomIn.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                int positionPerform = currentPosition;

                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int newRadius = (int) valueAnimator.getAnimatedValue();
                    changeNewRadius(positionPerform, newRadius);
                }
            });

            animatorZoomOut = ValueAnimator.ofInt(radiusSelected, radiusUnselected);
            animatorZoomOut.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                int positionPerform = beforePosition;

                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int newRadius = (int) valueAnimator.getAnimatedValue();
                    changeNewRadius(positionPerform, newRadius);
                }
            });

            animatorSet.play(animatorZoomIn).with(animatorZoomOut);
            animatorSet.start();
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    public class Dot {
        private Paint paint;

        private PointF center;

        private int currentRadius;

        public Dot() {
            paint = new Paint();
            paint.setAntiAlias(true);
            center = new PointF();
        }

        public void setColor(int color) {
            paint.setColor(color);
        }

        public void setAlpha(int alpha) {
            paint.setAlpha(alpha);
        }

        public void setCenter(float x, float y) {
            center.set(x, y);
        }

        public int getCurrentRadius() {
            return currentRadius;
        }

        public void setCurrentRadius(int radius) {
            this.currentRadius = radius;
        }

        public void draw(Canvas canvas) {
            canvas.drawCircle(center.x, center.y, currentRadius, paint);
        }
    }
}
