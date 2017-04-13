package com.tk.viewkiller.partical;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/12
 *     desc   : 粒子视图
 * </pre>
 */
public class ParticalView extends View {
    private Partical[][] particals;
    private Paint paint = new Paint();
    private ValueAnimator valueAnimator;
    private float animValue;
    private ParticalTransformer transformer;

    public ParticalView(Context context) {
        super(context);
        init();
    }

    public ParticalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
    }

    public void generate(@NonNull final Partical[][] particals, @NonNull final ParticalTransformer transformer) {
        this.particals = particals;
        this.transformer = transformer;
        valueAnimator = ValueAnimator.ofFloat(0f, 1f)
                .setDuration(transformer.buildDuring());
        valueAnimator.setInterpolator(transformer.buildInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animValue = (float) animation.getAnimatedValue();
                transformer.process(particals, animValue);
                invalidate();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                recycle();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                recycle();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void recycle() {
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
        }
    }

    /**
     * 执行爆破动画
     */
    public void explode() {
        explode(null);
    }

    /**
     * @param listener
     */
    public void explode(@Nullable Animator.AnimatorListener listener) {
        if (valueAnimator.isRunning()) {
            return;
        }
        valueAnimator.addListener(listener);
        valueAnimator.start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        transformer.display(canvas, paint, particals, animValue, this);

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }
    }
}
