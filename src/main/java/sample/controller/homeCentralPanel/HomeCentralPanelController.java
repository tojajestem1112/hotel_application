package sample.controller.homeCentralPanel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import sample.view.ViewDetails;

import java.io.IOException;

public class HomeCentralPanelController
{


    @FXML
    AnchorPane rightPane;

    @FXML
    public void showMyData() throws IOException {
        showOrHideRightPane("accountManagment/MyDataView");
    }
    @FXML
    public void changeMyData() throws IOException{
        showOrHideRightPane("accountManagment/MyDataChangerView");
    }
    @FXML
    public void changePassword() throws IOException{
        showOrHideRightPane("accountManagment/MyPasswordChangerView");
    }
    @FXML
    public void showMyTimetable() throws IOException{
        showOrHideRightPane("accountManagment/MyTimetableView");
    }
    @FXML
    public void showAllRooms() throws IOException{
        showOrHideRightPane("roomManagment/RoomShowerView");
    }
    @FXML
    public void addRoom() throws IOException{
        showOrHideRightPane("roomManagment/RoomInserterView");
    }
    @FXML
    public void deleteRoom() throws  IOException{
        showOrHideRightPane("roomManagment/RoomRemoverView");
    }
    @FXML
    public void editRoom()throws IOException{
        showOrHideRightPane("roomManagment/RoomEditorView");
    }
    @FXML
    public void addEmployee()throws IOException{
        showOrHideRightPane("employeeManagment/EmployeeInserterView");
    }
    public void showEmployees() throws IOException{
        showOrHideRightPane("employeeManagment/EmployeesShowerView");
    }
    public void deleteEmployee() throws IOException{
        showOrHideRightPane("employeeManagment/EmployeeRemoverView");
    }

    public void editEmployee() throws IOException{
        showOrHideRightPane("employeeManagment/EmployeeEditorView");
    }
    public void employeesTimetable() throws IOException {
        showOrHideRightPane("employeeManagment/EmployeesTimetableView");
    }
    public void addClient() throws IOException {
        showOrHideRightPane("clientManagment/ClientInserterView");
    }
    public void editClient() throws IOException{
        showOrHideRightPane("clientManagment/ClientChangerView");
    }
    public void deleteClient() throws IOException {
        showOrHideRightPane("clientManagment/ClientRemoverView");
    }
    public void clientsReservation() throws IOException {
        showOrHideRightPane("clientManagment/ClientsReservationView");
    }
    public void addReservation() throws IOException {
        showOrHideRightPane("clientManagment/ReservationInserterView");
    }
    public void checkingOut() throws IOException {
        showOrHideRightPane("clientManagment/ClientCheckOutView");
    }
    public void showClients() throws IOException {
        showOrHideRightPane("clientManagment/ClientsShowerView");
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
