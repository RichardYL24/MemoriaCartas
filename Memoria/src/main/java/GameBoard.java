/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Richard
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Collections;
import java.util.ArrayList;
import javax.swing.Timer; // usa el Timer de Swing
import java.util.List;    // usa la interfaz List de java.util


public class GameBoard extends JFrame {
    private Card selectedCard1 = null;
    private Card selectedCard2 = null;
    private Timer timer;
    private List<Card> cards = new ArrayList<>();

    public GameBoard() {
        setTitle("Juego de Memoria");
        setSize(600, 600);
        setLayout(new GridLayout(4, 4));

        for (int i = 1; i <= 8; i++) {
            cards.add(new Card(i, "images/" + i + ".jpg"));
            cards.add(new Card(i, "images/" + i + ".jpg"));
        }

        Collections.shuffle(cards);

        for (Card card : cards) {
            add(card);
            card.addActionListener(e -> handleCardClick(card));
        }

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void handleCardClick(Card card) {
        if (card.isMatched() || card == selectedCard1 || card == selectedCard2) return;

        card.showFront();

        if (selectedCard1 == null) {
            selectedCard1 = card;
        } else {
            selectedCard2 = card;
            if (selectedCard1.getId() == selectedCard2.getId()) {
                selectedCard1.setMatched(true);
                selectedCard2.setMatched(true);
                resetSelection();
                checkWin();
            } else {
                timer = new Timer(1000, e -> {
                    selectedCard1.showBack();
                    selectedCard2.showBack();
                    resetSelection();
                });
                timer.setRepeats(false);
                timer.start();
            }
        }
    }

    private void resetSelection() {
        selectedCard1 = null;
        selectedCard2 = null;
    }

    private void checkWin() {
        boolean allMatched = true;
        for (Card c : cards) {
            if (!c.isMatched()) {
                allMatched = false;
                break;
            }
        }
        if (allMatched) {
            JOptionPane.showMessageDialog(this, "¡Felicidades! Has encontrado todas las parejas.");
        }
    }
}