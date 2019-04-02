package com.example.woogyeongseo.shakingdice;


public class Dice {

    private int diceImage;
    private int diceNum;

    public Dice(int diceImage, int diceNum)
    {
        this.diceImage = diceImage;
        this.diceNum = diceNum;
    }

    public int getDiceImage() {
        return diceImage;
    }

    public int getDiceNum() {
        return diceNum;
    }

}
