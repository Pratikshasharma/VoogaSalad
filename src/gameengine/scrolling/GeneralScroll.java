package gameengine.scrolling;

import java.util.List;

import objects.GameObject;


/**
 * This is a general scrolling class that shifts all objects on the screen to 
 * give the appearance of movement to mainCharacter
 * @author Chalena Scholl, 
 */
public class GeneralScroll {
	
	    public void scrollUp(List<GameObject> gameObjects, GameObject mainChar, double speed){
			for(GameObject obstacle: gameObjects){
	            if (obstacle== mainChar){
	                continue;
	            }
				double newPos = obstacle.getYPosition() + speed;
				obstacle.setYPosition(newPos);
			}
	    }

	    public void scrollDown(List<GameObject> gameObjects, GameObject mainChar, double speed){
	    	for(GameObject obstacle: gameObjects){
	            if (obstacle== mainChar){
	                continue;
	            }
				double newPos = obstacle.getYPosition() - speed;
				obstacle.setYPosition(newPos);
	    	}
	    }

	    public void scrollRight(List<GameObject> gameObjects, GameObject mainChar, double speed){
	        for(GameObject obstacle: gameObjects){
	            if (obstacle== mainChar){
	                continue;
	            }
	            double newPos = obstacle.getXPosition() - speed;
	            obstacle.setXPosition(newPos);
	        }
	    }

	    public void scrollLeft(List<GameObject> gameObjects, GameObject mainChar, double speed){
	        for(GameObject obstacle: gameObjects){
	            if (obstacle== mainChar){
	                continue;
	            }
	            double newPos = obstacle.getXPosition() + speed;
	            obstacle.setXPosition(newPos);
	        }
	    }


}
