package com.alemaci.gameobjects;

import com.alemaci.screens.MainGameScreen;

public class Word {

    public String word;
    public boolean inDictionary;

    /**Object containing information about created word
     * @param word String containing the word
     * @param inDictionary Is the word in dictionary
     */
    public Word(String word, boolean inDictionary){
        this.word = word;
        this.inDictionary = inDictionary;
    }

    /**Compare created word with dictionary*/
    public void checkDictionary(){
        for(String wordInDictionary : MainGameScreen.dictionary) {
            inDictionary = word.equals(wordInDictionary);
            if(inDictionary) break;
        }
    }


}
