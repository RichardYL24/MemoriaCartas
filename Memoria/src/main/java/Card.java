/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Richard
 */
import javax.swing.*;

public class Card extends JButton {
    private int id;
    private boolean matched = false;
    private ImageIcon frontImage;
    private ImageIcon backImage;

    public Card(int id, String imagePath) {
        this.id = id;
        this.frontImage = new ImageIcon(imagePath);
        this.backImage = new ImageIcon("images/back.jpg");
        setIcon(backImage);
    }

    public void showFront() {
        setIcon(frontImage);
    }

    public void showBack() {
        setIcon(backImage);
    }

    public int getId() {
        return id;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }
}