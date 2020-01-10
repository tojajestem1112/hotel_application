package sample.Controller.homeCentralPanel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import sample.view.ViewDetails;

import java.io.IOException;

public class homeCentralPanel
{


    @FXML
    AnchorPane rightPane;

    @FXML
    public void showMyData() throws IOException {
        showOrHideRightPane("myData");
    }
    @FXML
    public void changeMyData() throws IOException{
        showOrHideRightPane("ChangeMyData");
    }
    @FXML
    public void changePassword() throws IOException{
        showOrHideRightPane("ChangePassword");
    }
    @FXML
    public void showMyTimetable() throws IOException{
        showOrHideRightPane("MyTimetable");
    }
    @FXML
    public void showAllRooms() throws IOException{
        showOrHideRightPane("AllRoomsShowing");
    }
    @FXML
    public void addRoom() throws IOException{
        showOrHideRightPane("AddRoom");
    }
    @FXML
    public void deleteRoom() throws  IOException{
        showOrHideRightPane("DeleteRoom");
    }
    @FXML
    public void editRoom()throws IOException{
        showOrHideRightPane("EditRoom");
    }
    @FXML
    public void addEmployee()throws IOException{
        showOrHideRightPane("AddEmployee");
    }
    private void showOrHideRightPane(String idOfPane) throws IOException {

        if(!ViewDetails.getShowedRightPanel().equals(idOfPane)) {
            AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/homeRightPanel/"+idOfPane+".fxml"));
            rightPane.getChildren().setAll(tempPane);
            ViewDetails.setShowedRightPanel(idOfPane);

        } else {
            AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/empty.fxml"));
            rightPane.getChildren().setAll(tempPane);
            ViewDetails.setShowedRightPanel("");
        }
    }

}
