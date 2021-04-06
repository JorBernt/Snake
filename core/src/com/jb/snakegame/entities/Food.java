package com.jb.snakegame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import javax.swing.*;
import java.util.List;
import java.util.Random;

public class Food extends Entity{
    private Random random;
    private float newX, newY;
    public Food(float x, float y, int size) {
        super(x, y);
        texture = new Texture(Gdx.files.internal("food.png"));
        rect = new Rectangle();
        rect.width = size;
        rect.height = size;
        random = new Random();
    }
    @Override
    public void update(float delta) {
        pos.x = newX;
        pos.y = newY;
    }

    public void spawn(List<SnakeBody> snakeBodyList) {
        out:while(true) {
            newX = 25*random.nextInt(20);
            newY = 25*random.nextInt(20);
            for(SnakeBody snakeBody : snakeBodyList) {
                if(newX == snakeBody.getX() && newY == snakeBody.getY()) continue out;
            }
            return;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, this.pos.x, this.pos.y, rect.getWidth(), rect.getHeight());
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
