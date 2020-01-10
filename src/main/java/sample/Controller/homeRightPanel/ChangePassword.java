package sample.Controller.homeRightPanel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.module.SavedData;
import sample.module.dao.Dao;
import sample.module.entities.Employee;
import sample.view.ViewDetails;

import java.io.IOException;

public class ChangePassword
{
    @FXML
    AnchorPane contentPane;
    @FXML
    PasswordField oldPassword;
    @FXML
    PasswordField newPassword1;
    @FXML
    PasswordField newPassword2;
    @FXML
    Text invalidPassword;
    @FXML
    Text invalidNewPassword;

    public void changePassword() throws IOException {
        String oldP = oldPassword.getText();
        boolean permissionToChange=true;
        if(!SavedData.getLoggedEmployee().getPassword().equals(oldP))
        {
            invalidPassword.setVisible(true);
            permissionToChange = false;
        }else
        {
            invalidPassword.setVisible(false);
        }
        if(!newPassword1.getText().equals(newPassword2.getText()))
        {
            invalidNewPassword.setVisible(true);
            permissionToChange=false;
        }else{
            invalidNewPassword.setVisible(false);
        }
        if(permissionToChange)
        {
            SavedData.getLoggedEmployee().setPassword(newPassword1.getText());
            Dao.update(SavedData.getLoggedEmployee());
            AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/homeRightPanel/UpdateConfirmation.fxml"));
            contentPane.getChildren().setAll(tempPane);
            ViewDetails.setShowedRightPanel("");
        }

    }
}
