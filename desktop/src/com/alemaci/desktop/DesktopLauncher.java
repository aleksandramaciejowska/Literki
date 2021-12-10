package com.alemaci.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.alemaci.LettersGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.resizable = false;
		config.width = LettersGame.WINDOW_WIDTH;
		config.height = LettersGame.WINDOW_HEIGHT;
		config.title = "Literki";
		config.addIcon("icon.png", Files.FileType.Internal);

		new LwjglApplication(new LettersGame(), config);
	}
}
