// Importaciones necesarias para pruebas con JUnit
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

// Importación de clases del juego
import fx.GameLogic;
import fx.CardModel;
import java.util.*;

public class GameFXTest {

    @Test
    public void cartasIgualesSeEmparejan() {
        CardModel c1 = new CardModel(1);
        CardModel c2 = new CardModel(1);
        GameLogic logic = new GameLogic();

        logic.selectCard(c1);
        logic.selectCard(c2);

        assertTrue(logic.isReadyToCheck());
        assertTrue(logic.checkForMatch());
        assertTrue(c1.isMatched());
        assertTrue(c2.isMatched());
    }

    @Test
    public void cartasDiferentesNoSeEmparejan() {
        CardModel c1 = new CardModel(1);
        CardModel c2 = new CardModel(2);
        GameLogic logic = new GameLogic();

        logic.selectCard(c1);
        logic.selectCard(c2);

        assertTrue(logic.isReadyToCheck());
        assertFalse(logic.checkForMatch());
        assertFalse(c1.isMatched());
        assertFalse(c2.isMatched());
        assertFalse(c1.isFaceUp());
        assertFalse(c2.isFaceUp());
    }

    @Test
    public void juegoGanadoSiTodasEstanEmparejadas() {
        List<CardModel> cards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            CardModel a = new CardModel(i);
            a.setMatched(true);
            cards.add(a);
        }

        GameLogic logic = new GameLogic();

        assertTrue(logic.isGameWon(cards));
    }

    @Test
    public void juegoNoGanadoSiFaltaUna() {
        List<CardModel> cards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            CardModel a = new CardModel(i);
            if (i == 2) a.setMatched(false);
            else a.setMatched(true);
            cards.add(a);
        }

        GameLogic logic = new GameLogic();

        assertFalse(logic.isGameWon(cards));
    }

    @Test
    public void noSeleccionarCartaEmparejada() {
        CardModel c = new CardModel(1);
        c.setMatched(true);
        GameLogic logic = new GameLogic();

        logic.selectCard(c);

        assertFalse(logic.isReadyToCheck());
    }

    @Test
    public void noSeleccionarMismaCartaDosVeces() {
        CardModel c = new CardModel(1);
        GameLogic logic = new GameLogic();

        logic.selectCard(c);
        logic.selectCard(c);

        assertFalse(logic.isReadyToCheck());
    }

    @Test
    public void terceraCartaNoEsValida() {
        CardModel c1 = new CardModel(1);
        CardModel c2 = new CardModel(2);
        CardModel c3 = new CardModel(3);
        GameLogic logic = new GameLogic();

        logic.selectCard(c1);
        logic.selectCard(c2);
        logic.selectCard(c3);

        assertTrue(logic.isReadyToCheck());
    }

    @Test
    public void noVoltearCartaEmparejada() {
        CardModel c = new CardModel(1);
        c.setMatched(true);

        c.flip();

        assertFalse(c.isFaceUp());
    }

    @Test
    public void flipFuncionaCorrectamente() {
        CardModel c = new CardModel(1);

        assertFalse(c.isFaceUp());

        c.flip();
        assertTrue(c.isFaceUp());

        c.flip();
        assertFalse(c.isFaceUp());
    }
}
