/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author MatiasNoa
 */
import java.util.*;

public class Player {
    private final String name;
    private int score = 0;
    private int streak = 0;
    private final List<PowerType> powers = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public int getScore() { return score; }
    public void addScore(int s) { score += s; }
    public int getStreak() { return streak; }
    public void incrementStreak() { streak++; }
    public void resetStreak() { streak = 0; }
    public List<PowerType> getPowers() { return powers; }
    public void addPower(PowerType p) { powers.add(p); }
    public void usePower(PowerType p) { powers.remove(p); }
}