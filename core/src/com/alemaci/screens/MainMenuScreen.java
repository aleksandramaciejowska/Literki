package com.alemaci.screens;

import com.alemaci.LettersGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

    private static final int BUTTON_WIDTH = 280;
    private static final int BUTTON_HEIGHT = 120;
    private static final int NEWGAME_BUTTON_Y = 440;
    private static final int RULES_BUTTON_Y = 270;
    private static final int QUIT_BUTTON_Y = 100;
    private static final int TITLE_WIDTH = 630;
    private static final int TITLE_HEIGHT = 105;

    LettersGame game;

    private Texture newGameButtonActive;
    private Texture newGameButtonInactive;
    private Texture gameRulesButtonActive;
    private Texture gameRulesButtonInactive;
    private Texture quitGameButtonActive;
    private Texture quitGameButtonInactive;

    private Texture title;

    public MainMenuScreen (LettersGame game){
        this.game = game;
        newGameButtonActive = new Texture("buttonNowaGra1.png");
        newGameButtonInactive = new Texture("buttonNowaGra.png");
        gameRulesButtonActive = new Texture("buttonZasadyGry1.png");
        gameRulesButtonInactive = new Texture("buttonZasadyGry.png");
        quitGameButtonActive = new Texture("buttonWyjscie1.png");
        quitGameButtonInactive = new Texture("buttonWyjscie.png");
        title = new Texture("title.png");
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.113f, 0.686f, 0.658f, 1);
        game.batch.begin();
        game.batch.draw(title, LettersGame.WINDOW_WIDTH/2-TITLE_WIDTH/2, LettersGame.WINDOW_HEIGHT - 150,TITLE_WIDTH,TITLE_HEIGHT);

        int x = LettersGame.WINDOW_WIDTH/2 - BUTTON_WIDTH/2;
        if(Gdx.input.getX() < x + BUTTON_WIDTH && Gdx.input.getX() > x && LettersGame.WINDOW_HEIGHT - Gdx.input.getY() < NEWGAME_BUTTON_Y + BUTTON_HEIGHT && LettersGame.WINDOW_HEIGHT - Gdx.input.getY() > NEWGAME_BUTTON_Y){
            game.batch.draw(newGameButtonActive,x,NEWGAME_BUTTON_Y,BUTTON_WIDTH,BUTTON_HEIGHT);
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                this.dispose();
                game.setScreen(new MainGameScreen(game));
            }
        } else{
            game.batch.draw(newGameButtonInactive,x,NEWGAME_BUTTON_Y,BUTTON_WIDTH,BUTTON_HEIGHT);
        }

        if(Gdx.input.getX() < x + BUTTON_WIDTH && Gdx.input.getX() > x && LettersGame.WINDOW_HEIGHT - Gdx.input.getY() < RULES_BUTTON_Y + BUTTON_HEIGHT && LettersGame.WINDOW_HEIGHT - Gdx.input.getY() > RULES_BUTTON_Y){
            game.batch.draw(gameRulesButtonActive,x,RULES_BUTTON_Y,BUTTON_WIDTH,BUTTON_HEIGHT);
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                this.dispose();
//                game.setScreen(new RulesScreen(game));
//                game.setScreen(MainMenuScreen(game).resume());
            }
        } else{
            game.batch.draw(gameRulesButtonInactive,x,RULES_BUTTON_Y,BUTTON_WIDTH,BUTTON_HEIGHT);
        }

        if(Gdx.input.getX() < x + BUTTON_WIDTH && Gdx.input.getX() > x && LettersGame.WINDOW_HEIGHT - Gdx.input.getY() < QUIT_BUTTON_Y + BUTTON_HEIGHT && LettersGame.WINDOW_HEIGHT - Gdx.input.getY() > QUIT_BUTTON_Y){
            game.batch.draw(quitGameButtonActive,x,QUIT_BUTTON_Y,BUTTON_WIDTH,BUTTON_HEIGHT);
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                Gdx.app.exit();
            }
        } else{
            game.batch.draw(quitGameButtonInactive,x,QUIT_BUTTON_Y,BUTTON_WIDTH,BUTTON_HEIGHT);
        }

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {

    }
}