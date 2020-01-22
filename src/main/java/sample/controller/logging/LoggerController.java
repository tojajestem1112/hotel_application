package sample.controller.logging;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.model.SavedData;
import sample.model.dao.Dao;

import java.io.IOException;

public class LoggerController
{

    @FXML Button logInButton;
    @FXML TextField usernameField;
    @FXML TextField passwordField;
    @FXML AnchorPane mainPane;
    @FXML Text invalidData;
    @FXML Text connectingText;
    @FXML AnchorPane connectionError;



    @FXML
    public void logging() throws IOException {
        Dao.logIn(usernameField.getText(), passwordField.getText());
        if (SavedData.getLoggedEmployee() == null)
            invalidData.setVisible(true);
        else {
            AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/homeLeftPanel/MainMenu.fxml"));
            mainPane.getChildren().setAll(tempPane);
        }
    }
}

