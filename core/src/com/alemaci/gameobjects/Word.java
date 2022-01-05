package com.alemaci.gameobjects;

import com.alemaci.screens.MainGameScreen;

public class Word {

    public String word;
    public boolean inDictionary;

    public Word(String word, boolean inDictionary){
        this.word = word;
        this.inDictionary = inDictionary;
    }

    public void checkDictionary(){
        for(String wordInDictionary : MainGameScreen.dictionary) {
            inDictionary = word.equals(wordInDictionary);
            if(inDictionary) break;
        }
    }


}
