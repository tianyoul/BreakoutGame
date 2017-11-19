package edu.macalester.comp124.breakout;

import acm.graphics.*;
import acm.program.GraphicsProgram;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Main GraphicsProgram for the breakout game described
 * in exercise 10.10 int the Roberts Textbook.
 *
 */

public class BreakoutProgram extends GraphicsProgram {

    private static int numTurns; // number of turns left in game
    private static int numBricks; // number of bricks left to remove

    private static final double BALL_SIZE = 40;
    private static final int PAUSE_TIME = 4;
    private static double PADDLE_WIDTH=150;
    private static double PADDLE_HEIGHT=30;
    private static double BRICK_WIDTH=100;
    private static double BRICK_HEIGHT=50;
    private static int CANVAS_WIDTH=1000;
    private static int CANVAS_HEIGHT=800;
    private static double PADDLE_OFFCET=100;
    private static double BRICKWALL_OFFCET=100;
    private double dx=0;
    private double dy=0;


    private Ball ball;
    private Paddle paddle;
    private BrickWall wall;
    private ArrayList<GRect> BrickList;
    private GLabel label;

    public void init(){
        setSize(CANVAS_WIDTH,CANVAS_HEIGHT);
        numTurns = 3;
        numBricks = 56;
        label = new GLabel("Chances left: "+numTurns);
        label.setFont("SanSarif-30");
        add(label,10,30);
        BrickList = new ArrayList<>();
        wall = new BrickWall(BRICKWALL_OFFCET,80,BRICK_WIDTH,BRICK_HEIGHT,7,8,BrickList);
        add(wall);
        wall.setRowColor(1,Color.RED);
        wall.setRowColor(2,Color.ORANGE);
        wall.setRowColor(3,Color.YELLOW);
        wall.setRowColor(4,Color.GREEN);
        wall.setRowColor(5,Color.CYAN);
        wall.setRowColor(6,Color.BLUE);
        wall.setRowColor(7,Color.MAGENTA);
        paddle = new Paddle(getWidth()/2,getHeight()-PADDLE_OFFCET,PADDLE_WIDTH,PADDLE_HEIGHT);
        add(paddle);
        ball = new Ball(Color.BLACK,BALL_SIZE);
        add(ball,getWidth()/2+PADDLE_WIDTH/2-BALL_SIZE/2,getHeight()-PADDLE_OFFCET-BALL_SIZE);
        addMouseListeners();
    }


    public void run(){
        while(true){
            ball.move(dx,dy);
            bounceBall();
            bouncePaddle();
            bounceBrick();
            if(checkWin()){
                break;
            }
            if(!checkBall()){
                if(!checkLose()){
                    remove(ball);
                    pause(1000);
                    add(ball,getWidth()/2+PADDLE_WIDTH/2-BALL_SIZE/2,paddle.getPaddleY()-BALL_SIZE);
                    dx=0;
                    dy=0;
                } else {
                    break;
                }
            }
            pause(PAUSE_TIME);

        }

    }


    /**
     * If the ball intersects with the paddle, its direction changes correspondingly.
     */

    public void bouncePaddle(){
        if(ball.getY()>=paddle.getPaddleY()-BALL_SIZE&&ball.getY()<=paddle.getPaddleY()-BALL_SIZE+2&&ball.getX()>=paddle.getPaddleX()-BALL_SIZE/2&&ball.getX()<=paddle.getPaddleX()-BALL_SIZE/2+PADDLE_WIDTH){
            dx = (ball.getX()+BALL_SIZE/2-paddle.getPaddleX()-PADDLE_WIDTH/2)/PADDLE_WIDTH*1.2;
            dy = -Math.sqrt(2-Math.pow(dx,2));
        } else if(ball.contains(paddle.getPaddleX(),paddle.getPaddleY())){
            double a = dx;
            dx=-dy;
            dy=-a;
        } else if(ball.contains(paddle.getPaddleX()+PADDLE_WIDTH,paddle.getPaddleY())){
            double a = dx;
            dx=dy;
            dy=a;
        } else if(paddle.contains(ball.getX(),ball.getY()+BALL_SIZE/2)) {
            dx=-dx;
        } else if(paddle.contains(ball.getX()+BALL_SIZE,ball.getY()+BALL_SIZE/2)) {
            dx=-dx;
        }
    }

    /**
     * If the ball intersects with the wall, its direction changes correspondingly.
     */

    public void bounceBall(){
        if(ball.getX()>=getWidth()-BALL_SIZE||ball.getX()<=0){
            dx=-dx;
        }

        if(ball.getY()>=getHeight()-BALL_SIZE||ball.getY()<=0){
            dy=-dy;
        }
    }

    /**
     * Move the paddle so that the middle point of the paddle is at the same place
     * as the mouse.
     */

    public void mouseMoved(MouseEvent event){
        double x = event.getPoint().getX();
        paddle.setX(x);
    }

    /**
     * Loop over all the bricks to check if the ball intersects with any of the bricks.
     * If it does, remove the brick and change the direction of the ball.
     */

    public void bounceBrick(){
        for (int i = 0; i < numBricks ; i++){
            GRect obj = BrickList.get(i);
            if(obj.contains(ball.getX()+BALL_SIZE/2,ball.getY())||obj.contains(ball.getX()+BALL_SIZE/2,ball.getY()+BALL_SIZE)){
                dy=-dy;
                removeBrick(i);
            }else if(obj.contains(ball.getX()+BALL_SIZE,ball.getY()+BALL_SIZE/2)||obj.contains(ball.getX(),ball.getY()+BALL_SIZE/2)){
                dx=-dx;
                removeBrick(i);
            }else if(ball.contains(obj.getX(),obj.getY())||ball.contains(obj.getX()+BRICK_WIDTH,obj.getY()+BRICK_HEIGHT)){
                double a = dx;
                dx=-dy;
                dy=-a;
                removeBrick(i);
            }else if(ball.contains(obj.getX()+BRICK_WIDTH,obj.getY())||ball.contains(obj.getX(),obj.getY()+BRICK_HEIGHT)){
                double a = dx;
                dx=dy;
                dy=a;
                removeBrick(i);
            }
        }

    }

    /**
     * Remove the brick from the canvas and the list and minus one from numBricks.
     */

    public void removeBrick(int index){
        wall.destoryBrick(index);
        BrickList.remove(index);
        numBricks--;
    }

    /**
     * Return false if the ball falls under the paddle and minus one from numTurns.
     * @return If the ball is still alive.
     */

    public boolean checkBall(){
        if(ball.getY()+BALL_SIZE/2<paddle.getPaddleY()+PADDLE_HEIGHT){
            return true;
        }
        numTurns--;
        label.setLabel("Chances: "+numTurns);
        return false;
    }

    /**
     * If the numTurns is 0, the player loses.
     * @return If the player loses.
     */

    public boolean checkLose(){
        if(numTurns==0){
            label.setLabel("You lost.");
            return true;
        }
        return false;
    }

    /**
     * If the numBricks is 0, the player wins.
     * @return If the player wins.
     */

    public boolean checkWin(){
        if(numBricks==0){
            label.setLabel("You win.");
            return true;
        }
        return false;
    }


}
