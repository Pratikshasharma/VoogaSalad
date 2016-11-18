package buttons;

import java.util.Locale;
import java.util.ResourceBundle;

import buttons.interfaces.IButtonLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

/**
 * 
 * @author pratikshasharma
 *
 */

public class ButtonTemplate implements IButtonLayout {
    private Button myButton;
    private ResourceBundle myResources;

    /**
     * @param property
     * Creates a button based on the String property
     */
    public ButtonTemplate(String property){     
        myResources = ResourceBundle.getBundle(BUTTON_LABEL_FILE, Locale.getDefault());

        String label = myResources.getString(property);
        myButton = new Button(label);
    }

    /**
     * @return Button
     */
    public Button getButton(){
        return this.myButton;
    }
    
    /**
     * Changes layout positions of the button
     */
    public void changeButtonSettings(double xPosition, double yPosition){
        myButton.setLayoutX(xPosition);
        myButton.setLayoutY(yPosition);
        myButton.setFont(Font.font("Comic Sans",14));
    }
    
    
    @Override
    public void setOnButtonAction (EventHandler<ActionEvent> handler) {
       myButton.setOnAction(handler);
    }
}

