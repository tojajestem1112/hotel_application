package sample.controller.homeRightPanel.roomManagment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.model.dao.Dao;
import sample.model.entities.Room;
import sample.view.ViewDetails;

import java.io.IOException;

public class RoomEditiorController extends RoomMailer
{
    @FXML Text roomNumberText;
    @FXML Text peopleNumberText;
    @FXML Text invalidRoomNumber;
    @FXML Text invalidPeopleNumber;
    @FXML Button submitButton;
    @FXML Text updatingError;

    @FXML ComboBox<Room> comboBox;
    @FXML Text editionError;
    private Room roomToChange = null;

    public void initialize()
    {
        comboBox.setItems(RoomShowerController.getRooms());
    }

    public void setRoomToChange()
    {
        roomToChange = comboBox.getValue();
        peopleNumberText .setText(roomToChange.getNumberOfPeople()+"");
        roomNumberText.setText(roomToChange.getNumberOfRoom()+"");
        peopleNumberField.setDisable(false);
        roomNumberField.setDisable(false);
        submitButton.setDisable(false);

    }

    public void updateRoom() throws IOException {
        updatingError.setVisible(false);
        boolean permission = true;
        if(peopleNumberField.getText()!=null && !peopleNumberField.getText().equals("")) {
            editionError.setVisible(true);
            if(!validateField(peopleNumberField))
                permission = false;
            else
                roomToChange.setNumberOfPeople(Integer.parseInt(peopleNumberField.getText()));
        }

        if(roomNumberField.getText()!=null && !roomNumberField.getText().equals("")) {
            if(!validateField(roomNumberField))
                permission = false;
            roomToChange.setNumberOfRoom(Integer.parseInt(roomNumberField.getText()));
        }

        if(permission)
        {
            boolean hasBeenUpdated = Dao.update(roomToChange);
            if(hasBeenUpdated) {
                AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/homeRightPanel/UpdateConfirmation.fxml"));
                contentPane.getChildren().setAll(tempPane);
                ViewDetails.setShowedRightPanel("");
            }else
                updatingError.setVisible(true);
        }


    }
}
