package sample.controller.homeRightPanel.roomManagment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.model.dao.Dao;
import sample.model.entities.Room;
import sample.view.ViewDetails;

import java.io.IOException;
import java.util.List;

public class RoomRemoverController
{
    @FXML
    AnchorPane contentPane;
    @FXML
    ListView<Room> listView;
    @FXML
    Text deletingError;


    public void initialize()
    {
        listView.setItems(getRooms());
    }

    public void deleteRoom() throws IOException {
        deletingError.setVisible(false);
        Room room = listView.getFocusModel().getFocusedItem();
            deletingError.setVisible(false);
            boolean hasBeenDeleted = Dao.delete(room);
            if(hasBeenDeleted) {
                AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/homeRightPanel/DeleteConfirmation.fxml"));
                contentPane.getChildren().setAll(tempPane);
                ViewDetails.setShowedRightPanel("");
            }
            else
                deletingError.setVisible(true);
    }

    public ObservableList<Room> getRooms() {

        ObservableList<Room> list = FXCollections.observableArrayList();
        List<Object> roomList = Dao.get(Room.class);
        for(int i=0; i< roomList.size();i++)
        {
            list.add((Room)roomList.get(i));
        }
        return list;
    }
}
