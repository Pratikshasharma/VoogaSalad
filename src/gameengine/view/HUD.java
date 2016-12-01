/**
 * 
 */
package gameengine.view;

import java.util.TreeMap;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import objects.Level;

/**
 * @author Noel Moon (nm142)
 *
 */
public class HUD {
	
	private HBox myHUD;
	private TreeMap<String, String> myStatMap;

	public HUD() {
		myHUD = new HBox();
		myStatMap = new TreeMap<String, String>();
	}
	
	public HBox getHUD() {
		return myHUD;
	}
	
	public void update(Level level) {
		myHUD.getChildren().clear();
		Text text = new Text("Score: " + Integer.toString(level.getScore()));
		myHUD.getChildren().add(text);
	}
	
	public void addStat(String statName, String statValue) {
		myStatMap.put(statName, statValue);
	}
}
