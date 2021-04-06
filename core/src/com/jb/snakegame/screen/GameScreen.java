package com.jb.snakegame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.jb.snakegame.SnakeGame;
import com.jb.snakegame.entities.Food;
import com.jb.snakegame.entities.Snake;

public class GameScreen implements Screen {
    private static enum GameState{
        MENU,
        RUNNING,
        END;
    }
    private GameState State;

    private static final int START_LENGTH = 5;

    private SnakeGame snakeGame;
    private Snake snake;
    private Food food;
    private Texture backGround;
    private BitmapFont font;
    private int score;

    public GameScreen(SnakeGame snakeGame) {
        this.snakeGame = snakeGame;
        snake = new Snake(snakeGame.batch, START_LENGTH, new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2));
        food = new Food(0,0,25);
        food.spawn(snake.getSnakeBodyList());
        food.update(0);
        score = 0;
        backGround = new Texture(Gdx.files.internal("background.png"));
        font = new BitmapFont();
        State = GameState.MENU;
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        switch (State) {
            case MENU: {
                Gdx.gl.glClearColor(0.2f, 0.5f, 0.4f, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                snakeGame.batch.begin();
                snakeGame.batch.draw(backGround, 0,0);
                font.draw(snakeGame.batch, "Press any key to start!", 180, 250);

                if(Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {

                    State = GameState.RUNNING;
                }
                snakeGame.batch.end();
                break;
            }

        case RUNNING: {
                Gdx.gl.glClearColor(0.2f, 0.5f, 0.4f, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                snakeGame.batch.begin();
                snakeGame.batch.draw(backGround, 0,0);
                snake.update(delta);
                if(collidingWithFood()) {
                    snake.grow();
                    food.spawn(snake.getSnakeBodyList());
                    score+=100;
                }
                if(!snake.isDead()) {
                    snake.render();
                }
                else reset();

                food.update(delta);
                food.render(snakeGame.batch);
                snakeGame.batch.end();

                if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                    snake.setDir(Input.Keys.RIGHT);
                }
                if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                    snake.setDir(Input.Keys.LEFT);
                }
                if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                    snake.setDir(Input.Keys.DOWN);
                }
                if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                    snake.setDir(Input.Keys.UP);
                }
                break;
            }

            case END: {
                Gdx.gl.glClearColor(0.2f, 0.5f, 0.4f, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                snakeGame.batch.begin();
                snakeGame.batch.draw(backGround, 0,0);
                font.draw(snakeGame.batch, "You scored " + score +"! \n Press any key to play again", 180, 250);
                if(Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
                    State = GameState.MENU;
                }
                snakeGame.batch.end();
                break;
            }
        }

    }

    private boolean collidingWithFood() {

        return snake.getHeadPos().equals(food.getPos());
    }

    private void reset() {
        snake = new Snake(snakeGame.batch, START_LENGTH, new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2));
        food = new Food(0,0,25);
        food.spawn(snake.getSnakeBodyList());
        State = GameState.END;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        snake.dispose();
        food.dispose();
        snakeGame.batch.dispose();
    }
}
