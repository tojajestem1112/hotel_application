package sample.controller.homeRightPanel.accountManagment;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import sample.model.SavedData;

public class MyDataController
{
    @FXML Text nameText;
    @FXML Text surnameText;
    @FXML Text phoneNumberText;
    @FXML Text emailAdressText;

    @FXML
    public void initialize()
    {

        nameText.setText(SavedData.getLoggedEmployee().getName());
        surnameText.setText(SavedData.getLoggedEmployee().getSurname());
        phoneNumberText.setText(SavedData.getLoggedEmployee().getPhoneNumber());
        emailAdressText.setText(SavedData.getLoggedEmployee().getEmailAdress());
    }
}
