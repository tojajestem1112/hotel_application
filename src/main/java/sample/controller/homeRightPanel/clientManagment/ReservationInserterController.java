package sample.controller.homeRightPanel.clientManagment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.model.SavedData;
import sample.model.dao.Dao;
import sample.model.entities.Client;
import sample.model.entities.Reservation;
import sample.model.entities.Room;
import sample.view.ViewDetails;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;


public class ReservationInserterController extends ClientInserterController
{

    @FXML TextField lookForClientField;
    @FXML DatePicker beginDate;
    @FXML DatePicker endDate;
    @FXML ListView<Room> freeRooms;
    @FXML Button addClientButton;
    @FXML Text clientNotFound;
    @FXML Button addReservation;

    private Client client = null;

    public void initialize() {
        setClientFields(true);
        clientNotFound.setVisible(false);
        freeRooms.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        beginDate.setDisable(true);
        endDate.setDisable(true);
        freeRooms.setDisable(true);
        showSuccessMessage=false;
    }

    public void getClient()
    {
        String personIdNumber = lookForClientField.getText();
        if(personIdNumber == null || personIdNumber.equals(""))
            return ;        //empty field

        List<Client> list = Dao.getClient(personIdNumber);
        if(list.size()==0)
        {
            clientNotFound.setVisible(true);
            client=null;
            setClientFields(false);
            setAddingReservationFields(true);
        }
        else
        {
            clientNotFound.setVisible(false);
            setClientFields(true);
            client = list.get(0);
            setAddingReservationFields(false);
        }
        lookForClientField.setDisable(true);

    }

    @Override
    public void addClient() throws IOException {
        if(lookForClientField.getText().equals(personalIdField.getText())) {
            super.addClient();
            if(permissionToAdd) {
                setAddingReservationFields(false);
                setClientFields(true);
                invalidDataText.setVisible(false);
                getClient();
            }
            else
                invalidDataText.setVisible(true);
            personalIdField.setStyle("-fx-border-color: black;");
        }
        else {
            personalIdField.setStyle("-fx-border-color: red;");
            invalidDataText.setVisible(true);
        }
    }

    public void createReservation() throws IOException {
        Date date1 = java.sql.Date.valueOf(beginDate.getValue());
        Date date2 = java.sql.Date.valueOf(endDate.getValue());
        Reservation res = new Reservation(date1, date2);
        res.setEmployee(SavedData.getLoggedEmployee());
        res.setClient(client);
        List<Room> rooms = freeRooms.getSelectionModel().getSelectedItems();
        if (rooms.size()==0)
            return ;
        for(int i=0; i<rooms.size();i++)
        {
            res.addRoom(rooms.get(i));
        }
        res.calculateTotalCost();
        Dao.insert(res);
        AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/homeRightPanel/AddConfirmation.fxml"));
        contentPane.getChildren().setAll(tempPane);
        ViewDetails.setShowedRightPanel("");

    }

    public void getListOfRooms() throws ParseException {
        if (beginDate.getValue() != null && endDate.getValue() != null) {
            Date date1 = java.sql.Date.valueOf(beginDate.getValue());
            Date date2 = java.sql.Date.valueOf(endDate.getValue());
            Date currDate = Dao.getDate();
            if(!date1.after(date2) && !currDate.after(date1)) {
                freeRooms.setItems(getRooms());
                    addReservation.setDisable(false);
            }
            else

                addReservation.setDisable(true);
        }
        else
            addReservation.setDisable(true);
    }


    public ObservableList<Room> getRooms() {
        ObservableList<Room> list = FXCollections.observableArrayList();
        Date date1 = java.sql.Date.valueOf(beginDate.getValue());
        Date date2 = java.sql.Date.valueOf(endDate.getValue());
        List<Integer> busyRooms = Dao.getBusyRooms(date1,date2);
        List<Object> rooms = Dao.get(Room.class);
        for(int i=0; i<rooms.size(); i++)
        {
            if(!busyRooms.contains( ((Room)rooms.get(i)).getNumberOfRoom()))
            {
                list.add((Room)rooms.get(i));
            }
        }
        return list;
    }
    private void setClientFields(boolean isDisabled)
    {
        nameField.setDisable(isDisabled);
        surnameField.setDisable(isDisabled);
        phoneNumberField.setDisable(isDisabled);
        emailAdressField.setDisable(isDisabled);
        personalIdField.setDisable(isDisabled);
        addClientButton.setDisable(isDisabled);
    }
    private void setAddingReservationFields(boolean isDisabled)
    {
        beginDate.setDisable(isDisabled);
        endDate.setDisable(isDisabled);
        freeRooms.setDisable(isDisabled);
    }
}
