package com.alemaci;

import com.alemaci.screens.MainGameScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;

import java.util.ListIterator;

public class Letter {

    public Texture letterImg;
    public GridPoint2 positionOnScreen;
    public String value;
    public int rowOnBoard;
    public int columnOnBoard;
    public static final int LETTER_WIDTH = 50;
    public static final int LETTER_HEIGHT = 50;

    public Letter(String value, Texture letterImg, GridPoint2 positionOnScreen, int rowOnBoard, int columnOnBoard){
        this.value = value;
        this.letterImg = letterImg;
        this.positionOnScreen = new GridPoint2(positionOnScreen);
        this.rowOnBoard = rowOnBoard;
        this.columnOnBoard = columnOnBoard;
    }

    public String getValue(){
        return value;
    }

    public void drawLetter(SpriteBatch batch) {
        batch.draw(letterImg,positionOnScreen.x,positionOnScreen.y,LETTER_WIDTH,LETTER_HEIGHT);
    }

    public boolean isMouseIn(GridPoint2 mousePos) {
        return mousePos.x >= positionOnScreen.x &&
                mousePos.y >= positionOnScreen.y &&
                mousePos.x < positionOnScreen.x + LETTER_WIDTH &&
                mousePos.y < positionOnScreen.y + LETTER_HEIGHT;
    }

    public void moveBy(int x, int y) {
        positionOnScreen.x += x;
        positionOnScreen.y += y;
    }

    public GridPoint2 whichCell(GridPoint2 dropPosition){
        GridPoint2 foundCellPosition = new GridPoint2();
        ListIterator<GridPoint2> listIterator = MainGameScreen.gridList.listIterator();
        while (listIterator.hasNext()) {
            GridPoint2 cellPosition = listIterator.next();

            if (dropPosition.x >= cellPosition.x &&
                    dropPosition.y >= cellPosition.y &&
                    dropPosition.x < cellPosition.x + Letter.LETTER_WIDTH &&
                    dropPosition.y < cellPosition.y + Letter.LETTER_HEIGHT) {
                foundCellPosition.set(cellPosition);
                break;
            }
        }
        return foundCellPosition;
    }

    public void snapToGrid(GridPoint2 cellPosition){
        positionOnScreen.set(cellPosition);

    }

    public void changePositionOnBoard(String finishedBoard[][]){
        if(rowOnBoard!=MainGameScreen.startValueOfPosOnBoard && columnOnBoard!=MainGameScreen.startValueOfPosOnBoard){
            finishedBoard[rowOnBoard][columnOnBoard] = null;
        }
        rowOnBoard = positionOnScreen.y/Letter.LETTER_HEIGHT;
        columnOnBoard = positionOnScreen.x/Letter.LETTER_WIDTH;
        finishedBoard[rowOnBoard][columnOnBoard] = value;
    }
}
