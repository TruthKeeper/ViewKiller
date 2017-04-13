package com.tk.viewkiller.partical;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/12
 *     desc   : 粒子实体
 * </pre>
 */
public class Partical {
    private int centerX;
    private int centerY;
    private int color;
    private int radius;

    public void offset(int dx, int dy) {
        centerX += dx;
        centerY += dy;
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }


}
