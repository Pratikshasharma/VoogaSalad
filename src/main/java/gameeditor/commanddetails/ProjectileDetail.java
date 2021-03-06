package gameeditor.commanddetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import gameeditor.controller.interfaces.IGameEditorData;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ProjectileDetail {
    private IGameEditorData myDataStore;
    private VBox myVBox;
    private DetailFrontEndUtil myDetailFrontEndUtil;
    private ImageDetail myImageDetail;
    private String selectedType;
    private ArrayList<TextArea> myTextInputs;
    private ComboBox<String> projectileDirection;
    private ComboBox<String> myTypes;
    private String myImageFile;



    //public static String [] 
    public ProjectileDetail(IGameEditorData dataStore){
        myDataStore = dataStore;
        myImageDetail= new ImageDetail();
        myDetailFrontEndUtil = new DetailFrontEndUtil();
        myTextInputs = new ArrayList<TextArea>();
    }

    public VBox getTabContent(){
        myVBox = new VBox();
        myVBox.setSpacing(IAbstractCommandDetail.MY_DETAIL_PADDING);
        myVBox.setAlignment(Pos.CENTER);
        createSpriteTypesCombo();
        addProperties();
        return myVBox;
    }

    private void addProperties(){
        addDirection();
        addTextAreaProperties();
        addImagePane();
        createSave(e-> handleSave());
    }

    private void createSave(EventHandler<MouseEvent> handler){
        Button save = myDetailFrontEndUtil.createButton("SaveCommand",handler);
        myVBox.getChildren().add(save);
    }

    private void handleSave(){
        Map<String,String> projectilePropertiesMap = new HashMap<String,String>();
        if(projectileDirection.getValue()!=null){ 
            projectilePropertiesMap.put(DetailResources.DIRECTION_KEY.getResource(),projectileDirection.getValue());
        }
        myImageFile = myImageDetail.getFilePath();

        int counter=0;
        for(TextArea area: myTextInputs){
            String label = DetailResources.PROJECTILE_TEXT_INPUT_PROPERTIES_LABEL.getArrayResource()[counter];
            String value = area.getText();
            if(value.isEmpty()|| value!=null){
                value = DetailDefaultsResources.TEXT_BOX_NUMBER_DEFAULT_INPUT.getResource();
            }
            
            projectilePropertiesMap.put(label.toLowerCase(), area.getText());   
        }
    
    String type =null;
    if(myTypes.getValue()!=null){
        type = myTypes.getValue();
    }
    myDataStore.addProjectileProperties(type, projectilePropertiesMap); 
}


private void addImagePane(){
    myVBox.getChildren().add(myImageDetail.createImageChoose());
}

private void addTextAreaProperties(){
    String [] propertiesList = DetailResources.PROJECTILE_TEXT_INPUT_PROPERTIES_LABEL.getArrayResource();
    for(String property: propertiesList){
        Label label = myDetailFrontEndUtil.createPropertyLbl(property);
        TextArea myTextArea= myDetailFrontEndUtil.createInputField(DetailDefaultsResources.TEXT_BOX_NUMBER_DEFAULT_INPUT.getResource());
        myDetailFrontEndUtil.handleClick(myTextArea);
        BorderPane bp = myDetailFrontEndUtil.createBorderpane(myTextArea, label);
        myVBox.getChildren().add(bp);
        myTextInputs.add(myTextArea);
    }
}

private void createSpriteTypesCombo(){
    ArrayList<String>  listOfTypes = myDataStore.getTypes();
    listOfTypes.addAll(getMainCharacterTypes());     
    String[] types = listOfTypes.toArray(new String[listOfTypes.size()]);
    Label labl = myDetailFrontEndUtil.createPropertyLbl("Select");
    myTypes = myDetailFrontEndUtil.createComboBox(types, null);
    myTypes.setMaxWidth(IAbstractCommandDetail.PADDED_DETAIL_WIDTH*1.5);
    myTypes.setMinWidth(IAbstractCommandDetail.PADDED_DETAIL_WIDTH*1.5);
    //myTypesCombo.setOnAction(e-> displayProjectileProperties(myTypesCombo));
    BorderPane bp = myDetailFrontEndUtil.createBorderpane(myTypes, labl);
    myVBox.getChildren().add(bp);
}

private ArrayList<String> getMainCharacterTypes(){
    ArrayList<String> mainChars = new ArrayList<String> ();
    ArrayList<String> listOfMainCharacters = myDataStore.getMainCharacterTypes();
    for(String val: listOfMainCharacters){
        String str = val.substring(0, DetailResources.MAIN_CHARACTER_TYPE.getResource().length());
        String str2= val.substring(DetailResources.MAIN_CHARACTER_TYPE.getResource().length());
        mainChars.add(str + " " + str2);
    }
    return mainChars;
}

//    private void displayProjectileProperties(ComboBox <String> myCombo){
//        if(myCombo.getValue()!=null){
//            Map<String,String> myTypeMap = myDataStore.getType(myCombo.getValue());
//                   
//               }
//            }

private void addDirection(){
    Label label = myDetailFrontEndUtil.createPropertyLbl(DetailResources.DIRECTION_LABEL.getResource());
    String defaultDirection = DetailDefaultsResources.PROJECTILE_DIRECTION.getResource();
    projectileDirection = myDetailFrontEndUtil.createComboBox(DetailResources.SCROLL_DIRECTIONS_OPTIONS.getArrayResource(), defaultDirection);
    BorderPane bp = myDetailFrontEndUtil.createBorderpane(projectileDirection, label);
    myVBox.getChildren().add(bp);
}


}
