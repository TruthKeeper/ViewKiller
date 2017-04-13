package com.tk.viewkiller.partical;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.view.animation.Interpolator;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/13
 *     desc   : 粒子变换器
 * </pre>
 */
public interface ParticalTransformer {
    void process(@NonNull Partical[][] particals, @FloatRange(from = 0f, to = 1f) float animValue);

    Interpolator buildInterpolator();

    int buildDuring();

    void display(Canvas canvas, Paint paint, Partical[][] particals, @FloatRange(from = 0f, to = 1f) float animValue, ParticalView view);
}
