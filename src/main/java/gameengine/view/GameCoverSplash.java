package gameengine.view;

import frontend.util.ButtonTemplate;
import general.MainController;
import general.NodeFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import objects.GameObject;
import objects.Level;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Delia on 12/11/2016.
 */
public class GameCoverSplash {
    private String title, character, background;
    private static final String COVER_SPLASH_STYLE = "default.css";
    private static final int COVER_WIDTH = 700;
    private ArrayList<GameObject> playahs;
    private Scene coverScene;
    private Pane myWindow;
    private MainController mainController;
    private NodeFactory myFactory = new NodeFactory();

    public GameCoverSplash(Level level, MainController myMainController){
        System.out.println(level + " initialized yo");
        this.playahs = (ArrayList) level.getPlayers();
        this.background = level.getBackgroundFilePath();
        this.title = level.getTitle();
        this.mainController = myMainController;
    }

    public Scene createSplashScene(){
        myWindow = new Pane();
        int titleWidth = 100 + (30 * title.length());
        if(titleWidth < COVER_WIDTH) titleWidth = COVER_WIDTH;
        coverScene = new Scene(myWindow, titleWidth, 775);
        coverScene.getStylesheets().add(COVER_SPLASH_STYLE);
        Image backg = new Image(background);
        ImageView backgroundImage = new ImageView(backg);
        backgroundImage.setPreserveRatio(true);
        backgroundImage.setFitHeight(775);
        Text titleText = myFactory.bigNameTitle(title, 35, 100);
        ButtonTemplate startTemp = new ButtonTemplate("GalleryGameEngine", 250, 365);
        Button start = startTemp.getButton();
        start.setOnMouseClicked(e -> mainController.startPlaying());
        System.out.println(playahs.size() + " playahs");
        myWindow.getChildren().addAll(backgroundImage, titleText, start);
        addPlayahs();
        return coverScene;
    }

    private void addPlayahs(){
        for (int i = 0; i < playahs.size(); i++){
            Image playah = new Image(myFactory.getUserDirectorySpritePrefix() + playahs.get(i).getImageFileName());
            ImageView newPlayah = new ImageView(playah);
            newPlayah.setPreserveRatio(true);
            newPlayah.setFitHeight(200);
            newPlayah.setTranslateX(50 + (i * 150));
            newPlayah.setTranslateY(500);
            myWindow.getChildren().add(newPlayah);
        }
    }

    public String getTitle(){
        return title;
    }
}
