package sample.controller.homeRightPanel.accountManagment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.controller.homeRightPanel.personManagment.PersonMailer;
import sample.model.SavedData;
import sample.model.dao.Dao;
import sample.view.ViewDetails;

import java.io.IOException;

public class MyDataChangerController extends PersonMailer
{

    @FXML TextField passwordField;
    @FXML Text invalidPasswordText;
    @FXML Button submitButton;
    @FXML Text nameText;
    @FXML Text surnameText;
    @FXML Text phoneNumberText;
    @FXML Text emailAdressText;
    @FXML Text editError;


    private boolean permissionToChange = true;
    public void initialize()
    {

        nameText.setText(SavedData.getLoggedEmployee().getName());
        surnameText.setText(SavedData.getLoggedEmployee().getSurname());
        phoneNumberText.setText(SavedData.getLoggedEmployee().getPhoneNumber());
        emailAdressText.setText(SavedData.getLoggedEmployee().getEmailAdress());
    }

    public void submitChanges() throws IOException {
        String password = passwordField.getText();
        invalidDataText.setVisible(false);
        editError.setVisible(false);
        if(!SavedData.getLoggedEmployee().getPassword().equals(password))
        {
            invalidPasswordText.setVisible(true);
        }
        else
            updateMyAccount();



}

    private void updateMyAccount() throws IOException {

        permissionToChange = true;
        String oldName= SavedData.getLoggedEmployee().getName();
        String oldSurname=SavedData.getLoggedEmployee().getSurname();
        String oldPhoneNumber = SavedData.getLoggedEmployee().getPhoneNumber();
        String oldEmailAdress = SavedData.getLoggedEmployee().getEmailAdress();

        String name = getAttributeForUpdatedEmployee(nameField,validateNameAndSurname(true),oldName);
        String surname = getAttributeForUpdatedEmployee(surnameField, validateNameAndSurname(false), oldSurname);
        String phoneNumber = getAttributeForUpdatedEmployee(phoneNumberField, validatePhoneNumber(), oldPhoneNumber);
        String emailAdress = getAttributeForUpdatedEmployee(emailAdressField, validateEmailAdress(),oldEmailAdress);

        invalidPasswordText.setVisible(false);

        if(permissionToChange) {
            SavedData.getLoggedEmployee().setData(name, surname, phoneNumber, emailAdress);
            boolean hasBeenSaved = Dao.update(SavedData.getLoggedEmployee());
            if(hasBeenSaved)
                showUpdateSucces();
            else
            {
                editError.setVisible(true);
                SavedData.getLoggedEmployee().setData(oldName,oldSurname,oldPhoneNumber,oldEmailAdress);
            }

        }
        else
            invalidDataText.setVisible(true);
    }

    private String getAttributeForUpdatedEmployee(TextField field, boolean isCorr, String oldAttribute) {
        String attribute;
        if (!field.getText().trim().equals("") && field.getText() != null) {
            attribute = field.getText();
            if (!isCorr) {
                permissionToChange = false;
            }
            setFieldStatus(isCorr,field);
        } else {
            attribute = oldAttribute;
            setFieldStatus(true,field);
        }
        return attribute;
    }


}
