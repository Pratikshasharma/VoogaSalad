package gameeditor.commanddetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import gameeditor.controller.interfaces.IGameEditorData;
import gameeditor.objects.GameObject;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SelectDetail extends AbstractCommandDetail implements ISelectDetail {

    private static final String X_LABEL = "X: ";
    private static final String Y_LABEL = "Y: ";
    private static final String WIDTH_LABEL = "W: ";
    private static final String HEIGHT_LABEL = "H: ";

    private VBox myVBox = new VBox();

    private Label mySelectLabel;

    private TextArea myXTextArea = new TextArea();
    private TextArea myYTextArea = new TextArea();
    private TextArea myWidthTextArea = new TextArea();
    private TextArea myHeightTextArea = new TextArea();
    private GameObject myGO;

    private List<TextArea>myRandomGenerationList = new ArrayList<TextArea>();
    String[] myRandomGenerationParameters = DetailResources.RANDOM_GENERATION_PARAMETERS.getArrayResource();


    private String myType;

    public SelectDetail() {
        super();
    }

    @Override
    public void init() {
        myVBox = new VBox();
        myVBox.setSpacing(myDetailPadding);
        myVBox.setAlignment(Pos.CENTER);
        myContainerPane.setContent(myVBox);	
        myDesignArea.enableClick(this);
        addSelectLabel();
    }

    public void initLevel2(GameObject sprite){
        init();
        myGO = sprite;
        mySelectLabel.setTextFill(Color.LIGHTGREY);
        createTypeLabel();
        createPos();

        String typeName = myGO.getType();
        Map<String, String> typeMap = myDataStore.getType(typeName);
        String randomGen = typeMap.get(DetailResources.RANDOM_GEN_KEY.getResource());
        if(randomGen != null && randomGen.equals("True")){
            createProperties();
            createUpdate() ;
        }else{
            createUpdate();
        }
    }

    public void clearSelect(){
        init();
    }

    public void updateSpritePosition(double x, double y){
        String xString = Double.toString(x);
        xString = xString.substring(0, xString.indexOf(".")+2);
        String yString = Double.toString(y);
        yString = yString.substring(0, yString.indexOf(".")+2);
        myXTextArea.setText(X_LABEL + xString);
        myYTextArea.setText(Y_LABEL + yString);
    }

    public void updateSpriteDimensions(double width, double height){
        String widthString = Double.toString(width);
        widthString = widthString.substring(0, widthString.indexOf(".")+2);
        String heightString = Double.toString(height);
        heightString = heightString.substring(0, heightString.indexOf(".")+2);
        myWidthTextArea.setText(WIDTH_LABEL + widthString);
        myHeightTextArea.setText(HEIGHT_LABEL + heightString);
    }

    private void createUpdate(){
        Button update = new Button();
        update.setText(DetailResources.UPDATE_BUTTON_TEXT.getResource());
        update.setMinWidth((paddedPaneWidth - hboxSpacing)/2);
        update.setMaxWidth((paddedPaneWidth - hboxSpacing)/2);
        update.setMinHeight(cbHeight);
        update.setOnAction((e) -> {handleUpdate();});
        myVBox.getChildren().add(update);
    }

    private void handleUpdate() {
        String xString = myXTextArea.getText();
        String yString = myYTextArea.getText();
        String widthString = myWidthTextArea.getText();
        String heightString = myHeightTextArea.getText();
        double x = Double.parseDouble(xString.substring(X_LABEL.length()));
        double y = Double.parseDouble(yString.substring(Y_LABEL.length()));
        double width = Double.parseDouble(widthString.substring(WIDTH_LABEL.length()));
        double height = Double.parseDouble(heightString.substring(HEIGHT_LABEL.length()));

        myGO.update(x, y, width, height);

        // Update Data in the Back End
        Map<String, String> typeMap = myDataStore.getType(myGO.getType());
        typeMap.put(ISelectDetail.X_POSITION_KEY,xString.substring(X_LABEL.length()));
        typeMap.put(ISelectDetail.Y_POSITION_KEY, yString.substring(Y_LABEL.length()));
        typeMap.put(IGameEditorData.WIDTH_KEY,widthString.substring(WIDTH_LABEL.length()));
        typeMap.put(IGameEditorData.HEIGHT_KEY, heightString.substring(HEIGHT_LABEL.length()));
        
        
        String randomGen = typeMap.get(DetailResources.RANDOM_GEN_KEY.getResource());
       if(randomGen!=null && randomGen.equals("True")){ 
           myDataStore.addRandomGeneration(myGO.getType(), myRandomGenerationList);
       }
    }

    private void addSelectLabel(){
        BorderPane bp = new BorderPane();
        mySelectLabel = new Label(DetailResources.SELECT_LABEL_TEXT.getResource());
        bp.setCenter(mySelectLabel);
        bp.setMinWidth(paddedPaneWidth);
        bp.setMaxWidth(paddedPaneWidth);
        myVBox.getChildren().add(bp);
    }	

    private void createPos(){
        createInfoBP(myXTextArea, X_LABEL, myGO.getX(), myYTextArea, Y_LABEL, myGO.getY());
        createInfoBP(myWidthTextArea, WIDTH_LABEL, myGO.getWidth(), myHeightTextArea, HEIGHT_LABEL, myGO.getHeight());
    }

    private void createInfoBP(TextArea ta1, String label1, double value1, TextArea ta2, String label2, double value2){
        BorderPane bp = new BorderPane();
        bp.setMinWidth(paddedPaneWidth);
        bp.setMaxWidth(paddedPaneWidth);
        ta1 = createTextArea(label1, value1, ta1);
        ta2 = createTextArea(label2, value2, ta2);
        bp.setLeft(ta1);
        bp.setRight(ta2);
        myVBox.getChildren().add(bp);
    }

    private TextArea createTextArea(String label, double value, TextArea ta){
        String valueString = Double.toString(value);
        valueString = valueString.substring(0, valueString.indexOf(".")+2);
        ta.setText(label + valueString);
        ta.setMinWidth(cbWidth); ta.setMaxWidth(cbWidth);
        ta.setMinHeight(cbHeight); ta.setMaxHeight(cbHeight);
        ta.setOnKeyReleased((e) -> handleKeyRelease(e.getCode(), e.getCharacter(), ta, label));
        //		ta.setOnMouseClicked((e) -> handleClick(ta));
        return ta;
    }

    private void createTypeLabel(){
        myType = myGO.getType();
        mySelectLabel.setText(myType);
    }

    private void createProperties(){
        for (String label : myRandomGenerationParameters){           
            BorderPane bp = new BorderPane();
            bp.setMinWidth(paddedPaneWidth);
            bp.setMaxWidth(paddedPaneWidth);
            Label labl = createPropertyLbl(label);
            TextArea input= createInputField("0");
            myRandomGenerationList.add(input);

            bp.setLeft(labl);
            bp.setRight(input);
            BorderPane.setAlignment(labl, Pos.CENTER_LEFT);
            myVBox.getChildren().add(bp);
        }
    }

    private Label createPropertyLbl(String property){
        Label labl = new Label (property);
        return labl;
    }

    private TextArea createInputField(String initialText){
        TextArea inputField = new TextArea();
        inputField.setMinWidth(paddedDetailWidth);
        inputField.setMaxWidth(paddedDetailWidth);
        inputField.setText(initialText);
        inputField.setMinHeight(cbHeight);
        inputField.setMaxHeight(cbHeight);
        inputField.setOnMouseClicked(e -> handleClick(inputField));
        return inputField;
    }	

    private void handleClick(TextArea field){
        field.setText("");
    }

    private void handleKeyRelease(KeyCode kc, String character, TextArea field, String label){
        //		if (kc == KeyCode.BACK_SPACE){
        if (field.getText().length() < label.length() && kc.isDigitKey()){
            field.setText(label + character);
            field.positionCaret(field.getText().length());
        } else if (field.getText().length() < label.length()){
            field.setText(label);
            field.positionCaret(field.getText().length());
        } else if (kc.isDigitKey()){
            field.setText(label + field.getText().substring(label.length()));
            field.positionCaret(field.getText().length());
        } else if (kc.isLetterKey() || kc.isWhitespaceKey()){
            field.setText(label + field.getText().substring(label.length(), field.getText().length()-1));
            field.positionCaret(field.getText().length());
        }

    }
    
    
}