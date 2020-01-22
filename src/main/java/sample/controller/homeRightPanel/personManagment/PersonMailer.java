package sample.controller.homeRightPanel.personManagment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.view.ViewDetails;

import java.io.IOException;
import java.util.regex.Pattern;

public abstract class PersonMailer {

    @FXML
    protected TextField nameField;
    @FXML
    protected TextField surnameField;
    @FXML
    protected TextField phoneNumberField;
    @FXML
    protected TextField emailAdressField;
    @FXML
    protected AnchorPane contentPane;
    @FXML
    protected Text invalidDataText;

    protected void setFieldStatus(boolean isCorrect, TextField field)
    {
        if(!isCorrect)
        {
            field.setStyle("-fx-border-color: red");
        }
        else {
            field.setStyle("-fx-border-color: black");
        }
    }

    protected boolean validateNameAndSurname(boolean isName){
        TextField field;
        if(isName)
            field = nameField;
        else
            field = surnameField;
        String text = field.getText();
        Pattern pattern =Pattern.compile("[A-Za-z`\\- ']+");
        if(!pattern.matcher(text).matches()) {
            return false;
        }
        else {
            return true;
        }
    }

    protected boolean validatePhoneNumber(){
        String phoneNumber = phoneNumberField.getText();
        Pattern pattern =Pattern.compile("(\\+)?[0-9 -]{9,}");
        if(!pattern.matcher(phoneNumber).matches()) {
            return false;
        }else{
            return true;
        }
    }
    protected boolean validateEmailAdress() {
        String emailAdress = emailAdressField.getText();
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_.-]+@[a-z0-9.-]+\\.[a-z0-9]{1,6}");
        if(!pattern.matcher(emailAdress).matches()) {
            return false;
        }else{
            return true;
        }
    }

    protected void showUpdateSucces() throws IOException {
        AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/homeRightPanel/UpdateConfirmation.fxml"));
        contentPane.getChildren().setAll(tempPane);
        ViewDetails.setShowedRightPanel("");
    }



}
