package sample.Controller.homeRightPanel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.module.dao.Dao;
import sample.module.entities.Room;
import sample.view.ViewDetails;

import java.io.IOException;
import java.util.regex.Pattern;

public class EditRoom
{

    @FXML
    AnchorPane contentPane;

    @FXML
    TextField roomNumberField;
    @FXML
    TextField peopleNumberField;

    @FXML
    Text roomNumberText;
    @FXML
    Text peopleNumberText;
    @FXML
    Text invalidRoomNumber;
    @FXML
    Text invalidPeopleNumber;

    @FXML
    Button submitButton;

    @FXML
    ComboBox<Room> comboBox;

    private Room roomToChange = null;

    public void initialize()
    {
        comboBox.setItems(AllRoomsShowing.getRooms());
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
        Pattern pattern = Pattern.compile("[1-9][0-9]*");
        boolean permission = true;
        String newPeopleNumber = peopleNumberField.getText();
        String newRoomNumber = roomNumberField.getText();
        if(newPeopleNumber!=null)
        {
            if(pattern.matcher(newPeopleNumber).matches())
            {
                roomToChange.setNumberOfPeople(Integer.parseInt(newPeopleNumber));
                invalidPeopleNumber.setVisible(false);
            }
            else
            {
                invalidPeopleNumber.setVisible(true);
                permission = false;
            }
        }


        if(newPeopleNumber!= null)
        {
            if(newRoomNumber!=null) {
                if (pattern.matcher(newRoomNumber).matches()) {
                    roomToChange.setNumberOfRoom(Integer.parseInt(newRoomNumber));
                    invalidRoomNumber.setVisible(false);
                } else {
                    invalidRoomNumber.setVisible(true);
                    permission = false;
                }
            }
        }
        if(permission)
        {
            Dao.update(roomToChange);
            AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/homeRightPanel/UpdateConfirmation.fxml"));
            contentPane.getChildren().setAll(tempPane);
            ViewDetails.setShowedRightPanel("");
        }


    }
}
