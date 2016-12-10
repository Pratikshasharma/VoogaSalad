package gameengine.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import com.sun.javafx.scene.traversal.Direction;
import exception.ScrollDirectionNotFoundException;
import exception.ScrollTypeNotFoundException;
import gameeditor.xml.XMLSerializer;
import gameengine.controller.interfaces.CommandInterface;
import gameengine.controller.interfaces.GameHandler;
import gameengine.controller.interfaces.RGInterface;
import gameengine.controller.interfaces.RuleActionHandler;
import gameengine.model.*;
import gameengine.model.interfaces.Scrolling;
import gameengine.view.GameEngineUI;
import gameengine.view.HighScoreScreen;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Duration;
import objects.*;
import utils.ReflectionUtil;

/**
 * @author Soravit Sophastienphong, Eric Song, Brian Zhou, Chalena Scholl, Noel
 *         Moon
 */

public class GameEngineController implements CommandInterface {
	public static final double FRAMES_PER_SECOND = 60;
	public static final double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1 / FRAMES_PER_SECOND;

	private String xmlData;
	private XMLSerializer serializer;

	private GameEngineUI gameEngineView;
	private GameEngineBackend backend;

	private boolean multiplayer;
	private boolean isServer;

	public GameEngineController() {
//		this.multiplayer = multiplayer;
//		this.isServer = isServer;
		this.multiplayer = true;
		this.isServer = false;
		serializer = new XMLSerializer();
	}

	public boolean startGame(String xmlData) {
		Game currentGame = serializer.getGameFromString(xmlData);
		if (currentGame.getCurrentLevel() == null || currentGame.getCurrentLevel().getPlayers().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Cannot start game.");
			alert.setContentText("You must create a level with a main character to start a game.");
			alert.showAndWait();
			return false;
		}
		if (!multiplayer) {
			startServerGame(currentGame);
			startClientGame();
		} else if (isServer) {
			startServerGame(currentGame);
		} else {
			startClientGame();
		}
		return true;
	}

	public void startServerGame(Game currentGame) {
		backend = new GameEngineBackend();
		backend.startGame(currentGame);
	}

	public void startClientGame() {
		gameEngineView = new GameEngineUI(this, event -> reset());
		while(!gameEngineView.gameLoadedFromServer()){
			System.out.println("waiting");
		}
		System.out.print("done");
		beginUI();
	}

	public void beginUI() {
		gameEngineView.initLevel();
		gameEngineView.mapKeys();
		gameEngineView.setupKeyFrameAndTimeline(GameEngineController.MILLISECOND_DELAY);
	}


	public Scene getScene() {
		return gameEngineView.getScene();
	}

	@Override
	public void reset() {
		stop();
		gameEngineView.resetGameScreen();
		startGame(xmlData);
		System.out.println("aaa " + xmlData);
	}

	@Override
	public void stop() {
		gameEngineView.stop();
	}

	@Override
	public void endGame() {
		gameEngineView.endGame();
	}


	// private void checkProjectileDistance(){
	// ProjectileProperties properties =
	// currentGame.getCurrentLevel().getMainCharacter().getProjectileProperties();
	// for(GameObject
	// projectile:currentGame.getCurrentLevel().getProjectiles()){
	// if(properties.getDirection().equals(Direction.RIGHT) ||
	// properties.getDirection().equals(Direction.LEFT)){
	// if(projectile.getXDistanceMoved() >= properties.getRange()){
	// removeObject(projectile);
	// }
	// }else{
	// if(projectile.getYDistanceMoved() >= properties.getRange()){
	// removeObject(projectile);
	// }
	// }
	// }
	// }
}