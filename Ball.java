package edu.macalester.comp124.breakout;

import acm.graphics.GDimension;
import acm.graphics.GOval;

import java.awt.*;

/**
 * This is the class that creates a ball for the breakout program.
 * Created by Tianyou on 16/3/11.
 */
public class Ball extends GOval {
    private double size ;
    private Color color;


    /**
     * The constructor of ball.
     *
     * @param color     The color of the ball
     * @param size      The diameter of the ball
     */

    public Ball(Color color, double size) {
        super(size,size);
        super.setColor(color);
        super.setFilled(true);
        super.setFillColor(color);
    }



}
