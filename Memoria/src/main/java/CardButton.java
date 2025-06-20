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

public class CardButton extends JButton {
    private final CardModel model;
    private final ImageIcon frontImage;
    private final ImageIcon backImage;

    public CardButton(CardModel model, String imagePath) {
        this.model = model;
        this.frontImage = new ImageIcon(imagePath);
        this.backImage = new ImageIcon("images/back.jpg");

        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        setContentAreaFilled(false);
        setFocusPainted(false);

        updateVisual();

        // El tamaño se ajustará automáticamente al de la imagen
        setPreferredSize(new Dimension(frontImage.getIconWidth(), frontImage.getIconHeight()));
    }

    public CardModel getCardModel() {
        return model;
    }

    public void updateVisual() {
        setIcon(model.isFaceUp() || model.isMatched() ? frontImage : backImage);
    }
}
