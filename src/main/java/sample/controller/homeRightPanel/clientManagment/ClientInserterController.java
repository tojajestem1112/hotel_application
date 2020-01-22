package sample.controller.homeRightPanel.clientManagment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.controller.homeRightPanel.personManagment.PersonMailer;
import sample.model.dao.Dao;
import sample.model.entities.Client;
import sample.view.ViewDetails;

import java.io.IOException;

public class ClientInserterController extends PersonMailer
{
    @FXML TextField personalIdField;
    @FXML Text insertError;

    protected boolean showSuccessMessage = true;
    protected boolean permissionToAdd=true;
    protected boolean hasBeenAdded=true;
    public void addClient() throws IOException {
        insertError.setVisible(false);
        permissionToAdd = true;

        if(!validateNameAndSurname(true)) {
            permissionToAdd = false;
            setFieldStatus(false,nameField);
        }else
            setFieldStatus(true, nameField);

        if(!validateNameAndSurname(false)){
            permissionToAdd=false;
            setFieldStatus(false,surnameField);
        }else
            setFieldStatus(true, surnameField);

        if(!validatePhoneNumber()){
            permissionToAdd=false;
            setFieldStatus(false,phoneNumberField);
        }else
            setFieldStatus(true, phoneNumberField);

        if(!validateEmailAdress()){
            permissionToAdd=false;
            setFieldStatus(false,emailAdressField);
        }else
            setFieldStatus(true, emailAdressField);

        String personalId=personalIdField.getText();
        checkPersonalId(personalId);

        if(permissionToAdd) {
            Client client = new Client(nameField.getText()
                                    ,surnameField.getText()
                                    ,phoneNumberField.getText()
                                    ,emailAdressField.getText()
                                    ,personalIdField.getText());
            hasBeenAdded = Dao.insert(client);
            if(showSuccessMessage && hasBeenAdded) {
                AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/homeRightPanel/AddConfirmation.fxml"));
                contentPane.getChildren().setAll(tempPane);
                ViewDetails.setShowedRightPanel("");
            }
            if(!hasBeenAdded)
            {
                insertError.setVisible(true);
                invalidDataText.setVisible(false);
            }
        }
        else
            invalidDataText.setVisible(true);
    }

    private void checkPersonalId(String personalId) {
        if(personalId==null || personalId.equals("")) {
            setFieldStatus(false,personalIdField);
            permissionToAdd = false;
        }
        else
            setFieldStatus(true,personalIdField);
    }


}
