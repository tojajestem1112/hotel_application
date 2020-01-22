package sample.controller.homeRightPanel.accountManagment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.model.SavedData;
import sample.model.dao.Dao;
import sample.view.ViewDetails;

import java.io.IOException;

public class MyPasswordChangerContoller
{
    @FXML AnchorPane contentPane;
    @FXML PasswordField oldPassword;
    @FXML PasswordField newPassword1;
    @FXML PasswordField newPassword2;
    @FXML Text invalidPassword;
    @FXML Text invalidNewPassword;
    @FXML Text invalidNewPasswordFormat;

    private boolean permissionToChange=true;
    public void changePassword() throws IOException {
        permissionToChange = true;
        String oldP = oldPassword.getText();
        invalidNewPasswordFormat.setVisible(false);

        checkPasswords(oldP, SavedData.getLoggedEmployee().getPassword(), invalidPassword);
        checkPasswords(newPassword1.getText(), newPassword2.getText(), invalidNewPassword);

        if(permissionToChange)
        {
            boolean isCorrectFormat = SavedData.getLoggedEmployee().setPassword(newPassword1.getText());
            if(!isCorrectFormat)
            {
                invalidNewPasswordFormat.setVisible(true);
            }
            else {
                Dao.update(SavedData.getLoggedEmployee());
                AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/homeRightPanel/UpdateConfirmation.fxml"));
                contentPane.getChildren().setAll(tempPane);
                ViewDetails.setShowedRightPanel("");
            }
        }

    }

    private void checkPasswords(String pass1, String pass2, Text errorText) {
        if (!pass2.equals(pass1)) {
            errorText.setVisible(true);
            permissionToChange = false;
        } else {
            errorText.setVisible(false);
        }
    }
}
