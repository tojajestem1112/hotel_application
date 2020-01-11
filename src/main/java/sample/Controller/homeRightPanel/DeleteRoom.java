package sample.Controller.homeRightPanel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import sample.module.dao.Dao;
import sample.module.entities.Room;
import sample.view.ViewDetails;

import java.io.IOException;
import java.util.List;

public class DeleteRoom
{
    @FXML
    AnchorPane contentPane;
    @FXML
    ListView<Room> listView;


    public void initialize()
    {
        listView.setItems(getRooms());
    }

    public void deleteRoom() throws IOException {
        Room room = listView.getFocusModel().getFocusedItem();
        Dao.delete(room);
        AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/homeRightPanel/DeleteConfirmation.fxml"));
        contentPane.getChildren().setAll(tempPane);
        ViewDetails.setShowedRightPanel("");
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
