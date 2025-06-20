/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author MatiasNoa
 */

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class GameLogicTest {

    @Test
    public void testEmparejarCartasIguales() {
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
    public void testCartasDiferentesNoEmparejan() {
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
    public void testVictoriaCuandoTodoEmparejado() {
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
    public void testNoVictoriaSiFaltaEmparejar() {
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
    public void testCartaNoSePuedeSeleccionarDosVeces() {
        CardModel c1 = new CardModel(1);
        GameLogic logic = new GameLogic();

        logic.selectCard(c1);
        logic.selectCard(c1); // doble click

        assertFalse(logic.isReadyToCheck());
    }

    @Test
    public void testNoSeSeleccionaTerceraCarta() {
        CardModel c1 = new CardModel(1);
        CardModel c2 = new CardModel(1);
        CardModel c3 = new CardModel(2);
        GameLogic logic = new GameLogic();

        logic.selectCard(c1);
        logic.selectCard(c2);
        logic.selectCard(c3); // esta no debería contar

        assertTrue(logic.isReadyToCheck());
    }

    @Test
    public void testFlipSoloSiNoEstaEmparejada() {
        CardModel c = new CardModel(1);
        c.setMatched(true);
        c.flip();
        assertFalse(c.isFaceUp()); // no debería voltearse
    }

    @Test
    public void testFlipCorrecto() {
        CardModel c = new CardModel(1);
        assertFalse(c.isFaceUp());
        c.flip();
        assertTrue(c.isFaceUp());
        c.flip();
        assertFalse(c.isFaceUp());
    }

    @Test
    public void testNoEmparejarCartasIgualesYaEmparejadas() {
        CardModel c1 = new CardModel(1);
        CardModel c2 = new CardModel(1);
        c1.setMatched(true);
        c2.setMatched(true);
        GameLogic logic = new GameLogic();
        logic.selectCard(c1);
        logic.selectCard(c2);
        assertFalse(logic.isReadyToCheck());
    }

    @Test
    public void testCartasSeMantienenBocaArribaSiEmparejadas() {
        CardModel c1 = new CardModel(1);
        CardModel c2 = new CardModel(1);
        GameLogic logic = new GameLogic();
        logic.selectCard(c1);
        logic.selectCard(c2);
        logic.checkForMatch();
        assertTrue(c1.isMatched());
        assertTrue(c2.isMatched());
        assertTrue(c1.isFaceUp());
        assertTrue(c2.isFaceUp());
    }
    
    @Test
    public void testPerderPorMovimientosAgotados() {
        List<CardModel> cards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            CardModel c = new CardModel(i);
            if (i < 3) c.setMatched(true);
            cards.add(c);
        }

        GameLogic logic = new GameLogic();
        assertFalse(logic.isGameWon(cards));
    }
    
    @Test
    public void testMovimientoNoCuentaSiCartaYaSeleccionada() {
        CardModel c1 = new CardModel(1);
        c1.setMatched(true); // ya emparejada
        GameLogic logic = new GameLogic();

        logic.selectCard(c1); // no debería seleccionar nada

        assertFalse(logic.isReadyToCheck());
    }

}
