/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author MatiasNoa
 */


// -------------------------
// ✅ 1. ATDD - Acceptance Test-Driven Development
// -------------------------
// Historias de Usuario simuladas antes del desarrollo

/**
 * Historia 1: Como jugador quiero que al hacer clic en dos cartas iguales,
 * estas permanezcan visibles para poder avanzar en el juego.
 *
 * Criterio de aceptación:
 * - Si selecciono dos cartas con el mismo ID, deben permanecer volteadas.
 */

/**
 * Historia 2: Como jugador quiero que el juego me notifique cuando gane o pierda,
 * para saber cuándo termina el juego.
 *
 * Criterio de aceptación:
 * - El juego muestra pantalla de victoria cuando todas las cartas están emparejadas.
 * - El juego muestra pantalla de derrota al pasar el límite de tiempo.
 */

/**
 * Historia 3: Como jugador quiero que las cartas se volteen cuando hago clic,
 * para saber qué carta elegí.
 */

/**
 * Historia 4: Como jugador quiero que el botón de volver al menú me saque del juego actual,
 * para poder reiniciar o cambiar de nivel.
 */

/**
 * Historia 5: Como jugador quiero que el juego me impida seleccionar más de dos cartas al mismo tiempo,
 * para evitar errores o comportamientos inesperados.
 */

/**
 * Historia 6: Como jugador quiero poder elegir entre tres niveles de dificultad,
 * para ajustar la complejidad del juego.
 */

/**
 * Historia 7: Como desarrollador quiero que la lógica del juego esté separada de la interfaz,
 * para poder probarla fácilmente.
 */


// -------------------------
// ✅ 2. BDD - Behavior Driven Development
// -------------------------
// Escenarios Gherkin simulados antes del desarrollo

/*
Feature: Emparejamiento de cartas en el juego de memoria

Scenario: Emparejar dos cartas iguales
  Given una carta con ID 5
  And otra carta con ID 5
  When selecciono ambas cartas
  Then ambas deben marcarse como emparejadas

Scenario: Seleccionar dos cartas diferentes
  Given una carta con ID 1
  And otra carta con ID 2
  When selecciono ambas cartas
  Then ambas deben voltearse nuevamente

Scenario: Seleccionar una tercera carta antes de resolver las anteriores
  Given dos cartas seleccionadas
  When intento seleccionar una tercera carta
  Then no debería permitirse hasta que se resuelva la comparación

Scenario: Ganar el juego
  Given todas las cartas están emparejadas
  When no hay más cartas visibles
  Then se muestra un mensaje de victoria

Scenario: Perder por tiempo
  Given el temporizador llega a 120 segundos
  When el juego no ha terminado
  Then se muestra una pantalla de derrota
*/


// -------------------------
// ✅ 3. TDD - Test Driven Development (JUnit)
// -------------------------
// Tests creados ANTES de desarrollar la lógica

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
}
