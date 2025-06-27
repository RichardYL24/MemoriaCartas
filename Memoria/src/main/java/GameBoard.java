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
import java.util.Random;
import fx.CardModel;
import fx.GameLogic;

public class GameBoard extends JFrame {
    private final GameLogic logic = new GameLogic();
    private final List<CardModel> cardModels = new ArrayList<>();
    private final List<CardButton> cardButtons = new ArrayList<>();
    private boolean isBlocked = false;
    private final int MAX_MOVIMIENTOS;
    private int movimientosRestantes;
    private JLabel movimientosLabel;

    // PvP
    private final Player[] players = new Player[2];
    private int currentPlayer = 0;
    private JLabel turnoLabel, puntajeLabel, poderesLabel;
    private PowerType powerToUse = null;
    private Random random = new Random();

    public GameBoard(int numberOfPairs, String nombre1, String nombre2) {
        setTitle("Juego de Memoria PvP");
        setSize(900, 950);
        setLayout(new BorderLayout(10, 10));

        MAX_MOVIMIENTOS = numberOfPairs * 2 + 5;
        movimientosRestantes = MAX_MOVIMIENTOS;

        // --- Solución: nombres por defecto sin espacios y únicos ---
        if (nombre1 == null || nombre1.trim().isEmpty()) nombre1 = "Jugador1";
        if (nombre2 == null || nombre2.trim().isEmpty()) nombre2 = "Jugador2";
        if (nombre1.equals(nombre2)) nombre2 = nombre2 + "_2";
        // ----------------------------------------------------------

        players[0] = new Player(nombre1);
        players[1] = new Player(nombre2);

        Font fuente = new Font("SansSerif", Font.BOLD, 18);
        movimientosLabel = new JLabel("Movimientos restantes: " + movimientosRestantes);
        movimientosLabel.setFont(fuente);

        turnoLabel = new JLabel();
        turnoLabel.setFont(fuente);

        puntajeLabel = new JLabel();
        puntajeLabel.setFont(fuente);

        JPanel topPanel = new JPanel(new GridLayout(2, 2));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(movimientosLabel);
        topPanel.add(turnoLabel);
        topPanel.add(puntajeLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel poderesPanel = new JPanel(new BorderLayout());
        // --- Mejora visual de poderes ---
        poderesPanel.setBackground(new Color(245, 246, 250));
        poderesPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        poderesLabel = new JLabel();
        poderesLabel.setFont(new Font("Arial", Font.BOLD, 16));
        poderesLabel.setForeground(new Color(44, 62, 80));
        poderesPanel.add(poderesLabel, BorderLayout.CENTER);
        JButton usarPoderBtn = new JButton("Usar Poder");
        usarPoderBtn.setFont(new Font("Arial", Font.BOLD, 16));
        usarPoderBtn.setBackground(new Color(52, 152, 219));
        usarPoderBtn.setForeground(Color.WHITE);
        usarPoderBtn.setFocusPainted(false);
        usarPoderBtn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        usarPoderBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        usarPoderBtn.addActionListener(e -> seleccionarPoder());
        usarPoderBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                usarPoderBtn.setBackground(new Color(41, 128, 185));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                usarPoderBtn.setBackground(new Color(52, 152, 219));
            }
        });
        poderesPanel.add(usarPoderBtn, BorderLayout.EAST);
        add(poderesPanel, BorderLayout.SOUTH);

        int totalCards = numberOfPairs * 2;
        int rows, cols;
        if (numberOfPairs == 18) { // Difícil: 4x9
            rows = 4;
            cols = 9;
        } else {
            rows = (int) Math.sqrt(totalCards);
            cols = totalCards / rows;
            // Ajuste para casos donde no es cuadrado perfecto
            if (rows * cols < totalCards) {
                cols++;
            }
        }

        JPanel gridPanel = new JPanel(new GridLayout(rows, cols, 10, 10));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(gridPanel, BorderLayout.CENTER);

        for (int i = 1; i <= numberOfPairs; i++) {
            cardModels.add(new CardModel(i));
            cardModels.add(new CardModel(i));
        }
        Collections.shuffle(cardModels);

        for (int idx = 0; idx < cardModels.size(); idx++) {
            CardModel model = cardModels.get(idx);
            CardButton button = new CardButton(model, "images/" + model.getId() + ".jpg");
            button.setPreferredSize(new Dimension(100, 100));
            final int cardIdx = idx;
            button.addActionListener(e -> onCardClick(button, cardIdx));
            cardButtons.add(button);
            gridPanel.add(button);
        }

        updateUIInfo();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateUIInfo() {
        movimientosLabel.setText("Movimientos restantes: " + movimientosRestantes);
        turnoLabel.setText("Turno: " + players[currentPlayer].getName());
        puntajeLabel.setText(players[0].getName() + ": " + players[0].getScore() + " pts | " +
                             players[1].getName() + ": " + players[1].getScore() + " pts");
        // --- Presentación visual mejorada de poderes ---
        List<PowerType> powers = players[currentPlayer].getPowers();
        if (powers.isEmpty()) {
            poderesLabel.setText("Poderes: <html><span style='color:#b2bec3;'>Ninguno</span></html>");
        } else {
            StringBuilder sb = new StringBuilder("<html>Poderes: ");
            for (PowerType p : powers) {
                String color = "#00b894";
                switch (p) {
                    case VER_DOS_CARTAS: color = "#00b894"; break;
                    case ROBAR_TURNO: color = "#fdcb6e"; break;
                    case RANDOMIZAR_DECK: color = "#0984e3"; break;
                }
                sb.append("<span style='background:#fff;border-radius:8px;padding:2px 8px;margin:2px;color:")
                  .append(color).append(";border:1px solid #dfe6e9;'>")
                  .append(p.toString().replace("_", " "))
                  .append("</span> ");
            }
            sb.append("</html>");
            poderesLabel.setText(sb.toString());
        }
    }

    private void seleccionarPoder() {
        Player p = players[currentPlayer];
        if (p.getPowers().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tienes poderes disponibles.");
            return;
        }
        PowerType[] arr = p.getPowers().toArray(new PowerType[0]);
        PowerType seleccion = (PowerType) JOptionPane.showInputDialog(
            this, "Selecciona un poder para usar:",
            "Poderes", JOptionPane.PLAIN_MESSAGE, null, arr, arr[0]);
        if (seleccion != null) {
            powerToUse = seleccion;
            aplicarPoder(seleccion);
            p.usePower(seleccion);
            updateUIInfo();
        }
    }

    private void aplicarPoder(PowerType poder) {
        switch (poder) {
            case VER_DOS_CARTAS:
                verDosCartas();
                break;
            case ROBAR_TURNO:
                // No mostrar mensaje aquí, se maneja en onCardClick según el resultado
                break;
            case RANDOMIZAR_DECK:
                randomizarDeckRival();
                break;
        }
    }

    private void verDosCartas() {
        List<CardModel> ocultas = new ArrayList<>();
        for (CardModel c : cardModels) {
            if (!c.isFaceUp() && !c.isMatched()) ocultas.add(c);
        }
        if (ocultas.size() < 2) return;
        Collections.shuffle(ocultas);
        ocultas.get(0).flip();
        ocultas.get(1).flip();
        updateAllButtons();
        Timer t = new Timer(1200, e -> {
            ocultas.get(0).flip();
            ocultas.get(1).flip();
            updateAllButtons();
        });
        t.setRepeats(false);
        t.start();
    }

    private void randomizarDeckRival() {
        // Solo mezcla las cartas que NO están emparejadas ni volteadas
        List<Integer> indicesNoVisibles = new ArrayList<>();
        List<CardModel> cartasNoVisibles = new ArrayList<>();
        for (int i = 0; i < cardModels.size(); i++) {
            CardModel c = cardModels.get(i);
            if (!c.isMatched() && !c.isFaceUp()) {
                indicesNoVisibles.add(i);
                cartasNoVisibles.add(c);
            }
        }
        Collections.shuffle(cartasNoVisibles);
        // Reasigna solo las cartas no visibles
        for (int j = 0; j < indicesNoVisibles.size(); j++) {
            cardModels.set(indicesNoVisibles.get(j), cartasNoVisibles.get(j));
        }
        // Actualiza los botones con los modelos correctos
        for (int i = 0; i < cardButtons.size(); i++) {
            cardButtons.get(i).setCardModel(cardModels.get(i));
        }
        updateAllButtons();
        JOptionPane.showMessageDialog(this, "¡El mazo del rival ha sido mezclado!");
    }

    private void onCardClick(CardButton button, int cardIdx) {
        if (isBlocked) return;
        // Evita seleccionar más de dos cartas rápidamente
        if (logic.isReadyToCheck()) return;

        logic.selectCard(button.getCardModel());
        button.updateVisual();

        if (logic.isReadyToCheck()) {
            isBlocked = true;
            movimientosRestantes--;
            updateUIInfo();

            Timer delay = new Timer(1000, e -> {
                boolean acierto = logic.checkForMatch();
                updateAllButtons();

                if (acierto) {
                    players[currentPlayer].addScore(2);
                    players[currentPlayer].incrementStreak();
                    if (players[currentPlayer].getStreak() > 0 && players[currentPlayer].getStreak() % 2 == 0) {
                        PowerType nuevo;
                        // Solo permite VER_DOS_CARTAS, ROBAR_TURNO, RANDOMIZAR_DECK
                        PowerType[] posibles = {PowerType.VER_DOS_CARTAS, PowerType.ROBAR_TURNO, PowerType.RANDOMIZAR_DECK};
                        nuevo = posibles[random.nextInt(posibles.length)];
                        players[currentPlayer].addPower(nuevo);
                        JOptionPane.showMessageDialog(this, "¡" + players[currentPlayer].getName() +
                                " ganó un poder: " + nuevo.toString().replace("_", " ") + "!");
                    }
                } else {
                    players[currentPlayer].resetStreak();
                }

                if (logic.isGameWon(cardModels)) {
                    mostrarResultados(true);
                    dispose();
                } else if (movimientosRestantes <= 0) {
                    mostrarResultados(false);
                    dispose();
                } else {
                    // Cambio de turno solo si falló
                    if (!acierto) {
                        if (powerToUse == PowerType.ROBAR_TURNO) {
                            JOptionPane.showMessageDialog(this, "¡Robaste el turno! Juegas de nuevo.");
                            // No cambia el turno
                        } else {
                            currentPlayer = 1 - currentPlayer;
                            //blockedCards.clear(); // Limpia bloqueos al cambiar de turno
                        }
                    }
                    powerToUse = null;
                    updateUIInfo();
                }
                isBlocked = false;
                System.out.println("DEBUG: isBlocked = " + isBlocked);
            });
            delay.setRepeats(false);
            delay.start();
        }
    }

    // Cambia la firma para aceptar victoria/derrota
    private void mostrarResultados(boolean victoria) {
        String ganador;
        boolean empate = false;
        if (players[0].getScore() > players[1].getScore()) {
            ganador = players[0].getName();
        } else if (players[1].getScore() > players[0].getScore()) {
            ganador = players[1].getName();
        } else {
            ganador = null;
            empate = true;
        }
        // Mostrar pantalla de resultados personalizada
        new ResultScreen(!empate && victoria, movimientosRestantes);
        dispose();
    }

    private void updateAllButtons() {
        for (CardButton b : cardButtons) {
            b.updateVisual();
        }
    }
}