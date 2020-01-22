package sample.controller.homeRightPanel.roomManagment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.model.dao.Dao;
import sample.model.entities.Room;
import sample.view.ViewDetails;

import java.io.IOException;



public class RoomInserterController extends RoomMailer {

    @FXML Text insertError;

    public void addRoom() throws IOException {
        insertError.setVisible(false);
        boolean permission = true;
        if(!validateField(roomNumberField))
            permission = false;
        if(!validateField(peopleNumberField))
            permission = false;

        if(permission) {
            Room newRoom = new Room(Integer.parseInt(roomNumberField.getText()),
                    Integer.parseInt(peopleNumberField.getText()));
            boolean hasBeenAdded = Dao.insert(newRoom);
            if(hasBeenAdded) {
                AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/homeRightPanel/AddConfirmation.fxml"));
                contentPane.getChildren().setAll(tempPane);
                ViewDetails.setShowedRightPanel("");
            }else
            {
                insertError.setVisible(true);
            }
        }
        else
        {
            invalidDataText.setVisible(true);
        }
    }
}
