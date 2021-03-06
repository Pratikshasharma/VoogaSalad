package objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.input.KeyCode;
import objects.interfaces.IGame;
import sun.management.counter.perf.PerfLongArrayCounter;

/**
 * Created by Soravit on 11/18/2016.
 * @ author Soravit, pratiksha Sharma
 */
public class Game implements IGame{

    private String name;
    private Map<Integer, Level> levels;
    private Level currentLevel;
    private List<Player> players;

    public Game(String name) {
        levels = new HashMap<Integer,Level>();
        players = new ArrayList<>();
        this.name = name;
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public void removePlayer(Player player){
        players.remove(player);
    }

    public List<Player> getPlayers(){
        return players;
    }

    public void addLevel(Level level) {
        levels.put(level.getLevel(),level);
    }

    public void removeLevel(int level) {
        levels.remove(level);
    }

    public Level getCurrentLevel(){
        return this.currentLevel;
    }

    public void setCurrentLevel(Level currentLevel){
        this.currentLevel = currentLevel;
    }

    public void setGameName(String name){
        this.name = name;
    }

    public int getNumberOfLevels(){
        return levels.size();
    }

    public String getGameName(){
        return this.name;
    }

    public Level getLevelByIndex(int index){
        return levels.get(index);
    }

    public Map<KeyCode, String> getAllControls(){
        Map<KeyCode, String> controls = new HashMap<KeyCode, String>();
        for(Player player : players){
            controls.putAll(player.getControls());
        }
        return controls;
    }
}
