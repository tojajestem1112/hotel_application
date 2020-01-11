package sample.Controller.homeRightPanel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sample.module.dao.Dao;
import sample.module.entities.Client;
import sample.view.ViewDetails;

import java.io.IOException;

public class AddClient
{
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
    AnchorPane contentPane;

    public void addClient() throws IOException {
        Client client = new Client(nameField.getText(), surnameField.getText(), phoneNumberField.getText(),
                emailAdressField.getText(), personalIdField.getText());
        Dao.insert(client);
        AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/homeRightPanel/AddConfirmation.fxml"));
        contentPane.getChildren().setAll(tempPane);
        ViewDetails.setShowedRightPanel("");
    }
}
