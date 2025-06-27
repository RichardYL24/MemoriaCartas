package fx;

import java.util.*;
import java.io.File;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.animation.*;
import javafx.util.Duration;
import fx.CardModel;
import fx.GameLogic;

public class GameboardFX {
    private int movimientosRestantes;
    private int MAX_MOVIMIENTOS;
    private int currentPlayer = 0;
    private PlayerFX[] players;
    private int pares;
    private Label jugador1Puntaje, jugador2Puntaje, turnoValor, movValor, poderesLabel;
    private List<CardModel> cardModels = new ArrayList<>();
    private List<CardFX> cardFXs = new ArrayList<>();
    private PowerFX powerToUse = null;
    private Random random = new Random();
    private GameLogic logic = new GameLogic();
    private boolean isBlocked = false;

    public void start(Stage stage, int pares, String nombre1, String nombre2) {
        this.pares = pares;
        this.MAX_MOVIMIENTOS = pares * 2 + 5;
        this.movimientosRestantes = MAX_MOVIMIENTOS;
        this.players = new PlayerFX[] {
            new PlayerFX(nombre1, null, Color.LIGHTBLUE),
            new PlayerFX(nombre2, null, Color.LIGHTPINK)
        };

        // Fondo con gradiente y bordes redondeados
        Stop[] stops = new Stop[] {
            new Stop(0, Color.web("#00b894")),
            new Stop(1, Color.web("#0984e3"))
        };
        LinearGradient grad = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setBackground(new Background(new BackgroundFill(grad, new CornerRadii(30), Insets.EMPTY)));

        Font fuente = Font.font("Arial", 22);

        // --- Encabezado Mejorado ---
        HBox header = new HBox(30);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(15));
        header.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(20), Insets.EMPTY)));
        header.setEffect(new javafx.scene.effect.DropShadow(20, Color.rgb(44, 62, 80, 0.18)));

        Label jugador1Nombre = new Label(players[0].getName());
        jugador1Nombre.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        jugador1Nombre.setTextFill(Color.web("#30336b"));
        jugador1Puntaje = new Label("Puntos: " + getScore(0));
        jugador1Puntaje.setFont(Font.font("Arial", 18));
        jugador1Puntaje.setTextFill(Color.web("#0984e3"));
        HBox jugador1Box = new HBox(10);
        jugador1Box.setAlignment(Pos.CENTER);
        jugador1Box.getChildren().addAll(jugador1Nombre, jugador1Puntaje);

        Label jugador2Nombre = new Label(players[1].getName());
        jugador2Nombre.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        jugador2Nombre.setTextFill(Color.web("#30336b"));
        jugador2Puntaje = new Label("Puntos: " + getScore(1));
        jugador2Puntaje.setFont(Font.font("Arial", 18));
        jugador2Puntaje.setTextFill(Color.web("#e17055"));
        HBox jugador2Box = new HBox(10);
        jugador2Box.setAlignment(Pos.CENTER);
        jugador2Box.getChildren().addAll(jugador2Nombre, jugador2Puntaje);

        Label turnoTitulo = new Label("Turno");
        turnoTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        turnoTitulo.setTextFill(Color.web("#636e72"));
        turnoValor = new Label(players[currentPlayer].getName());
        turnoValor.setFont(Font.font("Arial", 22));
        turnoValor.setTextFill(Color.web("#00b894"));
        HBox turnoBox = new HBox(10);
        turnoBox.setAlignment(Pos.CENTER);
        turnoBox.getChildren().addAll(turnoTitulo, turnoValor);

        Label movTitulo = new Label("Movimientos restantes");
        movTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        movTitulo.setTextFill(Color.web("#636e72"));
        movValor = new Label(String.valueOf(movimientosRestantes));
        movValor.setFont(Font.font("Arial", 22));
        movValor.setTextFill(Color.web("#fdcb6e"));
        HBox movimientosBox = new HBox(10);
        movimientosBox.setAlignment(Pos.CENTER);
        movimientosBox.getChildren().addAll(movTitulo, movValor);

        Button backButton = new Button("⟵ Menú");
        backButton.setFont(Font.font("Arial", 18));
        backButton.setStyle("-fx-background-color: #30336b; -fx-text-fill: white; -fx-background-radius: 15;");
        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-background-color: #130f40; -fx-text-fill: white; -fx-background-radius: 15;"));
        backButton.setOnMouseExited(e -> backButton.setStyle("-fx-background-color: #30336b; -fx-text-fill: white; -fx-background-radius: 15;"));
        backButton.setOnAction(e -> new MainMenuFX().start(stage));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        header.getChildren().addAll(jugador1Box, turnoBox, movimientosBox, spacer, jugador2Box, backButton);
        root.setTop(header);

        // --- Panel de poderes ---
        HBox poderesPanel = new HBox(20);
        poderesPanel.setAlignment(Pos.CENTER_LEFT);
        poderesPanel.setPadding(new Insets(10));
        poderesPanel.setBackground(new Background(new BackgroundFill(Color.web("#f5f6fa"), new CornerRadii(15), Insets.EMPTY)));
        poderesPanel.setEffect(new javafx.scene.effect.DropShadow(15, Color.rgb(44, 62, 80, 0.12)));
        poderesLabel = new Label();
        poderesLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        poderesLabel.setTextFill(Color.web("#30336b"));
        Button usarPoderBtn = new Button("Usar Poder");
        usarPoderBtn.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        usarPoderBtn.setStyle("-fx-background-color: #30336b; -fx-text-fill: white; -fx-background-radius: 15;");
        usarPoderBtn.setOnMouseEntered(e -> usarPoderBtn.setStyle("-fx-background-color: #130f40; -fx-text-fill: white; -fx-background-radius: 15;"));
        usarPoderBtn.setOnMouseExited(e -> usarPoderBtn.setStyle("-fx-background-color: #30336b; -fx-text-fill: white; -fx-background-radius: 15;"));
        usarPoderBtn.setOnAction(e -> seleccionarPoder(stage));
        poderesPanel.getChildren().addAll(poderesLabel, usarPoderBtn);
        root.setBottom(poderesPanel);

        // --- Tablero de cartas ---
        int totalCards = pares * 2;
        int cols, rows;
        if (pares == 18) { // Difícil: 4x9
            cols = 9;
            rows = 4;
        } else if (pares == 4) { // Para 4x2 fácil
            cols = 4;
            rows = 2;
        } else {
            cols = (int) Math.ceil(Math.sqrt(totalCards));
            rows = (int) Math.ceil((double) totalCards / cols);
        }

        GridPane gridPanel = new GridPane();
        gridPanel.setHgap(15);
        gridPanel.setVgap(15);
        gridPanel.setAlignment(Pos.CENTER);
        gridPanel.setPadding(new Insets(30));
        gridPanel.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(20), Insets.EMPTY)));
        gridPanel.setEffect(new javafx.scene.effect.DropShadow(20, Color.rgb(44, 62, 80, 0.10)));

        cardModels.clear();
        cardFXs.clear();
        for (int i = 1; i <= pares; i++) {
            cardModels.add(new CardModel(i));
            cardModels.add(new CardModel(i));
        }
        Collections.shuffle(cardModels);

        // Ruta absoluta al directorio del proyecto
        String projectDir = System.getProperty("user.dir");

        for (int idx = 0; idx < cardModels.size(); idx++) {
            CardModel model = cardModels.get(idx);
            String imgPath = projectDir + File.separator + "images" + File.separator + model.getId() + ".jpg";
            String backPath = projectDir + File.separator + "images" + File.separator + "back.jpg";
            CardFX card = new CardFX(imgPath, backPath, model.getId());
            final int cardIdx = idx;
            card.setOnMouseClicked(e -> onCardClick(card, cardIdx, stage));
            cardFXs.add(card);
            gridPanel.add(card, idx % cols, idx / cols);
        }

        // Si el tablero es grande, usa ScrollPane
        ScrollPane scroll = new ScrollPane(gridPanel);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        root.setCenter(scroll);

        updateUIInfo();

        Scene scene = new Scene(root, 1100, 800);
        stage.setScene(scene);
        stage.setTitle("Juego de Memoria PvP");
        stage.show();
    }

    private void updateUIInfo() {
        jugador1Puntaje.setText("Puntos: " + getScore(0));
        jugador2Puntaje.setText("Puntos: " + getScore(1));
        turnoValor.setText(players[currentPlayer].getName());
        movValor.setText(String.valueOf(movimientosRestantes));
        // --- Presentación visual mejorada de poderes ---
        List<PowerFX> powers = getPowers(currentPlayer);
        if (powers.isEmpty()) {
            poderesLabel.setText("Poderes: ");
            poderesLabel.setGraphic(null);
        } else {
            HBox powersBox = new HBox(8);
            for (PowerFX p : powers) {
                Label lbl = new Label(poderToString(p));
                lbl.setPadding(new Insets(4, 12, 4, 12));
                lbl.setFont(Font.font("Arial", FontWeight.BOLD, 15));
                lbl.setStyle("-fx-background-radius: 8; -fx-border-radius: 8; -fx-border-width: 1; -fx-border-color: #dfe6e9;");
                switch (p) {
                    case VER_DOS_CARTAS:
                        lbl.setTextFill(Color.web("#00b894"));
                        lbl.setStyle(lbl.getStyle() + "-fx-background-color: #eafaf1;");
                        break;
                    case ROBAR_TURNO:
                        lbl.setTextFill(Color.web("#fdcb6e"));
                        lbl.setStyle(lbl.getStyle() + "-fx-background-color: #fff9e3;");
                        break;
                    case RANDOMIZAR_DECK:
                        lbl.setTextFill(Color.web("#0984e3"));
                        lbl.setStyle(lbl.getStyle() + "-fx-background-color: #eaf4fb;");
                        break;
                }
                powersBox.getChildren().add(lbl);
            }
            poderesLabel.setText("Poderes: ");
            poderesLabel.setGraphic(powersBox);
        }
    }

    // Métodos auxiliares para puntaje y poderes (puedes implementar almacenamiento real si lo deseas)
    private int[] scores = new int[]{0,0};
    private int[] streaks = new int[]{0,0};
    private List<PowerFX>[] powers = new List[]{new ArrayList<>(), new ArrayList<>()};

    private int getScore(int idx) { return scores[idx]; }
    private void addScore(int idx, int s) { scores[idx] += s; }
    private int getStreak(int idx) { return streaks[idx]; }
    private void incrementStreak(int idx) { streaks[idx]++; }
    private void resetStreak(int idx) { streaks[idx] = 0; }
    private List<PowerFX> getPowers(int idx) { return powers[idx]; }
    private void addPower(int idx, PowerFX p) { powers[idx].add(p); }
    private void usePower(int idx, PowerFX p) { powers[idx].remove(p); }

    private void seleccionarPoder(Stage stage) {
        List<PowerFX> lista = getPowers(currentPlayer);
        if (lista.isEmpty()) {
            showAlert("No tienes poderes disponibles.");
            return;
        }
        ChoiceDialog<PowerFX> dialog = new ChoiceDialog<>(lista.get(0), lista);
        dialog.setTitle("Poderes");
        dialog.setHeaderText("Selecciona un poder para usar:");
        dialog.setContentText("Poder:");
        Optional<PowerFX> result = dialog.showAndWait();
        result.ifPresent(seleccion -> {
            powerToUse = seleccion;
            aplicarPoder(seleccion, stage);
            usePower(currentPlayer, seleccion);
            updateUIInfo();
        });
    }

    private void aplicarPoder(PowerFX poder, Stage stage) {
        switch (poder) {
            case VER_DOS_CARTAS:
                verDosCartas();
                break;
            case ROBAR_TURNO:
                showAlert("¡Robaste el turno! Juegas de nuevo.");
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
        updateAllCards();
        PauseTransition pause = new PauseTransition(Duration.millis(1200));
        pause.setOnFinished(e -> {
            ocultas.get(0).flip();
            ocultas.get(1).flip();
            updateAllCards();
        });
        pause.play();
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
        // Mezcla los modelos
        Collections.shuffle(cartasNoVisibles);
        // Asigna los modelos mezclados y actualiza los CardFX correspondientes
        for (int j = 0; j < indicesNoVisibles.size(); j++) {
            int idx = indicesNoVisibles.get(j);
            cardModels.set(idx, cartasNoVisibles.get(j));
            // Actualiza el modelo en el CardFX correspondiente
            cardFXs.get(idx).setOnMouseClicked(null); // Limpia eventos previos
            CardModel newModel = cartasNoVisibles.get(j);
            cardFXs.get(idx).setOnMouseClicked(e -> onCardClick(cardFXs.get(idx), idx, null));
            // Si tienes un método para actualizar visual, puedes llamarlo aquí si es necesario
        }
        updateAllCards();
        showAlert("¡El mazo del rival ha sido mezclado!");
    }

    private void onCardClick(CardFX card, int cardIdx, Stage stage) {
        if (isBlocked) return;
        if (logic.isReadyToCheck()) return;

        CardModel model = cardModels.get(cardIdx);
        if (model.isFaceUp() || model.isMatched()) return;

        logic.selectCard(model);
        card.flip();

        if (logic.isReadyToCheck()) {
            isBlocked = true;
            movimientosRestantes--;
            updateUIInfo();

            PauseTransition delay = new PauseTransition(Duration.millis(1000));
            delay.setOnFinished(e -> {
                boolean acierto = logic.checkForMatch();
                updateAllCards();

                if (acierto) {
                    addScore(currentPlayer, 2);
                    incrementStreak(currentPlayer);
                    if (getStreak(currentPlayer) > 0 && getStreak(currentPlayer) % 2 == 0) {
                        PowerFX[] posibles = {PowerFX.VER_DOS_CARTAS, PowerFX.ROBAR_TURNO, PowerFX.RANDOMIZAR_DECK};
                        PowerFX nuevo = posibles[random.nextInt(posibles.length)];
                        addPower(currentPlayer, nuevo);
                        javafx.application.Platform.runLater(() -> {
                            Alert poderAlert = new Alert(Alert.AlertType.INFORMATION, 
                                "¡" + players[currentPlayer].getName() + " ganó un poder: " + poderToString(nuevo) + "!", ButtonType.OK);
                            poderAlert.setHeaderText("¡Nuevo poder!");
                            poderAlert.show();
                        });
                    }
                } else {
                    resetStreak(currentPlayer);
                }

                boolean quedanJugables = cardModels.stream().anyMatch(c -> !c.isMatched() && !c.isFaceUp());
                if (logic.isGameWon(cardModels) || !quedanJugables) {
                    mostrarResultados(stage, true);
                } else if (movimientosRestantes <= 0) {
                    mostrarResultados(stage, false);
                } else {
                    if (!acierto && powerToUse != PowerFX.ROBAR_TURNO) {
                        currentPlayer = 1 - currentPlayer;
                    }
                    powerToUse = null;
                    updateUIInfo();
                }
                isBlocked = false;
            });
            delay.play();
        }
    }

    private void mostrarResultados(Stage stage, boolean victoria) {
        int score0 = getScore(0);
        int score1 = getScore(1);
        String ganador = null;
        boolean empate = false;
        if (score0 > score1) {
            ganador = players[0].getName();
        } else if (score1 > score0) {
            ganador = players[1].getName();
        } else {
            empate = true;
        }
        // Pantalla de resultado personalizada
        new ResultScreenFX().show(stage, ganador, empate, movimientosRestantes);
    }

    private String poderToString(PowerFX poder) {
        switch (poder) {
            case VER_DOS_CARTAS: return "Ver dos cartas";
            case ROBAR_TURNO: return "Robar turno";
            case RANDOMIZAR_DECK: return "Randomizar mazo";
            default: return poder.name();
        }
    }

    private void updateAllCards() {
        for (int i = 0; i < cardFXs.size(); i++) {
            CardFX card = cardFXs.get(i);
            CardModel model = cardModels.get(i);
            if (model.isFaceUp() || model.isMatched()) {
                if (!card.isFaceUp()) card.flip();
            } else {
                if (card.isFaceUp()) card.flip();
            }
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }
}