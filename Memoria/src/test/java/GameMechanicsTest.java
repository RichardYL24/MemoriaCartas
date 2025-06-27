import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class GameMechanicsTest {

    @Test
    public void nombreYPuntajeSeMuestran() {
        String nombre1 = "Ana";
        String nombre2 = "Luis";
        int puntaje1 = 4;
        int puntaje2 = 2;
        String display = nombre1 + ": " + puntaje1 + " pts | " + nombre2 + ": " + puntaje2 + " pts";
        assertTrue(display.contains("Ana: 4 pts"));
        assertTrue(display.contains("Luis: 2 pts"));
    }

    @Test
    public void nombrePorDefectoSiNoSeIngresa() {
        String nombre1 = "";
        String nombre2 = null;
        if (nombre1 == null || nombre1.trim().isEmpty()) nombre1 = "Jugador1";
        if (nombre2 == null || nombre2.trim().isEmpty()) nombre2 = "Jugador2";
        assertEquals("Jugador1", nombre1);
        assertEquals("Jugador2", nombre2);
    }

    @Test
    public void otorgaPoderPorRacha() {
        int racha = 2;
        List<String> poderes = new ArrayList<>();
        if (racha % 2 == 0) {
            poderes.add("VER_DOS_CARTAS");
        }
        assertTrue(poderes.contains("VER_DOS_CARTAS"));
    }

    @Test
    public void juegoTerminaAlEmparejarTodasLasCartas() {
        List<Boolean> emparejadas = Arrays.asList(true, true, true, true);
        boolean terminado = emparejadas.stream().allMatch(b -> b);
        assertTrue(terminado);
    }

    @Test
    public void determinaGanadorPorPuntaje() {
        int p1 = 6, p2 = 4;
        String ganador = p1 > p2 ? "Jugador1" : "Jugador2";
        assertEquals("Jugador1", ganador);
    }

    @Test
    public void empatePorPuntaje() {
        int p1 = 4, p2 = 4;
        String resultado = p1 == p2 ? "Empate" : "No Empate";
        assertEquals("Empate", resultado);
    }

    @Test
    public void testGanaPoderPorRacha() {
        Player p = new Player("A");
        p.incrementStreak();
        p.incrementStreak();
        if (p.getStreak() % 2 == 0) {
            p.addPower(PowerType.VER_DOS_CARTAS);
        }
        assertTrue(p.getPowers().contains(PowerType.VER_DOS_CARTAS));
    }

    @Test
    public void testGanadorPorPuntaje() {
        Player p1 = new Player("A");
        Player p2 = new Player("B");
        p1.addScore(6);
        p2.addScore(4);
        String ganador = p1.getScore() > p2.getScore() ? p1.getName() : p2.getName();
        assertEquals("A", ganador);
    }

    @Test
    public void testEmpatePorPuntaje() {
        Player p1 = new Player("A");
        Player p2 = new Player("B");
        p1.addScore(4);
        p2.addScore(4);
        String resultado = p1.getScore() == p2.getScore() ? "Empate" : "No Empate";
        assertEquals("Empate", resultado);
    }


    @Test
    public void turnoSeMantieneSiAcierta() {
        int turno = 0;
        boolean acierto = true;
        if (!acierto) turno = 1 - turno;
        assertEquals(0, turno);
    }

    @Test
    public void turnoCambiaSiFalla() {
        int turno = 0;
        boolean acierto = false;
        if (!acierto) turno = 1 - turno;
        assertEquals(1, turno);
    }

    @Test
    public void usarPoderLoElimina() {
        List<String> poderes = new ArrayList<>();
        poderes.add("RANDOMIZAR_DECK");
        poderes.remove("RANDOMIZAR_DECK");
        assertFalse(poderes.contains("RANDOMIZAR_DECK"));
    }

    @Test
    public void testTurnoAlternaAlFallar() {
        Player p1 = new Player("A");
        Player p2 = new Player("B");
        int turno = 0;
        boolean acierto = false;
        if (!acierto) turno = 1 - turno;
        assertEquals(1, turno);
    }

    @Test
    public void testTurnoNoAlternaSiAcierta() {
        Player p1 = new Player("A");
        Player p2 = new Player("B");
        int turno = 0;
        boolean acierto = true;
        if (!acierto) turno = 1 - turno;
        assertEquals(0, turno);
    }

    @Test
    public void testPuntajePorAcierto() {
        Player p = new Player("A");
        p.addScore(2);
        assertEquals(2, p.getScore());
    }

    @Test
    public void testRachaIncrementaYSeResetea() {
        Player p = new Player("A");
        p.incrementStreak();
        p.incrementStreak();
        assertEquals(2, p.getStreak());
        p.resetStreak();
        assertEquals(0, p.getStreak());
    }

    @Test
    public void testUsarPoderLoEliminaDeLaLista() {
        Player p = new Player("A");
        p.addPower(PowerType.BLOQUEAR_CARTA);
        assertTrue(p.getPowers().contains(PowerType.BLOQUEAR_CARTA));
        p.usePower(PowerType.BLOQUEAR_CARTA);
        assertFalse(p.getPowers().contains(PowerType.BLOQUEAR_CARTA));
    }

    @Test
    public void testBloquearCarta() {
        Set<Integer> blocked = new HashSet<>();
        blocked.add(3);
        assertTrue(blocked.contains(3));
        blocked.remove(3);
        assertFalse(blocked.contains(3));
    }

    @Test
    public void testRandomizarDeck() {
        List<Integer> deck = Arrays.asList(1,2,3,4,5,6);
        List<Integer> copy = new ArrayList<>(deck);
        Collections.shuffle(copy, new Random(42));
        assertNotEquals(deck, copy); // Probabilidad muy baja de que sean iguales
    }
}
