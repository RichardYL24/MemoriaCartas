package fx;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class PlayerFX {
    private String name;
    private Image avatar;
    private Color color;

    public PlayerFX(String name, Image avatar, Color color) {
        this.name = name;
        this.avatar = avatar;
        this.color = color;
    }
    public String getName() { return name; }
    public Image getAvatar() { return avatar; }
    public Color getColor() { return color; }
}