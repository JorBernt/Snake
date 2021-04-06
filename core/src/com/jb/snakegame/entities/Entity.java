package com.jb.snakegame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    protected Vector2 pos;
    protected Texture texture;
    protected Rectangle rect;

    public Entity(float x, float y) {
        pos = new Vector2();
        pos.x = x;
        pos.y = y;
    }

    public abstract void update(float delta);

    public abstract void render(SpriteBatch batch);

    public abstract void dispose();

    public Vector2 getPos() {
        return pos;
    }
    public float getY() {
        return pos.y;
    }

    public float getX() {
        return pos.x;
    }
    

}
