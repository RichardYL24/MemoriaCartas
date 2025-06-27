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
    public ResultScreen(boolean victoria, int movimientosRestantes) {
        setTitle(victoria ? "¡Ganaste!" : "Fin del Juego");
        setSize(440, 320);
        setLayout(new BorderLayout());

        // Panel principal con fondo oscuro o rojo según resultado
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = victoria ? new Color(34, 49, 63) : new Color(192, 57, 43);
                Color color2 = victoria ? new Color(44, 62, 80) : new Color(231, 76, 60);
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
            }
        };
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Icono
        JLabel icono = new JLabel(victoria ? "\uD83C\uDFC6" : "\uD83D\uDE41", SwingConstants.CENTER);
        icono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
        icono.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Mensaje principal
        JLabel mensaje = new JLabel(
            victoria ? "¡Felicidades, ganaste!" : "Se acabó el tiempo o empate",
            SwingConstants.CENTER
        );
        mensaje.setFont(new Font("Arial", Font.BOLD, 30));
        mensaje.setForeground(Color.WHITE);
        mensaje.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Movimientos restantes
        JLabel movLabel = new JLabel("Movimientos restantes: " + movimientosRestantes, SwingConstants.CENTER);
        movLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        movLabel.setForeground(Color.WHITE);
        movLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(icono);
        panel.add(Box.createRigidArea(new Dimension(0, 18)));
        panel.add(mensaje);
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(movLabel);
        panel.add(Box.createVerticalGlue());

        // Botón estilizado con hover
        JButton volver = new JButton("Volver al menú");
        volver.setFont(new Font("Arial", Font.BOLD, 20));
        volver.setBackground(new Color(52, 152, 219));
        volver.setForeground(Color.WHITE);
        volver.setFocusPainted(false);
        volver.setBorder(BorderFactory.createEmptyBorder(12, 28, 12, 28));
        volver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        volver.setAlignmentX(Component.CENTER_ALIGNMENT);
        volver.addActionListener(e -> {
            new MainMenu();
            dispose();
        });
        volver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                volver.setBackground(new Color(41, 128, 185));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                volver.setBackground(new Color(52, 152, 219));
            }
        });

        JPanel southPanel = new JPanel();
        southPanel.setOpaque(false);
        southPanel.add(volver);

        // Fondo redondeado y sombra
        JPanel wrapper = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(new Color(0,0,0,60));
                g2.fillRoundRect(8, 8, getWidth()-16, getHeight()-16, 50, 50);
            }
        };
        wrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        wrapper.setOpaque(false);
        wrapper.add(panel, BorderLayout.CENTER);
        wrapper.add(southPanel, BorderLayout.SOUTH);

        setContentPane(wrapper);
        setUndecorated(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
