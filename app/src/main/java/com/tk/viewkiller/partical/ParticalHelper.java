package com.tk.viewkiller.partical;

import android.animation.Animator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/12
 *     desc   : xxxx描述
 * </pre>
 */
public class ParticalHelper {
    /**
     * 粒子大小
     */
    public static final int PARTICAL_SIZE = 24;

    public static Bitmap copy(@NonNull View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

    /**
     * 生成一个粒子
     *
     * @param color
     * @param centerX
     * @param centerY
     * @param radius
     * @return
     */
    private static Partical generatePartical(int color, int centerX, int centerY, int radius) {
        Partical particals = new Partical();
        particals.setColor(color);
        particals.setCenterX(centerX);
        particals.setCenterY(centerY);
        particals.setRadius(radius);
        return particals;
    }

    /**
     * 生成临摹粒子集合
     *
     * @param bitmap 临摹图
     * @param rect   临摹区域
     * @return
     */
    public static Partical[][] generateParticals(Bitmap bitmap, Rect rect, int particalSize) {
        int column = rect.width() / particalSize;
        int row = rect.height() / particalSize;
        Partical[][] particals = new Partical[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                int x = particalSize * j + particalSize / 2;
                int y = particalSize * i + particalSize / 2;
                int centerX = rect.left + particalSize * j + particalSize / 2;
                int centerY = rect.top + particalSize * i + particalSize / 2;
                int color = bitmap.getPixel(x, y);
                if (color != Color.TRANSPARENT) {
                    //无色不创建粒子
                    particals[i][j] = generatePartical(color, centerX, centerY, particalSize / 2);
                }
            }
        }
        return particals;
    }

    public static void with(@NonNull Activity activity, @NonNull final View view, final boolean kill, @Nullable final Animator.AnimatorListener listener) {
        Bitmap bitmap = copy(view);
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        Partical[][] particals = generateParticals(bitmap, rect, PARTICAL_SIZE);
        ParticalView particalView = new ParticalView(view.getContext());
        particalView.generate(particals, new RandomTransformer());

        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        decorView.addView(particalView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setVisibility(View.INVISIBLE);
        particalView.explode(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (listener != null) {
                    listener.onAnimationStart(animation);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (kill) {
                    removeSelf(view);
                }
                if (listener != null) {
                    listener.onAnimationEnd(animation);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (listener != null) {
                    listener.onAnimationCancel(animation);
                }
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                if (listener != null) {
                    listener.onAnimationRepeat(animation);
                }
            }
        });
    }

    /**
     * 移除自己
     *
     * @param view
     */
    public static void removeSelf(@NonNull View view) {
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }
}
