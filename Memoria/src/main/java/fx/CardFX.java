package fx;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.animation.RotateTransition;
import javafx.util.Duration;
import javafx.scene.transform.Rotate;
import java.io.File;

public class CardFX extends StackPane {
    private ImageView front, back;
    private boolean faceUp = false;

    public CardFX(String frontPath, String backPath, int id) {
        Image frontImg, backImg;
        File frontFile = new File(frontPath);
        if (frontFile.exists()) {
            frontImg = new Image(frontFile.toURI().toString(), 100, 100, true, true);
        } else {
            frontImg = new Image("https://via.placeholder.com/100x100.png?text=" + id);
        }
        File backFile = new File(backPath);
        if (backFile.exists()) {
            backImg = new Image(backFile.toURI().toString(), 100, 100, true, true);
        } else {
            backImg = new Image("https://via.placeholder.com/100x100.png?text=?");
        }
        front = new ImageView(frontImg);
        back = new ImageView(backImg);
        front.setFitWidth(100);
        front.setFitHeight(100);
        back.setFitWidth(100);
        back.setFitHeight(100);
        getChildren().addAll(back, front);
        front.setVisible(false);
        setPrefSize(100, 100);

        setStyle("-fx-background-color: #fff; -fx-border-color: #30336b; -fx-border-width: 2; -fx-background-radius: 10; -fx-border-radius: 10;");
        setOnMouseClicked(e -> flip());
    }

    public void flip() {
        RotateTransition rt = new RotateTransition(Duration.millis(350), this);
        rt.setAxis(Rotate.Y_AXIS);
        rt.setFromAngle(0);
        rt.setToAngle(180);
        rt.setOnFinished(ev -> {
            faceUp = !faceUp;
            front.setVisible(faceUp);
            back.setVisible(!faceUp);
            setRotate(0); // Corrige la orientación tras la animación
        });
        rt.play();
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void setFaceUp(boolean up) {
        this.faceUp = up;
        front.setVisible(faceUp);
        back.setVisible(!faceUp);
    }
}