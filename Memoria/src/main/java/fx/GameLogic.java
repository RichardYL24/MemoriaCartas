package fx;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author MatiasNoa
 */
import java.util.List;

public class GameLogic {
    private CardModel selected1 = null;
    private CardModel selected2 = null;

    public void selectCard(CardModel card) {
        if (card.isMatched() || card.isFaceUp()) return;

        card.flip();

        if (selected1 == null) {
            selected1 = card;
        } else if (selected2 == null && card != selected1) {
            selected2 = card;
        }
    }

    public boolean checkForMatch() {
        if (selected1 != null && selected2 != null) {
            boolean match = selected1.getId() == selected2.getId();
            if (match) {
                selected1.setMatched(true);
                selected2.setMatched(true);
            } else {
                selected1.flip();
                selected2.flip();
            }
            clearSelection();
            return match;
        }
        return false;
    }

    public boolean isReadyToCheck() {
        return selected1 != null && selected2 != null;
    }

    private void clearSelection() {
        selected1 = null;
        selected2 = null;
    }

    public boolean isGameWon(List<CardModel> cards) {
        return cards.stream().allMatch(CardModel::isMatched);
    }
}


