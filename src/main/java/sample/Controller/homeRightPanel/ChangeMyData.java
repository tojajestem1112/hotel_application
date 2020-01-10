package sample.Controller.homeRightPanel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.module.SavedData;
import sample.module.dao.Dao;
import sample.view.ViewDetails;

import java.io.IOException;

public class ChangeMyData
{
    @FXML
    AnchorPane contentPane;
    @FXML
    Text nameField;
    @FXML
    Text surnameField;
    @FXML
    Text phoneNumberField;
    @FXML
    Text emailAdressField;
    @FXML
    TextField nameTextField;
    @FXML
    TextField surnameTextField;
    @FXML
    TextField phoneNumberTextField;
    @FXML
    TextField emailAdressTextField;
    @FXML
    TextField passwordTextField;
    @FXML
    Text invalidPasswordField;
    @FXML
    Button submitButton;
    @FXML
    public void initialize()
    {
        nameField.setText(SavedData.getLoggedEmployee().getName());
        surnameField.setText(SavedData.getLoggedEmployee().getSurname());
        phoneNumberField.setText(SavedData.getLoggedEmployee().getPhoneNumber());
        emailAdressField.setText(SavedData.getLoggedEmployee().getEmailAdress());
    }
    public void submitChanges() throws IOException {
        String password = passwordTextField.getText();
        if(!SavedData.getLoggedEmployee().getPassword().equals(password))
        {
            invalidPasswordField.setVisible(true);
        }
        else {
            if (!nameTextField.getText().replace(" ", "").replace("\t", "").equals("")) {
                String name = nameTextField.getText().toUpperCase();
                SavedData.loggedEmployee.setName(name);
            }
            if (!surnameTextField.getText().replace(" ", "").replace("\t", "").equals("")) {
                String surname = surnameTextField.getText().toUpperCase();
                SavedData.loggedEmployee.setSurname(surname);
            }
            if (!phoneNumberTextField.getText().replace(" ", "").replace("\t", "").equals("")) {
                String phoneNumber = phoneNumberTextField.getText().replace(" ", "");
                SavedData.loggedEmployee.setPhoneNumber(phoneNumber);
            }
            if (!emailAdressTextField.getText().replace(" ", "").replace("\t", "").equals("")) {
                String emailAdress = emailAdressTextField.getText();
                SavedData.loggedEmployee.setEmailAdress(emailAdress);
            }
            Dao.update(SavedData.getLoggedEmployee());
            AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/homeRightPanel/UpdateConfirmation.fxml"));
            contentPane.getChildren().setAll(tempPane);
            ViewDetails.setShowedRightPanel("");
    }
}}
