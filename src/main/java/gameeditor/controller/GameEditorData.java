package gameeditor.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import gameeditor.commanddetails.DetailDefaultsResources;
import gameeditor.commanddetails.DetailResources;
import gameeditor.commanddetails.ISelectDetail;
import gameeditor.controller.interfaces.IGameEditorData;
import gameengine.view.GameScreen;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import objects.GameObject;
import objects.Player;
import objects.ProjectileProperties;
import objects.RandomGeneration;
import objects.ScrollType;
import objects.interfaces.IGame;
import objects.interfaces.ILevel;

/**
 * @author pratikshasharma, John Martin
 */

public class GameEditorData implements IGameEditorData{
    private ArrayList<Map<String, String>> mySpriteTypes = new ArrayList<Map<String, String>>();
    private ArrayList<Map<String,String>> myImageViewObjectMap = new ArrayList<Map<String,String>>();
    private ArrayList<Map<String,String>> myMainCharImageViewMaps= new ArrayList<Map<String,String>>();
    private List<Map<String,String>>myProjectileProperties = new ArrayList<Map<String,String>>();
    private Map<String,Map<String,String>> myProjectileObjects = new HashMap<String,Map<String,String>>();

    private ILevel myLevel;
    private String mainCharacterImageFilePath;
    private IGame myGame;

    public GameEditorData(ILevel level, IGame myGameInterface){
        myLevel = level;
        myGame = myGameInterface;
    }


    public void storeType(Map<String, String> typeMap){
        mySpriteTypes.add(typeMap);
    }

    @Override
    public void storeMainCharater (Map<String, String> myMainCharMap) {
        // TODO Auto-generated method stub
        myMainCharImageViewMaps.add(myMainCharMap);
    }

    public Map<String, String> getType(String inputTypeName){
        for (Map<String, String> type : mySpriteTypes){
            String testTypeName = type.get(DetailResources.TYPE_NAME.getResource());
            if (inputTypeName.equals(testTypeName)){
                return type;
            }
        }
        return null;
    }
    
    

    public ArrayList<String> getTypes(){
        ArrayList<String> types = new ArrayList<String>();
        for (Map<String, String> type : mySpriteTypes){
            String typeName = type.get(DetailResources.TYPE_NAME.getResource());
            types.add(typeName);
        }
        return types;
    }

    public ArrayList<String> getImageViews(){
        ArrayList<String> views = new ArrayList<String>();
        for(Map<String,String> map: myImageViewObjectMap){
            String imageview = map.get(DetailResources.IMAGEVIEW_KEY.getResource());
            views.add(imageview);
        }
        return views;
    }


    @Override
    public Map<String, String> getViewMap (String viewName) {
        return getDesiredMap(DetailResources.IMAGEVIEW_KEY.getResource(),myImageViewObjectMap,viewName);
    } 

    private Map<String,String> getDesiredMap(String key, ArrayList<Map<String,String>> mapList, String value){
        for(Map<String,String> map: mapList){
            String valueFromMap = map.get(key);
            if (value.equals(valueFromMap)){
                return map;
            }
        }
        return null;
    }

    public Map<String,String> getMainCharMap(String imageViewName){
        return getDesiredMap(DetailResources.IMAGEVIEW_KEY.getResource(),myMainCharImageViewMaps,imageViewName);
    }

    @Override
    public Map<String,String> createViewMap (String typeName, String imageViewString) {
        Map<String,String> type = getType(typeName);
        Map<String,String> viewMap = new HashMap<String,String>(type);
        viewMap.put(DetailResources.IMAGEVIEW_KEY.getResource(), imageViewString);
        return viewMap;
    }

    public void storeImageViewMap(Map<String,String> viewMap){
        myImageViewObjectMap.add(viewMap);
    }

    public void addRandomGeneration(String type, List<TextArea> myRandomGenerationParameters){
        Map<String,String> properties=  getType(type);
        Map<String,String> propertiesMap = getPropertiesMap(properties);
        addRandomGeneration(propertiesMap, myRandomGenerationParameters);
        mySpriteTypes.remove(properties);
    }

    
    public void addProjectileProperties(String typeName, Map<String,String> myMap){
        myProjectileObjects.put(typeName, myMap);
    }
    
    private void addRandomGeneration(Map<String,String> properties, List<TextArea>myRandomGenParameters){
        Integer num = Integer.parseInt(myRandomGenParameters.get(0).getText());
        if(num==0){num=5;}
        Integer xMin = Integer.parseInt(myRandomGenParameters.get(1).getText());
        if(xMin==0){xMin=(int) (GameScreen.screenWidth/5);}
        Integer xMax = Integer.parseInt(myRandomGenParameters.get(2).getText());
        if(xMax==0){xMax=(int) GameScreen.screenWidth;}
        Integer yMin = Integer.parseInt(myRandomGenParameters.get(3).getText());
        if(yMin==0){yMin=((int) (GameScreen.screenHeight*0.2));}
        Integer yMax = Integer.parseInt(myRandomGenParameters.get(4).getText());
        if(yMax==0){yMax=(int) (GameScreen.screenHeight*0.6);}
        Integer minSpacing = Integer.parseInt(myRandomGenParameters.get(5).getText());
        if(minSpacing==0){minSpacing=250;}
        Integer maxSpacing = Integer.parseInt(myRandomGenParameters.get(6).getText());
        if(maxSpacing==0){maxSpacing=500;}

        // Need width and height of the game objects
        // Image URL (bird.png)
        // 

        RandomGeneration randomGeneration = new RandomGeneration(properties,num,xMin,xMax,yMin,yMax,minSpacing,maxSpacing);

        myLevel.addRandomGeneration(randomGeneration);
    }

