package sample.Controller.homeRightPanel;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.module.dao.Dao;
import sample.module.entities.Client;

import java.util.List;

public class EditClient
{
    @FXML
    AnchorPane contentPane;
    @FXML
    Text nameText;
    @FXML
    Text surnameText;
    @FXML
    Text phoneNumberText;
    @FXML
    Text emailAdressText;
    @FXML
    Text personalIdText;

    @FXML
    TextField nameField;
    @FXML
    TextField surnameField;
    @FXML
    TextField phoneNumberField;
    @FXML
    TextField emailAdressField;
    @FXML
    TextField personalIdField;
    @FXML
    Button submitButton;

    @FXML
    TextField logInField;
    @FXML
    Text invalidId;


    Client updatedClient = null;
    public void setClient()
    {
        List<Client> list = Dao.getClient(logInField.getText().trim());
        if(list.size()==0)
        {
            invalidId.setVisible(true);
            invalidId.setVisible(true);
            nameField.setDisable(true);
            surnameField.setDisable(true);
            phoneNumberField.setDisable(true);
            emailAdressField.setDisable(true);
            submitButton.setDisable(true);

        }
        else
        {
            invalidId.setVisible(false);
            nameField.setDisable(false);
            surnameField.setDisable(false);
            phoneNumberField.setDisable(false);
            emailAdressField.setDisable(false);
            submitButton.setDisable(false);
            updatedClient=list.get(0);
            nameText.setText(updatedClient.getName());
            surnameText.setText(updatedClient.getSurname());
            phoneNumberText.setText(updatedClient.getPhoneNumber());
            emailAdressText.setText(updatedClient.getEmailAdress());
            personalIdText.setText(updatedClient.getPersonalIdNumber());
        }
    }
}
