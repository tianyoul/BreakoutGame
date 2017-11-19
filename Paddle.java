package edu.macalester.comp124.breakout;

import acm.graphics.GCompound;
import acm.graphics.GRect;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * This is the class that creates a Paddle.
 * Created by Tianyou on 16/3/13.
 */
public class Paddle extends GCompound {
    private double width;
    private double height;
    private double x;
    private double y;
    private GRect paddle;

    /**
     * The constructor of the paddle.
     *
     * @param x        The x coordinate of the up left point of the paddle.
     * @param y        The y coordinate of the up left point of the paddle.
     * @param width      The width of the paddle.
     * @param height       The height of the paddle.
     */


    public Paddle(double x, double y, double width, double height) {
        this.width = width;
        this.x=x;
        this.y=y;
        paddle = new GRect(x, y, width, height);
        paddle.setFilled(true);
        paddle.setFillColor(Color.GRAY);
        add(paddle);
    }

    /**
     * Set the x of the paddle to x.
     * @param x    The target position.
     */

    public void setX(double x) {
        this.x=x-width/2;
        paddle.setLocation(x-width/2,paddle.getY());
    }

    /**
     * @return The x of the paddle.
     */

    public double getPaddleX() {
        return x;
    }

    /**
     * @return The y of the paddle.
     */
    public double getPaddleY() {
        return y;
    }
}
