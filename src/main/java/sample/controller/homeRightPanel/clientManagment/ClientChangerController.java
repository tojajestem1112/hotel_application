package sample.controller.homeRightPanel.clientManagment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.controller.homeRightPanel.personManagment.PersonMailer;
import sample.model.dao.Dao;
import sample.model.entities.Client;
import sample.view.ViewDetails;
import java.io.IOException;
import java.util.List;

public class ClientChangerController extends PersonMailer
{
    @FXML Text nameText;
    @FXML Text surnameText;
    @FXML Text phoneNumberText;
    @FXML Text emailAdressText;
    @FXML Text personalIdText;
    @FXML Button submitButton;
    @FXML TextField personalIdField;
    @FXML TextField lookForClientField;
    @FXML Text invalidId;
    @FXML Text editError;


    private Client updatedClient = null;
    private boolean permissionToUpdate = true;
    public void setClient()
    {

        List<Client> list = Dao.getClient(lookForClientField.getText().trim());
        if(list.size()==0)//no clients - client with that id doesnt exist
        {
            setStatusOfFields(true);
            updatedClient = null;
        }
        else//client exists
        {
            setStatusOfFields(false);
            updatedClient=list.get(0);
            nameText.setText(updatedClient.getName());
            surnameText.setText(updatedClient.getSurname());
            phoneNumberText.setText(updatedClient.getPhoneNumber());
            emailAdressText.setText(updatedClient.getEmailAdress());
            personalIdText.setText(updatedClient.getPersonalIdNumber());
            updatedClient = list.get(0);
        }
    }


    public void updateClient() throws IOException {

        permissionToUpdate=true;
        editError.setVisible(false);
        invalidDataText.setVisible(false);

        String name = getAttributeForUpdatedEmployee(nameField, updatedClient.getName(), validateNameAndSurname(true));
        String surname = getAttributeForUpdatedEmployee(surnameField, updatedClient.getSurname(), validateNameAndSurname(false));
        String phoneNumber = getAttributeForUpdatedEmployee(phoneNumberField, updatedClient.getPhoneNumber(), validatePhoneNumber());
        String emailAdress = getAttributeForUpdatedEmployee(emailAdressField, updatedClient.getEmailAdress(), validateEmailAdress());
        String personalId = "";

        if(personalIdField.getText() ==null || personalIdField.getText().equals(""))
            personalId = updatedClient.getPersonalIdNumber();
        else
            personalId=personalIdField.getText();


        if(permissionToUpdate)
        {
            updatedClient.setData(name,surname, phoneNumber, emailAdress, personalId);
            boolean hasBeenChanged = Dao.update(updatedClient);
            if(hasBeenChanged) {
                AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/homeRightPanel/AddConfirmation.fxml"));
                contentPane.getChildren().setAll(tempPane);
                ViewDetails.setShowedRightPanel("");
            } else
                editError.setVisible(true);
        }else
            invalidDataText.setVisible(true);

    }

    private String getAttributeForUpdatedEmployee(TextField field, String oldValueOfAttribute, boolean isCorrect) {
        String attribute;
        if (field.getText() == null || field.getText().equals("")) {
            attribute = oldValueOfAttribute;
            setFieldStatus(true,field);
        } else {
            attribute = field.getText();
            if (!isCorrect) {
                permissionToUpdate = false;
                setFieldStatus(false,field);
            }else
                setFieldStatus(true,field);
        }
        return attribute;
    }

    private void setStatusOfFields(boolean status)
    {
        invalidId.setVisible(status);
        nameField.setDisable(status);
        surnameField.setDisable(status);
        phoneNumberField.setDisable(status);
        emailAdressField.setDisable(status);
        submitButton.setDisable(status);
        personalIdField.setDisable(status);
    }


}
