package sample.Controller.homeRightPanel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sample.module.dao.Dao;
import sample.module.entities.Room;
import sample.view.ViewDetails;

import java.io.IOException;

public class AddRoom {
    @FXML
    TextField roomNumberField;
    @FXML
    TextField peopleRoomNumber;
    @FXML
    AnchorPane contentPane;

    public void addRoom() throws IOException {
        Room newRoom = new Room(Integer.parseInt(roomNumberField.getText()),
                                Integer.parseInt(peopleRoomNumber.getText()));
        Dao.insert(newRoom);
        AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/homeRightPanel/AddConfirmation.fxml"));
        contentPane.getChildren().setAll(tempPane);
        ViewDetails.setShowedRightPanel("");

    }
}
