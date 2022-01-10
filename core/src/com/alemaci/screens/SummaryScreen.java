package com.alemaci.screens;

import com.alemaci.LettersGame;
import com.alemaci.gameobjects.Word;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

/**Screen shown at the end of the game, displaying all the created words checked with dictionary*/
public class SummaryScreen implements Screen {

    private static final int BUTTON_WIDTH = 175;
    private static final int BUTTON_HEIGHT = 75;
    private static final int BACK_BUTTON_Y = 50;

    LettersGame game;

    private final Texture backToMenuButtonActive;
    private final Texture backToMenuButtonInactive;
    private final Texture backToGameButtonActive;
    private final Texture backToGameButtonInactive;


    private final GridPoint2 firstLine1 = new GridPoint2(100,LettersGame.WINDOW_HEIGHT-100);
    private final GridPoint2 firstLine2 = new GridPoint2(600,LettersGame.WINDOW_HEIGHT-100);

    private boolean isEveryWordCorrect = true;

    public SummaryScreen (LettersGame game){
        this.game = game;

        backToMenuButtonActive = new Texture("buttonPowrotDoMenu1.png");
        backToMenuButtonInactive = new Texture("buttonPowrotDoMenu.png");
        backToGameButtonActive = new Texture("buttonPowrotDoGry1.png");
        backToGameButtonInactive = new Texture("buttonPowrotDoGry.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.113f, 0.686f, 0.658f, 1);
        game.batch.begin();

        game.fontBlack.draw(game.batch,"Czas gry: " + game.time, LettersGame.WINDOW_WIDTH - 230, 100);

        game.fontBlack.draw(game.batch,"Słowa ułożone poziomo:", firstLine1.x, firstLine1.y+50);
        writeWords(game.horizontalWords, firstLine1);
        game.fontBlack.draw(game.batch,"Słowa ułożone pionowo:", firstLine2.x, firstLine2.y+50);
        writeWords(game.verticalWords, firstLine2);

        if(isEveryWordCorrect) {
            game.fontBlack.draw(game.batch, "Brawo! Wszystkie ułożone słowa znajdują się w słowniku języka \npolskiego! Może jeszcze jedna rozgrywka? :)", 50, BACK_BUTTON_Y + BUTTON_HEIGHT + 100);
        }else{
            game.fontRed.draw(game.batch, "Słowa w kolorze czerwonym nie znajdują się w słowniku języka \npolskiego. Wróć do gry, by poprawić swoją krzyżówkę. \nUwaga! Po przejściu do menu nie będzie można już poprawić \naktualnej krzyżówki!", 50, BACK_BUTTON_Y + BUTTON_HEIGHT + 150);
        }

        int x = LettersGame.WINDOW_WIDTH/2 - BUTTON_WIDTH/2;
        int xLeft = x - BUTTON_WIDTH/2 - 50;
        int xRight = x + BUTTON_WIDTH/2 + 50;

        if(Gdx.input.getX() < xLeft + BUTTON_WIDTH && Gdx.input.getX() > xLeft && LettersGame.WINDOW_HEIGHT - Gdx.input.getY() < BACK_BUTTON_Y + BUTTON_HEIGHT && LettersGame.WINDOW_HEIGHT - Gdx.input.getY() > BACK_BUTTON_Y){
            game.batch.draw(backToGameButtonActive,xLeft,BACK_BUTTON_Y,BUTTON_WIDTH,BUTTON_HEIGHT);
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                game.horizontalWords.clear();
                game.verticalWords.clear();
                game.setScreen(game.currentGameScreen);
            }
        } else{
            game.batch.draw(backToGameButtonInactive,xLeft,BACK_BUTTON_Y,BUTTON_WIDTH,BUTTON_HEIGHT);
        }

        if(Gdx.input.getX() < xRight + BUTTON_WIDTH && Gdx.input.getX() > xRight && LettersGame.WINDOW_HEIGHT - Gdx.input.getY() < BACK_BUTTON_Y + BUTTON_HEIGHT && LettersGame.WINDOW_HEIGHT - Gdx.input.getY() > BACK_BUTTON_Y){
            game.batch.draw(backToMenuButtonActive,xRight,BACK_BUTTON_Y,BUTTON_WIDTH,BUTTON_HEIGHT);
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                game.currentGameScreen = new MainGameScreen(game);
                game.setScreen(game.mainMenu);
            }
        } else{
            game.batch.draw(backToMenuButtonInactive,xRight,BACK_BUTTON_Y,BUTTON_WIDTH,BUTTON_HEIGHT);
        }

        game.batch.end();
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
        game.batch.dispose();
    }

    public void writeWords(ArrayList<Word> words, GridPoint2 position){
        int counter = 0;
        for (Word word : words) {
            if(word.inDictionary){
                game.fontBlack.draw(game.batch,word.word, position.x, position.y-counter*50);
            }else{
                game.fontRed.draw(game.batch,word.word, position.x, position.y-counter*50);
                isEveryWordCorrect=false;
            }
            counter++;
        }
    }


}
