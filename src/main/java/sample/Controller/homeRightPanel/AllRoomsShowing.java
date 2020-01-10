package sample.Controller.homeRightPanel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.module.dao.Dao;
import sample.module.entities.Room;
import sample.module.entities.Timetable;

import java.util.Date;
import java.util.List;

public class AllRoomsShowing
{
    @FXML
    TableView<Room> tableOfRooms;
    @FXML
    TableColumn<Room, Integer> columnRoomNumber;
    @FXML
    TableColumn<Room, Integer> columnNumberOfPeople;


    public void initialize(){
        columnRoomNumber.setCellValueFactory(new PropertyValueFactory<Room, Integer>("numberOfRoom"));
        columnNumberOfPeople.setCellValueFactory(new PropertyValueFactory<Room, Integer>("numberOfPeople"));
        tableOfRooms.setItems(getRooms());
    }

    public static ObservableList<Room> getRooms() {

        ObservableList<Room> list = FXCollections.observableArrayList();
        List<Object> roomList = Dao.get(Room.class);
        for(int i=0; i< roomList.size();i++)
        {
            list.add((Room)roomList.get(i));
        }
        return list;
    }
}
