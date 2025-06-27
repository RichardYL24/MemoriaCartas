package fx;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MainMenuFX {
    public void start(Stage stage) {
        // Fondo con gradiente y bordes redondeados
        Stop[] stops = new Stop[] {
            new Stop(0, Color.web("#00b894")),
            new Stop(1, Color.web("#0984e3"))
        };
        LinearGradient grad = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);

        VBox root = new VBox(30);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(60));
        root.setBackground(new Background(new BackgroundFill(grad, new CornerRadii(30), Insets.EMPTY)));

        Label title = new Label("Juego de Memoria");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 54));
        title.setTextFill(Color.WHITE);

        Label nivelLabel = new Label("Dificultad:");
        nivelLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        nivelLabel.setTextFill(Color.web("#30336b"));

        ComboBox<String> nivelBox = new ComboBox<>();
        nivelBox.getItems().addAll("Fácil (4x2)", "Medio (4x4)", "Difícil (4x9)");
        nivelBox.getSelectionModel().select(1);
        nivelBox.setStyle("-fx-font-size: 18px;");

        TextField nombre1 = new TextField();
        nombre1.setPromptText("Nombre del Jugador 1");
        nombre1.setStyle("-fx-font-size: 18px;");
        TextField nombre2 = new TextField();
        nombre2.setPromptText("Nombre del Jugador 2");
        nombre2.setStyle("-fx-font-size: 18px;");

        Button pvpBtn = new Button("Jugar 1 vs 1");
        pvpBtn.setPrefWidth(250);
        pvpBtn.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        pvpBtn.setStyle("-fx-background-color: #30336b; -fx-text-fill: white; -fx-background-radius: 15;");
        pvpBtn.setOnMouseEntered(e -> pvpBtn.setStyle("-fx-background-color: #130f40; -fx-text-fill: white; -fx-background-radius: 15;"));
        pvpBtn.setOnMouseExited(e -> pvpBtn.setStyle("-fx-background-color: #30336b; -fx-text-fill: white; -fx-background-radius: 15;"));

        pvpBtn.setOnAction(e -> {
            int seleccion = nivelBox.getSelectionModel().getSelectedIndex();
            int pares;
            if (seleccion == 0) pares = 4;
            else if (seleccion == 1) pares = 8;
            else pares = 18;

            String n1 = nombre1.getText().trim().isEmpty() ? "Jugador 1" : nombre1.getText().trim();
            String n2 = nombre2.getText().trim().isEmpty() ? "Jugador 2" : nombre2.getText().trim();

            new GameboardFX().start(stage, pares, n1, n2);
        });

        VBox form = new VBox(18, nivelLabel, nivelBox, nombre1, nombre2, pvpBtn);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(40));
        form.setSpacing(18);
        form.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(25), Insets.EMPTY)));
        form.setEffect(new javafx.scene.effect.DropShadow(30, Color.rgb(44, 62, 80, 0.18)));

        root.getChildren().addAll(title, form);

        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("Memoria - Menú Principal");
        stage.show();
    }
}