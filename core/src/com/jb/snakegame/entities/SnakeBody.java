package com.jb.snakegame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class SnakeBody extends Entity{

    private float speed;
    private int dir;
    private boolean head;


    public SnakeBody(float x, float y, int size, float speed, int dir, boolean head) {
        super(x, y);
        this.speed = speed;
        this.dir = dir;
        if(head) {
            texture = new Texture(Gdx.files.internal("snakeHead.png"));
        }
        else texture = new Texture(Gdx.files.internal("snakeBody.png"));
        rect = new Rectangle();
        rect.height = size;
        rect.width = size;

    }

    @Override
    public void update(float delta) {
        switch (dir) {
            case 0: this.pos.x += speed;break;
            case 1: this.pos.y -= speed;break;
            case 2: this.pos.x -= speed;break;
            case 3: this.pos.y += speed;break;
        }


    }

    public void move(int dir) {

    }

    @Override
    public void render(SpriteBatch batch) {

        batch.draw(texture, pos.x, pos.y, rect.getWidth(), rect.getHeight());

    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {

        this.dir = dir;
    }

    public boolean isHead() {
        return head;
    }
}
