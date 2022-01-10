package com.alemaci;

import com.alemaci.screens.MainGameScreen;
import com.alemaci.screens.MainMenuScreen;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/** Main game class */
public class LettersGame extends Game implements ApplicationListener {

	public static final int WINDOW_WIDTH = 1024;
	public static final int WINDOW_HEIGHT = 768;

	public SpriteBatch batch;
	public BitmapFont fontBlack;
	public BitmapFont fontRed;

	public Screen currentGameScreen;
	public Screen mainMenu;

	/**ArrayList of words stacked horizontally*/
	public ArrayList<com.alemaci.gameobjects.Word> horizontalWords;
	/**ArrayList of words stacked vertically*/
	public ArrayList<com.alemaci.gameobjects.Word> verticalWords;

	public String time;

	@Override
	public void create () {
		batch = new SpriteBatch();

		fontBlack = new BitmapFont(Gdx.files.internal("bookmanblack.fnt"));
		fontRed = new BitmapFont(Gdx.files.internal("bookmanred.fnt"));

		horizontalWords = new ArrayList<>();
		verticalWords = new ArrayList<>();

		time = "";

		mainMenu = new MainMenuScreen(this);
		currentGameScreen = new MainGameScreen(this);
		this.setScreen(mainMenu);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		fontBlack.dispose();
		fontRed.dispose();
	}

}