    private Map<String,String> getPropertiesMap(Map<String,String> myItemMap){
        removeValuesExceptProperties(myItemMap);
        Map<String,String> properties = new HashMap<String,String>();
        myItemMap.forEach((k,v)-> {
            if(!(v.isEmpty()) && !(v.equals(DetailDefaultsResources.TEXT_BOX_NUMBER_DEFAULT_INPUT.getResource())))
            properties.put(k, v);
        });
        return properties;
    }


    public void addControl(KeyCode key, String action){
        //myLevel.setControl(key, action);
    }


    @Override
    public ArrayList<Map<String, String>> getTypeMaps() {
        return mySpriteTypes;
    }

    public void addWinCondition(String type, String action){
        myLevel.addWinCondition(type, action);
    }

    public void addLoseCondition(String type, String action){
        myLevel.addLoseCondition(type, action);
    }

    public void addScrollType(ScrollType scrollType){
        myLevel.setScrollType(scrollType);
    }

    @Override
    public void addMainCharacterImage (String imageFilePath) {
        this.mainCharacterImageFilePath = imageFilePath;
    }



    @Override
    public void addScrollSpeed(String speed) {

    }

    public void addMainCharacter(double xpos, double ypos, double width, double height, Map<String,String> properties){
        GameObject mainCharacter = new GameObject(xpos,ypos,MAIN_CHAR_WIDTH,MAIN_CHAR_HEIGHT,this.mainCharacterImageFilePath,properties);
        myLevel.addGameObject(mainCharacter);
        //myLevel.setMainCharacter(mainCharacter);
    }


    public void addGameObjectsToLevel(){   
        for (Map<String, String> type : myImageViewObjectMap){
//            double xPosition = Double.valueOf(type.get(ISelectDetail.X_POSITION_KEY));
//            double yPosition = Double.valueOf(type.get(ISelectDetail.Y_POSITION_KEY));
//            double width = Double.valueOf(type.get(WIDTH_KEY));
//            double height = Double.valueOf(type.get(HEIGHT_KEY));
//
//            String imagePath = type.get(IMAGE_PATH_KEY);
//            String file = imagePath.substring(imagePath.lastIndexOf("/") +1);
//            Map<String,String> properties = getPropertiesMap(type);
//            GameObject myObject = new GameObject(xPosition,yPosition,width,height,file,properties);

            GameObject myObject = createGameObject(type);
            myLevel.addGameObject(myObject);   
        }
    }
    
    private GameObject createGameObject(Map<String,String> map){
        double xPosition = Double.valueOf(map.get(ISelectDetail.X_POSITION_KEY));
        double yPosition = Double.valueOf(map.get(ISelectDetail.Y_POSITION_KEY));
        double width = Double.valueOf(map.get(WIDTH_KEY));
        double height = Double.valueOf(map.get(HEIGHT_KEY));

        String imagePath = map.get(IMAGE_PATH_KEY);
        String file = imagePath.substring(imagePath.lastIndexOf("/") +1);
        Map<String,String> properties = getPropertiesMap(map);
        GameObject myObject = new GameObject(xPosition,yPosition,width,height,file,properties); 
        myObject.setTypeName(map.get(DetailResources.TYPE_NAME.getResource()));
        if(myProjectileObjects.containsKey(map.get(DetailResources.TYPE_NAME.getResource()))){
           // ProjectileProperties propMap = new ProjectileProperties()
            //myObject.setProjectileProperties(myProjectileObjects.get(myObject.getTypeName()));
        }
        
        return myObject;
    }

    private void removeValuesExceptProperties(Map<String,String> typeMap){
        typeMap.remove(IMAGE_PATH_KEY);
        typeMap.remove(HEIGHT_KEY);
        typeMap.remove(WIDTH_KEY);
        typeMap.remove(ISelectDetail.X_POSITION_KEY);
        typeMap.remove(ISelectDetail.Y_POSITION_KEY);
        //typeMap.remove(DetailResources.TYPE_NAME.getResource()); 
        typeMap.remove(DetailResources.IMAGEVIEW_KEY.getResource());
    }


    @Override
    public void storeMainCharToXML () {
        // TODO: Add Objects
        for(Map<String,String> map: myMainCharImageViewMaps){
            map.forEach((k,v)-> {
                GameObject myObject = createGameObject(map);
                // Add it to the game
                Player player = new Player(myObject);
                myGame.addPlayer(player);
                myLevel.addGameObject(myObject);  
            });
        }
    }

    @Override
    public ArrayList<String> getMainCharacterTypes () {
        ArrayList<String> types = new ArrayList<String>();
        for (Map<String, String> type : myMainCharImageViewMaps){
            String typeName = type.get(DetailResources.TYPE_NAME.getResource());
            types.add(typeName);
        }
        return types;
    }
}



