package fx;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ResultScreenFX {
    public void show(Stage stage, String ganador, boolean empate, int movimientosRestantes) {
        // Fondo con gradiente
        Stop[] stops;
        if (empate) {
            stops = new Stop[] { new Stop(0, Color.web("#636e72")), new Stop(1, Color.web("#b2bec3")) };
        } else if (ganador != null) {
            stops = new Stop[] { new Stop(0, Color.web("#00b894")), new Stop(1, Color.web("#0984e3")) };
        } else {
            stops = new Stop[] { new Stop(0, Color.web("#d63031")), new Stop(1, Color.web("#e17055")) };
        }
        LinearGradient grad = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);

        VBox root = new VBox(30);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(60));
        root.setBackground(new Background(new BackgroundFill(grad, new CornerRadii(30), Insets.EMPTY)));

        // Icono
        Label icon = new Label(empate ? "\uD83D\uDE10" : (ganador != null ? "\uD83C\uDFC6" : "\uD83D\uDE41"));
        icon.setFont(Font.font("Segoe UI Emoji", FontWeight.BOLD, 80));
        icon.setTextFill(Color.WHITE);

        // Mensaje principal
        Label msg = new Label();
        if (empate) {
            msg.setText("¡Empate!");
        } else if (ganador != null) {
            msg.setText("¡Ganó " + ganador + "!");
        } else {
            msg.setText("Fin del Juego");
        }
        msg.setFont(Font.font("Arial", FontWeight.BOLD, 44));
        msg.setTextFill(Color.WHITE);

        // Movimientos restantes
        Label movLabel = new Label("Movimientos restantes: " + movimientosRestantes);
        movLabel.setFont(Font.font("Arial", 24));
        movLabel.setTextFill(Color.WHITE);

        // Botón estilizado
        Button menuBtn = new Button("Volver al Menú");
        menuBtn.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        menuBtn.setStyle("-fx-background-color: #30336b; -fx-text-fill: white; -fx-background-radius: 15;");
        menuBtn.setOnMouseEntered(e -> menuBtn.setStyle("-fx-background-color: #130f40; -fx-text-fill: white; -fx-background-radius: 15;"));
        menuBtn.setOnMouseExited(e -> menuBtn.setStyle("-fx-background-color: #30336b; -fx-text-fill: white; -fx-background-radius: 15;"));
        menuBtn.setOnAction(e -> new MainMenuFX().start(stage));

        root.getChildren().addAll(icon, msg, movLabel, menuBtn);
        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
    }
}
