package com.alemaci.screens;

import com.alemaci.LettersGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class RulesScreen implements Screen {

    private static final int BUTTON_WIDTH = 280;
    private static final int BUTTON_HEIGHT = 120;
    private static final int BACK_BUTTON_Y = 100;

    LettersGame game;

    private final Texture backToMenuButtonActive;
    private final Texture backToMenuButtonInactive;
    private final Texture rules;

    boolean comeBackToMainMenu;

    public RulesScreen (LettersGame game, boolean comeBackToMainMenu){
        this.game = game;
        this.comeBackToMainMenu = comeBackToMainMenu;

        backToMenuButtonActive = new Texture("buttonPowrotDoMenu1.png");
        backToMenuButtonInactive = new Texture("buttonPowrotDoMenu.png");
        rules = new Texture("rules.png");
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.113f, 0.686f, 0.658f, 1);
        game.batch.begin();
        game.batch.draw(rules, 0, 0, LettersGame.WINDOW_WIDTH, LettersGame.WINDOW_HEIGHT);

        int x = LettersGame.WINDOW_WIDTH/2 - BUTTON_WIDTH/2;
        if(Gdx.input.getX() < x + BUTTON_WIDTH && Gdx.input.getX() > x && LettersGame.WINDOW_HEIGHT - Gdx.input.getY() < BACK_BUTTON_Y + BUTTON_HEIGHT && LettersGame.WINDOW_HEIGHT - Gdx.input.getY() > BACK_BUTTON_Y){
            game.batch.draw(backToMenuButtonActive,x,BACK_BUTTON_Y,BUTTON_WIDTH,BUTTON_HEIGHT);
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                if(comeBackToMainMenu){
                    game.setScreen(game.mainMenu);
                }else{
                    game.setScreen(new DuringGameMenuScreen(game));
                }
            }
        } else{
            game.batch.draw(backToMenuButtonInactive,x,BACK_BUTTON_Y,BUTTON_WIDTH,BUTTON_HEIGHT);
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
        game.batch.dispose();
        rules.dispose();
    }
}
