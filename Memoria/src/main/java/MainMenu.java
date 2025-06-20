/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author MatiasNoa
 */
import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Menú Principal");
        setSize(400, 300);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Juego de Memoria", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        String[] niveles = { "Fácil (4x2)", "Medio (4x4)", "Difícil (6x6)" };
        JComboBox<String> nivelBox = new JComboBox<>(niveles);
        JButton startButton = new JButton("Iniciar Juego");

        startButton.addActionListener(e -> {
            int seleccion = nivelBox.getSelectedIndex();
            int pares;
            if (seleccion == 0) {
                pares = 4; // Fácil (4x2)
            } else if (seleccion == 1) {
                pares = 8; // Medio (4x4)
            } else if (seleccion == 2) {
                pares = 18; // Difícil (6x6)
            } else {
                pares = 8;
            }

            new GameBoard(pares);
            dispose(); // Cierra el menú
        });

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 1, 10, 10));
        centerPanel.add(nivelBox);
        centerPanel.add(startButton);

        add(title, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
