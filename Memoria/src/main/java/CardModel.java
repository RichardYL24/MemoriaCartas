/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author MatiasNoa
 */
public class CardModel {
    private final int id;
    private boolean isFaceUp = false;
    private boolean isMatched = false;

    public CardModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isFaceUp() {
        return isFaceUp;
    }

    public void flip() {
        if (!isMatched) {
            isFaceUp = !isFaceUp;
        }
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        this.isMatched = matched;
    }
}
