package gameeditor.commanddetails;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import gameeditor.view.interfaces.IStandardDesignArea;
import gameeditor.objects.GameObjectView;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;


public class ControlsDetail extends AbstractCommandDetail implements ILevelTwo {
    private ArrayList<ComboBox<String>> myComboBoxes = new ArrayList<ComboBox<String>>();
    private String[] myControlsOptions = DetailResources.CONTROL_OPTIONS.getArrayResource();
    private ArrayList<TextArea> myInputFields = new ArrayList<TextArea>();
    private GameObjectView myGO;
    private String myImageViewString;
    private Map<String,String> myMainCharMap;

    public ControlsDetail() {
        super();
    }

    @Override
    public void init() {
        addVBoxSettings();
        int counter=0;
        for (@SuppressWarnings("unused") String string : myControlsOptions){
            String defaultKey = DetailDefaultsResources.CONTROLS_DEFAULT_VALUES.getArrayResource()[counter];
            String defaultControl = DetailDefaultsResources.CONTROLS_DEFAULT_COMBO_OPTIONS.getArrayResource()[counter];
            createSelectDirectionsControl(defaultKey, myControlsOptions,defaultControl);
            counter++;
        }
        createSave();
        initLevel2(myDetailPane.getCurrentAvatar());
    }

    @Override
    public void initLevel2 (GameObjectView sprite) {
        myGO = sprite;
        myImageViewString = myGO.getImageView().toString();
        myMainCharMap = myDataStore.getMainCharMap(myImageViewString);  
        
    }

    private void createSave(){
        Button save = myDetailFrontEndUtil.createButton("SaveCommand",e -> handleSave());
        myVBox.getChildren().add(save);
    }

    private void handleSave(){
        ResourceBundle controlProps =  ResourceBundle.getBundle("Controls");
        for (int i = 0; i < myComboBoxes.size(); i++){
            String controlKey = myComboBoxes.get(i).getValue();
            String kcString = myInputFields.get(i).getText();
            if ( controlKey != null && kcString != null && kcString !="Input" && !kcString.isEmpty()){
                KeyCode kc = KeyCode.valueOf(kcString);
                myDataStore.addControl(kc, controlProps.getString(controlKey.toLowerCase()));
            }
        }
    }

    private void createSelectDirectionsControl(String label, String[] optionsArray, String comboDefault){
        HBox innerContainer = new HBox();
        innerContainer.setSpacing(HBOX_SPACING);
        innerContainer.setAlignment(Pos.CENTER);
        ComboBox<String> cb = myDetailFrontEndUtil.createComboBox(optionsArray,comboDefault);
        TextArea inputField = createControlsInput(label);
        myInputFields.add(inputField);
        myComboBoxes.add(cb);
        innerContainer.getChildren().add(cb);
        innerContainer.getChildren().add(inputField);
        myVBox.getChildren().add(innerContainer);
    }

    private TextArea createControlsInput(String label){
        TextArea inputField = myDetailFrontEndUtil.createInputField(label);
        inputField.setOnKeyPressed(e -> inputField.clear());
        inputField.setOnKeyReleased(e -> handleKey(inputField, e.getCode()));
        return inputField;
    }

    private void handleKey(TextArea field, KeyCode kc){
        field.setText(kc.toString());
    }
   
}
