package com.jb.snakegame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Snake {
   private static final int TIME_TO_MOVE = 7;
   private int moveTime = TIME_TO_MOVE;


   private List<SnakeBody> snakeBodyList;
   private Queue<Integer> directions;
   private SpriteBatch batch;
   private static final int SIZE = 25;
   private static final float SPEED = 25;
   private int dir; //R=0, D=1, L=2, U=3
   private Vector2 headPos;
   private boolean dead;

   public Snake(SpriteBatch batch, int startLength, Vector2 startPos) {
       this.batch = batch;
       dir = -1;
       dead = false;
       directions = new LinkedList<>();
       snakeBodyList = new ArrayList<>();
       addStartBody(startLength, startPos);
       headPos = startPos;
   }

   private void addStartBody(int startLength, Vector2 startPos) {
       for(int i = 0; i < startLength; i++) {
           snakeBodyList.add(new SnakeBody(startPos.x-(i*SIZE), startPos.y, SIZE, SPEED, dir, i==0));
       }
   }

   public void grow() {
       SnakeBody tail = snakeBodyList.get(snakeBodyList.size()-1);
       float x = tail.getX(), y = tail.getY();
       switch (tail.getDir()) {
           case 0 : x -= SIZE;break;
           case 1 : y += SIZE;break;
           case 2 : x += SIZE;break;
           case 3 : y -= SIZE;break;
       }
       snakeBodyList.add(new SnakeBody(x, y, SIZE, SPEED, tail.getDir(), false));

   }


   public void render() {
       for(SnakeBody snakeBody : snakeBodyList) {
           snakeBody.render(batch);
       }
   }

   public void update(float delta) {
       if(dir == -1 && !directions.isEmpty()) {
           for (SnakeBody snakeBody : snakeBodyList) {
               snakeBody.setDir(0);
           }
           dir = 0;
           return;
       }
       moveTime -= delta*SPEED;
       if(moveTime == 0) {
           if(!directions.isEmpty()) {
               int n = directions.peek();
               if((dir == 0 && n != 2) || (dir == 1 && n != 3) || (dir == 2 && n != 0) || (dir == 3 && n != 1))
               dir = directions.poll();
               else directions.poll();
           }

           updateDir();


           for (SnakeBody snakeBody : snakeBodyList) {
               snakeBody.update(delta);
           }
           if(checkCollision()) {
               dead = true;
           }
           headPos = snakeBodyList.get(0).pos;
           moveTime = TIME_TO_MOVE;
       }


   }

   private boolean checkCollision() {
       Vector2 headPos = getHeadPos();

       for(int i = 1; i < snakeBodyList.size(); i++) {
           if(headPos.x == snakeBodyList.get(i).getX() &&headPos.y == snakeBodyList.get(i).getY()) return true;
       }

       if(headPos.x < 0 || headPos.x >= Gdx.graphics.getWidth() ||
               headPos.y < 0 || headPos.y >= Gdx.graphics.getHeight()) {
           return true;
       }
       return false;
   }

   private void updateDir() {
       Queue<SnakeBody> tempList = new LinkedList<>(snakeBodyList);
       int newDir = dir;
       while(!tempList.isEmpty()) {
           int tempDir = tempList.peek().getDir();
           tempList.poll().setDir(newDir);
           newDir = tempDir;

       }

   }

   public void setDir(int key) {
       switch (key) {
           case Input.Keys.RIGHT: directions.add(0);break;
           case Input.Keys.DOWN: directions.add(1);break;
           case Input.Keys.LEFT: directions.add(2);break;
           case Input.Keys.UP: directions.add(3);break;
       }
   }

   public Vector2 getHeadPos() {
       return headPos;
   }

   public boolean isDead() {
       return dead;
   }

   public void dispose() {
       for(SnakeBody snakeBody : snakeBodyList) {
           snakeBody.dispose();
       }
   }

   public List<SnakeBody> getSnakeBodyList() {
       return snakeBodyList;
   }
}
