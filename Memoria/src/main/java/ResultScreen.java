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

public class ResultScreen extends JFrame {
    public ResultScreen(boolean victoria, int tiempo) {
        setTitle(victoria ? "¡Ganaste!" : "Fin del Juego");
        setSize(300, 200);
        setLayout(new BorderLayout());

        JLabel mensaje = new JLabel(
            victoria ? "¡Felicidades, ganaste!" : "Se acabó el tiempo",
            SwingConstants.CENTER
        );
        mensaje.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel tiempoLabel = new JLabel("Tu tiempo: " + tiempo + " segundos", SwingConstants.CENTER);

        JButton volver = new JButton("Volver al menú");
        volver.addActionListener(e -> {
            new MainMenu();
            dispose();
        });

        JPanel center = new JPanel(new GridLayout(2, 1));
        center.add(mensaje);
        center.add(tiempoLabel);

        add(center, BorderLayout.CENTER);
        add(volver, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
