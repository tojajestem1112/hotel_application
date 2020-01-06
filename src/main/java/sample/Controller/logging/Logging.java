package sample.Controller.logging;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sample.module.SavedData;
import sample.module.dao.Dao;

import java.io.IOException;

public class Logging
{
    private Dao dao;
    public void initialize()
    {
        dao = new Dao();
    }

    @FXML
    Button logInButton;
    @FXML
    TextField usernameField;
    @FXML
    TextField passwordField;
    @FXML
    AnchorPane mainPane;

    @FXML
    public void logging() throws IOException {

        //System.out.println(usernameField.getText());
        dao.logIn(usernameField.getText(), passwordField.getText());
        if(SavedData.getLoggedEmployee()==null)
            System.out.println(SavedData.getLoggedEmployee());
        else {

            AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/homeLeftPanel/MainMenu.fxml"));
            mainPane.getChildren().setAll(tempPane);
        }

    }
    public void dataInit(Dao dao)
    {
        this.dao = dao;
    }

}
