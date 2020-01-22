package sample.controller.homeRightPanel.roomManagment;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;


import java.util.regex.Pattern;

public abstract class RoomMailer
{

    @FXML
    AnchorPane contentPane;
    @FXML
    TextField roomNumberField;
    @FXML
    TextField peopleNumberField;
    @FXML
    Text invalidDataText;

    protected boolean validateField(TextField field)
    {
        Pattern pattern = Pattern.compile("[0-9]+");
        if(pattern.matcher(field.getText()).matches())
        {
            setFieldStatus(true, field);
            return true;
        }
        else
        {
            setFieldStatus(false, field);
            return false;
        }
    }

    private void setFieldStatus(boolean isCorrect, TextField field)
    {
        if(!isCorrect)
        {
            field.setStyle("-fx-border-color: red");
            invalidDataText.setVisible(true);
        }
        else {
            field.setStyle("-fx-border-color: black");
            invalidDataText.setVisible(false);

        }
    }
}
