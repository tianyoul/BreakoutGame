package edu.macalester.comp124.breakout;

import acm.graphics.GCompound;
import acm.graphics.GRect;

import java.awt.*;
import java.util.ArrayList;

/**
 * This is the class that create a wall for the breakout program.
 * Created by Tianyou on 16/3/14.
 */
public class BrickWall extends GCompound {

    private ArrayList<GRect> BrickList;
    private int row;
    private int column;
    private double x;
    private double y;
    private double width;
    private double height;

    /**
     * The constructor of BrickWall class.
     * @param x  The x coordinate of the up left point of the whole wall.
     * @param y   The y coordinate of the up left point of the whole wall.
     * @param width   The width of a single brick.
     * @param height    The height of a single brick.
     * @param row    The number of rows of the whole wall.
     * @param column     The number of columns of the whole wall.
     * @param BrickList     A array list that contains all the bricks.
     */
    public BrickWall(double x, double y, double width, double height, int row, int column, ArrayList<GRect> BrickList) {
        this.BrickList = BrickList;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.row=row;
        this.column=column;
        for(int i = 0; i < row; i++){
            for( int j = 0 ; j < column ; j++){
                GRect rect = new  GRect(x+j*width,y+i*height,width,height);
                add(rect);
                BrickList.add(i*column+j,rect);
            }
        }
    }

    /**
     * Set color to a row of bricks.
     *
     * @param row     The number of row at which the target bricks are located
     * @param color     The color to be set to the brick.
     */


    public void setRowColor(int row , Color color){
        for(int i = (row-1)*this.column;i<row*this.column;i++){
            this.BrickList.get(i).setFilled(true);
            this.BrickList.get(i).setFillColor(color);
        }
    }

    /**
     * Remove a brick from the GCompound.
     *
     * @param index    The index of the brick to be removed.
     */

    public void destoryBrick(int index){
        remove(BrickList.get(index));
    }

}
