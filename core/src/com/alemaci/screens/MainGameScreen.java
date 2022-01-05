package com.alemaci.screens;

import com.alemaci.gameobjects.Letter;
import com.alemaci.LettersGame;
import com.alemaci.gameobjects.Word;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.ScreenUtils;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class MainGameScreen implements Screen {

    private static final int BUTTON_WIDTH = 175;
    private static final int BUTTON_HEIGHT = 75;
    private static final int BUTTON_Y = LettersGame.WINDOW_HEIGHT - BUTTON_HEIGHT - 30;
    private static final int MENU_BUTTON_X = LettersGame.WINDOW_WIDTH/2 - BUTTON_WIDTH/2 - 30 - BUTTON_WIDTH;
    private static final int SWAP_BUTTON_X = LettersGame.WINDOW_WIDTH/2 - BUTTON_WIDTH/2;
    private static final int CHECK_BUTTON_X = LettersGame.WINDOW_WIDTH/2 + BUTTON_WIDTH/2 + 30;

    private final Texture background;
    private final Texture menuButtonActive;
    private final Texture menuButtonInactive;
    private final Texture swapButtonActive;
    private final Texture swapButtonInactive;
    private final Texture checkButtonActive;
    private final Texture checkButtonInactive;

    private final ArrayList<Letter> letterBank;
    private final ArrayList<Letter> myLetters;
    public static ArrayList<GridPoint2> gridList;
    public static ArrayList<String> dictionary;
    public static ArrayList<GridPoint2> occupiedGridPositions;
    public String[][] finishedBoard;
    public Letter selectedLetter;
    public Word createdWord;
    private GridPoint2 lastMousePosition = new GridPoint2();
    public static int startValueOfPosOnBoard = 1000;
    private static int numberOfRows = BUTTON_Y / Letter.LETTER_HEIGHT;
    private static int numberOfColumns = LettersGame.WINDOW_WIDTH / Letter.LETTER_WIDTH;
    int firstRun = 2;

    float totalTime = 0;

    LettersGame game;

    public MainGameScreen(LettersGame game){
        this.game = game;

        menuButtonActive = new Texture("buttonPowrotDoMenu1.png");
        menuButtonInactive = new Texture("buttonPowrotDoMenu.png");
        swapButtonActive = new Texture("buttonWymien1.png");
        swapButtonInactive = new Texture("buttonWymien.png");
        checkButtonActive = new Texture("buttonSprawdz1.png");
        checkButtonInactive = new Texture("buttonSprawdz.png");
        background = new Texture("grid50.png");

        occupiedGridPositions = new ArrayList<>();

        letterBank = new ArrayList<>();
        fillLetterBank(letterBank);

        myLetters = new ArrayList<>();
        randomizeMyLetters(21);

        gridList = new ArrayList<>();
        createGrid();

        finishedBoard = new String[numberOfRows][numberOfColumns];
        game.horizontalWords = new ArrayList<>();
        game.verticalWords = new ArrayList<>();

        dictionary = new ArrayList<>();
        try {
            createDictionary();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        handleMouse();

        float deltaTime = Gdx.graphics.getDeltaTime();
        totalTime += deltaTime;
        int minutes = ((int)totalTime)/60;
        int seconds = ((int)totalTime)%60;

        ScreenUtils.clear(0.113f, 0.686f, 0.658f, 1);
        game.batch.begin();
        game.batch.draw(background, 0, 0, LettersGame.WINDOW_WIDTH, LettersGame.WINDOW_HEIGHT);

        if(seconds < 10){
            game.time = minutes + ":0" + seconds;
        }else{
            game.time = minutes + ":" + seconds;
        }
        game.fontBlack.draw(game.batch,"Czas: " + game.time, LettersGame.WINDOW_WIDTH - 175, LettersGame.WINDOW_HEIGHT - 50);

        if(Gdx.input.getX() < MENU_BUTTON_X + BUTTON_WIDTH && Gdx.input.getX() > MENU_BUTTON_X && LettersGame.WINDOW_HEIGHT - Gdx.input.getY() < BUTTON_Y + BUTTON_HEIGHT && LettersGame.WINDOW_HEIGHT - Gdx.input.getY() > BUTTON_Y){
            game.batch.draw(menuButtonActive,MENU_BUTTON_X,BUTTON_Y,BUTTON_WIDTH,BUTTON_HEIGHT);
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                game.setScreen(new DuringGameMenuScreen(game));
            }
        } else{
            game.batch.draw(menuButtonInactive,MENU_BUTTON_X,BUTTON_Y,BUTTON_WIDTH,BUTTON_HEIGHT);
        }

        if(Gdx.input.getX() < SWAP_BUTTON_X + BUTTON_WIDTH && Gdx.input.getX() > SWAP_BUTTON_X && LettersGame.WINDOW_HEIGHT - Gdx.input.getY() < BUTTON_Y + BUTTON_HEIGHT && LettersGame.WINDOW_HEIGHT - Gdx.input.getY() > BUTTON_Y){
            game.batch.draw(swapButtonActive,SWAP_BUTTON_X,BUTTON_Y,BUTTON_WIDTH,BUTTON_HEIGHT);
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                swapLetters();
            }
        } else{
            game.batch.draw(swapButtonInactive,SWAP_BUTTON_X,BUTTON_Y,BUTTON_WIDTH,BUTTON_HEIGHT);
        }

        if(Gdx.input.getX() < CHECK_BUTTON_X + BUTTON_WIDTH && Gdx.input.getX() > CHECK_BUTTON_X && LettersGame.WINDOW_HEIGHT - Gdx.input.getY() < BUTTON_Y + BUTTON_HEIGHT && LettersGame.WINDOW_HEIGHT - Gdx.input.getY() > BUTTON_Y){
            game.batch.draw(checkButtonActive,CHECK_BUTTON_X,BUTTON_Y,BUTTON_WIDTH,BUTTON_HEIGHT);
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                checkCrossword();
            }
        } else{
            game.batch.draw(checkButtonInactive,CHECK_BUTTON_X,BUTTON_Y,BUTTON_WIDTH,BUTTON_HEIGHT);
        }

        for (Letter letter : myLetters){
            letter.drawLetter(game.batch);
        }
        if (selectedLetter != null) {
            selectedLetter.drawLetter(game.batch);
        }

        if(firstRun==2) firstRun--;
        else if(firstRun==1){
            JOptionPane.showMessageDialog(null, "Pierwszą literę możesz ułożyć w dowolnym miejscu. \nKolejne litery dokładaj do już ułożonych liter.","Zanim zaczniesz",JOptionPane.INFORMATION_MESSAGE);
            firstRun--;
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
        background.dispose();
    }

    private void handleMouse(){
        GridPoint2 mousePosition = getMousePosMappedToScreenPos();

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            ListIterator<Letter> listIterator = myLetters.listIterator(myLetters.size());

            while (listIterator.hasPrevious()) {
                Letter letter = listIterator.previous();

                if (letter.isMouseIn(mousePosition)) {
                    selectedLetter = letter;
                    occupiedGridPositions.remove(selectedLetter.positionOnScreen);
                    listIterator.remove();
                    break;
                }
            }
            lastMousePosition.set(mousePosition);
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && selectedLetter != null) {
            if (isMouseInsideGameWindow()) {
                selectedLetter.moveBy(mousePosition.x - lastMousePosition.x, mousePosition.y - lastMousePosition.y);
                lastMousePosition.set(mousePosition);
            }
        } else if (selectedLetter != null) {
            selectedLetter.snapToGrid(selectedLetter.whichCell(mousePosition));
            if(occupiedGridPositions.contains(selectedLetter.positionOnScreen)){
                selectedLetter.setPositionOnScreen(mousePosition);
            } else if(mousePosition.x<1000) {
                selectedLetter.changePositionOnBoard(finishedBoard);
                if(isConnectionOnBoard(selectedLetter) || occupiedGridPositions.isEmpty()){
                    occupiedGridPositions.add(selectedLetter.positionOnScreen);
                } else{
                    selectedLetter.setPositionOnScreen(mousePosition);
                }
            } else{
                selectedLetter.setPositionOnScreen(randomizeLettersPosition());
            }
            myLetters.add(selectedLetter);
            selectedLetter = null;
        }
    }

    private void fillLetterBank(ArrayList<Letter> letterBank){
        for(int i=0;i<13;i++){
            letterBank.add(createLetter("a"));
        }
        for(int i=0;i<11;i++){
            letterBank.add(createLetter("j"));
        }
        for(int i=0;i<10;i++){
            letterBank.add(createLetter("e"));
            letterBank.add(createLetter("o"));
        }
        for(int i=0;i<7;i++){
            letterBank.add(createLetter("n"));
            letterBank.add(createLetter("z"));
        }
        for(int i=0;i<6;i++){
            letterBank.add(createLetter("r"));
            letterBank.add(createLetter("s"));
            letterBank.add(createLetter("w"));
        }
        for(int i=0;i<5;i++){
            letterBank.add(createLetter("c"));
            letterBank.add(createLetter("p"));
            letterBank.add(createLetter("t"));
            letterBank.add(createLetter("y"));
        }
        for(int i=0;i<4;i++){
            letterBank.add(createLetter("d"));
            letterBank.add(createLetter("k"));
            letterBank.add(createLetter("l"));
            letterBank.add(createLetter("m"));
        }
        for(int i=0;i<3;i++){
            letterBank.add(createLetter("b"));
            letterBank.add(createLetter("g"));
            letterBank.add(createLetter("h"));
            letterBank.add(createLetter("j"));
            letterBank.add(createLetter("ł"));
            letterBank.add(createLetter("u"));
        }
        for(int i=0;i<2;i++){
            letterBank.add(createLetter("ą"));
            letterBank.add(createLetter("ć"));
            letterBank.add(createLetter("ę"));
            letterBank.add(createLetter("f"));
            letterBank.add(createLetter("ż"));
        }
        letterBank.add(createLetter("ń"));
        letterBank.add(createLetter("ś"));
        letterBank.add(createLetter("ó"));
        letterBank.add(createLetter("ź"));

        sortLetters(letterBank);
    }

    private int randomIntMax(int maxValue) {
        return (int) (Math.random() * (maxValue + 1));
    }

    private GridPoint2 randomizeLettersPosition(){
        return new GridPoint2(randomIntMax(LettersGame.WINDOW_WIDTH - Letter.LETTER_WIDTH), randomIntMax(BUTTON_Y - Letter.LETTER_HEIGHT));
    }

    private Letter createLetter(String value){
        GridPoint2 positionOnScreen = randomizeLettersPosition();
        String imgName = "";
        if(value.equals("ą")) imgName = "A1.png";
        else if(value.equals("ć")) imgName = "C1.png";
        else if(value.equals("ę")) imgName = "E1.png";
        else if(value.equals("ł")) imgName = "L1.png";
        else if(value.equals("ń")) imgName = "N1.png";
        else if(value.equals("ó")) imgName = "O1.png";
        else if(value.equals("ś")) imgName = "S1.png";
        else if(value.equals("ź")) imgName = "Z1.png";
        else if(value.equals("ż")) imgName = "Z2.png";
        else imgName = value.toUpperCase(Locale.ROOT) + ".png";
        Texture letterImg = new Texture(imgName);
        int rowOnBoard = startValueOfPosOnBoard;
        int columnOnBoard = startValueOfPosOnBoard;
        Letter letter = new Letter(value, letterImg, positionOnScreen, rowOnBoard, columnOnBoard);
        return letter;
    }

    private void randomizeMyLetters(int howManyLetters){
        for(int i=0;i<howManyLetters;i++){
            int x = randomIntMax(letterBank.size()-1-i);
            myLetters.add(letterBank.get(x));
            letterBank.remove(x);
        }
        sortLetters(myLetters);
    }

    private void sortLetters(ArrayList list){
        Comparator<Letter> compareByValue = new Comparator<Letter>() {
            @Override
            public int compare(Letter o1, Letter o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        };

        Collections.sort(list, compareByValue);
    }

    private GridPoint2 getMousePosMappedToScreenPos() {
        return new GridPoint2(Gdx.input.getX(), LettersGame.WINDOW_HEIGHT - 1 - Gdx.input.getY());
    }

    private boolean isMouseInsideGameWindow() {
        GridPoint2 mousePosition = getMousePosMappedToScreenPos();
        return mousePosition.x >= 0 &&
                mousePosition.y >= 0 &&
                mousePosition.x < LettersGame.WINDOW_WIDTH &&
                mousePosition.y < LettersGame.WINDOW_HEIGHT;
    }

    private void createGrid(){
        for (int row = 0; row < numberOfRows; row++) {
            for (int col = 0; col < numberOfColumns; col++) {
                GridPoint2 cellPosition = new GridPoint2(col * Letter.LETTER_WIDTH,(numberOfRows - 1 - row) * Letter.LETTER_HEIGHT);
                gridList.add(cellPosition);
            }
        }
    }

    public boolean isConnectionOnBoard(Letter letter){
        int row = letter.positionOnScreen.y/Letter.LETTER_HEIGHT;
        int col = letter.positionOnScreen.x/Letter.LETTER_WIDTH;

        if(row==0){
            if(finishedBoard[row][col-1]!=null ||
                    finishedBoard[row][col+1]!=null ||
                    finishedBoard[row+1][col]!=null) return true;
            else return false;
        }else if(row==numberOfRows-1){
            if(finishedBoard[row][col-1]!=null ||
                    finishedBoard[row][col+1]!=null ||
                    finishedBoard[row-1][col]!=null) return true;
            else return false;
        }else if(col==0){
            if(finishedBoard[row][col+1]!=null ||
                    finishedBoard[row+1][col]!=null ||
                    finishedBoard[row-1][col]!=null) return true;
            else return false;
        }else if(col==numberOfColumns-1){
            if(finishedBoard[row][col-1]!=null ||
                    finishedBoard[row+1][col]!=null ||
                    finishedBoard[row-1][col]!=null) return true;
            else return false;
        }else{
            if(finishedBoard[row][col-1]!=null ||
                    finishedBoard[row][col+1]!=null ||
                    finishedBoard[row+1][col]!=null ||
                    finishedBoard[row-1][col]!=null) return true;
            else return false;
        }
    }

    private void swapLetters(){
        sortLetters(myLetters);

        ArrayList<String> letterValues = new ArrayList<>();

        for (Letter letter : myLetters) {
            String value = letter.value;
            letterValues.add(value);
        }
        Object[] chooseLetter = letterValues.toArray();

        String choice = (String)JOptionPane.showInputDialog(null, "Którą literę chcesz wymienić?", "Wymiana litery", JOptionPane.QUESTION_MESSAGE, null, chooseLetter, chooseLetter[0]);

        if(choice != null && letterBank.size()>=2){
            Letter chosenLetter = null;
            int i = 0;
            for (Letter letter : myLetters) {
                if (letter.value.equals(choice)) {
                    chosenLetter = letter;
                    break;
                }
                i++;
            }

            if(chosenLetter.rowOnBoard!=startValueOfPosOnBoard&&chosenLetter.columnOnBoard!=startValueOfPosOnBoard)
            finishedBoard[chosenLetter.rowOnBoard][chosenLetter.columnOnBoard]=null;

            myLetters.remove(i);
            letterBank.add(chosenLetter);
            sortLetters(letterBank);

            randomizeMyLetters(3);
        }else{
            JOptionPane.showMessageDialog(null, "W banku liter nie ma już wystarczającej liczby liter.","Nie można wymienić liter",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void checkCrossword(){
        if(occupiedGridPositions.size() == myLetters.size()){
            createWords();

            for (Word word : game.horizontalWords) {
                word.checkDictionary();
            }
            for (Word word : game.verticalWords) {
                word.checkDictionary();
            }

            game.setScreen(new SummaryScreen(game));

        }else{
            JOptionPane.showMessageDialog(null, "Ułóż wszystkie literki!","To jeszcze nie koniec",JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private void createWords(){
        String wordValueHorizontal = "";
        String wordValueVertical = "";

        //horizontal words
        for (int row = 0; row < numberOfRows; row++) {
            for (int col = 0; col < numberOfColumns; col++) {
                if(finishedBoard[row][col] != null){
                    if((col == 0 && finishedBoard[row][col+1] == null) || (finishedBoard[row][col-1] == null && finishedBoard[row][col+1] == null) || (finishedBoard[row][col-1] == null && col == numberOfColumns-1))
                        continue;

                    wordValueHorizontal += finishedBoard[row][col];

                    if(col == numberOfColumns-1 || finishedBoard[row][col+1] == null){
                        createdWord = new Word(wordValueHorizontal,false);
                        game.horizontalWords.add(createdWord);
                        wordValueHorizontal = "";
                    }
                }
            }
        }

        //vertical words
        for (int col = 0; col < numberOfColumns; col++) {
            for (int row = numberOfRows-1; row >= 0; row--) {
                if(finishedBoard[row][col] != null){
                    if((row == numberOfRows-1 && finishedBoard[row-1][col] == null) || (finishedBoard[row+1][col] == null && finishedBoard[row-1][col] == null) || (finishedBoard[row+1][col] == null && row == 0))
                        continue;

                    wordValueVertical += finishedBoard[row][col];


                    if(row == 0 || finishedBoard[row-1][col] == null){
                        createdWord = new Word(wordValueVertical,false);
                        game.verticalWords.add(createdWord);
                        wordValueVertical = "";
                    }
                }
            }
        }
    }

    public void createDictionary() throws IOException {
        Path path = Paths.get("slowa.txt");

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)){
            String line;
            while ((line = reader.readLine()) != null) {
                dictionary.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}