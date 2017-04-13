package com.tk.viewkiller.partical;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

import java.util.Random;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/13
 *     desc   : xxxx描述
 * </pre>
 */
public class RandomTransformer implements ParticalTransformer {
    public int[][][] randoms;
    public static int R = 4;

    @Override
    public void process(@NonNull Partical[][] particals, @FloatRange(from = 0f, to = 1f) float animValue) {
        if (randoms == null) {
            initRandoms(particals);
        }
        for (int i = 0, iLength = particals.length; i < iLength; i++) {
            for (int j = 0, jLength = particals[i].length; j < jLength; j++) {
                if (particals[i][j] != null) {
                    particals[i][j].offset(randoms[i][j][0], randoms[i][j][1]);
                }
            }
        }
    }

    @Override
    public Interpolator buildInterpolator() {
        return new AccelerateInterpolator();
    }

    @Override
    public int buildDuring() {
        return 1000;
    }

    @Override
    public void display(Canvas canvas, Paint paint, Partical[][] particals,
                        @FloatRange(from = 0f, to = 1f) float animValue, ParticalView view) {
        for (Partical[] partical : particals) {
            for (Partical p : partical) {
                if (p != null) {

                    paint.setColor(p.getColor());
                    paint.setAlpha((int) (255 * (1 - animValue)));
                    canvas.drawCircle(p.getCenterX(), p.getCenterY(), p.getRadius() * (1 - animValue), paint);
                }
            }
        }
    }

    private void initRandoms(Partical[][] particals) {
        randoms = new int[particals.length][particals[0].length][2];
        int x;
        for (int i = 0, iLength = randoms.length; i < iLength; i++) {
            for (int j = 0, jLength = randoms[i].length; j < jLength; j++) {
                x = new Random().nextInt(R);
                randoms[i][j][0] = x * off();
                randoms[i][j][1] = (int) (Math.sqrt(Math.pow(R, 2) - Math.pow(x, 2)) * off());
            }
        }
    }

    private int off() {
        return new Random().nextInt(2) > 0 ? 1 : -1;
    }
}
