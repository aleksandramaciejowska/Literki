package com.alemaci;

import com.alemaci.screens.MainGameScreen;

public class Word {

    public String word;
    public boolean inDictionary;
    public int startPosX;
    public int startPosY;
    public int endPosX;
    public int endPosY;

    public Word(String word, boolean inDictionary, int startPosX, int startPosY, int endPosX, int endPosY){
        this.word = word;
        this.inDictionary = inDictionary;
        this.startPosX = startPosX;
        this.startPosY = startPosY;
        this.endPosX = endPosX;
        this.endPosY = endPosY;
    }

    public void checkDictionary(){
        for(String wordInDictionary : MainGameScreen.dictionary) {
            inDictionary = word.equals(wordInDictionary);
            if(inDictionary) break;
        }
    }
}
