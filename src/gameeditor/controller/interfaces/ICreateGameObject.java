package gameeditor.controller.interfaces;

import java.util.Map;

/**
 * 
 * @author @author Ray Song(ys101)
 *
 */
public interface ICreateGameObject {
//	void createGameObject(double xPos, double yPos, double width, double height, 
//			String imageFileName, Map<String, String> properties);
//	void addToProperties(String key, String value);
	void addPropertiesToGameObject(Map<String, String> properties);
}
