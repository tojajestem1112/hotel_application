package sample.Controller.homeRightPanel;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import sample.module.SavedData;

public class myData
{
    @FXML
    Text nameField;
    @FXML
    Text surnameField;
    @FXML
    Text phoneNumberField;
    @FXML
    Text emailAdressField;

    @FXML
    public void initialize()
    {

        nameField.setText(SavedData.getLoggedEmployee().getName());
        surnameField.setText(SavedData.getLoggedEmployee().getSurname());
        phoneNumberField.setText(SavedData.getLoggedEmployee().getPhoneNumber());
        emailAdressField.setText(SavedData.getLoggedEmployee().getEmailAdress());
    }
}
