/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Richard
 */

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.awt.*;

public class GameBoard extends JFrame {
    private final GameLogic logic = new GameLogic();
    private final List<CardModel> cardModels = new ArrayList<>();
    private final List<CardButton> cardButtons = new ArrayList<>();
    private boolean isBlocked = false;
    private JLabel timerLabel = new JLabel("Tiempo: 0s");
    private Timer gameTimer;
    private int secondsElapsed = 0;
    private final int TIEMPO_LIMITE = 120; // 2 minutos

    public GameBoard(int numberOfPairs) {
        setTitle("Juego de Memoria");
        setSize(800, 850);
        setLayout(new BorderLayout(10, 10));

        // Estética
        Font fuente = new Font("SansSerif", Font.BOLD, 18);
        timerLabel.setFont(fuente);
        timerLabel.setForeground(Color.DARK_GRAY);

        JButton backButton = new JButton("⟵ Volver al Menú");
        backButton.setFont(fuente);
        backButton.addActionListener(e -> {
            gameTimer.stop();
            dispose();
            new MainMenu();
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(timerLabel, BorderLayout.WEST);
        topPanel.add(backButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        int totalCards = numberOfPairs * 2;
        int rows = (int) Math.sqrt(totalCards);
        int cols = totalCards / rows;

        JPanel gridPanel = new JPanel(new GridLayout(rows, cols, 10, 10));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(gridPanel, BorderLayout.CENTER);

        for (int i = 1; i <= numberOfPairs; i++) {
            cardModels.add(new CardModel(i));
            cardModels.add(new CardModel(i));
        }

        Collections.shuffle(cardModels);

        for (CardModel model : cardModels) {
            CardButton button = new CardButton(model, "images/" + model.getId() + ".jpg");
            button.setPreferredSize(new Dimension(100, 100));
            button.addActionListener(e -> onCardClick(button));
            cardButtons.add(button);
            gridPanel.add(button);
        }

        startTimer();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void onCardClick(CardButton button) {
        if (isBlocked) return;

        logic.selectCard(button.getCardModel());
        button.updateVisual();

        if (logic.isReadyToCheck()) {
            isBlocked = true;

            Timer delay = new Timer(1000, e -> {
                logic.checkForMatch();
                updateAllButtons();
                isBlocked = false;

                if (logic.isGameWon(cardModels)) {
                    gameTimer.stop();
                    new ResultScreen(true, secondsElapsed);
                    dispose();
                }
            });
            delay.setRepeats(false);
            delay.start();
        }
    }

    private void updateAllButtons() {
        for (CardButton b : cardButtons) {
            b.updateVisual();
        }
    }

    private void startTimer() {
        gameTimer = new Timer(1000, e -> {
            secondsElapsed++;
            timerLabel.setText("⏱ Tiempo: " + secondsElapsed + "s");

            if (secondsElapsed >= TIEMPO_LIMITE) {
                gameTimer.stop();
                new ResultScreen(false, secondsElapsed);
                dispose();
            }
        });
        gameTimer.start();
    }
}
