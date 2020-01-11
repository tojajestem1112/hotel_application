package sample.Controller.homeRightPanel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.module.SavedData;
import sample.module.dao.Dao;
import sample.module.entities.Client;
import sample.module.entities.Reservation;
import sample.module.entities.Room;
import sample.view.ViewDetails;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class AddReservation
{
    @FXML
    AnchorPane contentPane;
    @FXML
    TextField lookForClientField;
    @FXML
    DatePicker beginDate;
    @FXML
    DatePicker endDate;
    @FXML
    ListView<Room> freeRooms;
    @FXML
    TextField nameField;
    @FXML
    TextField surnameField;
    @FXML
    TextField phoneNumberField;
    @FXML
    TextField emailAdressField;
    @FXML
    TextField personalIdField;
    @FXML
    Button addClientButton;
    @FXML
    Text clientNotFound;
    @FXML
    Button addReservation;

    private Client client = null;

    public void initialize()
    {
        setClientFields(true);
        clientNotFound.setVisible(false);
        freeRooms.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        beginDate.setDisable(true);
        endDate.setDisable(true);
        addReservation.setDisable(true);
        freeRooms.setDisable(true);
    }

    public void createReservation() throws IOException {
        Date date1 = java.sql.Date.valueOf(beginDate.getValue());
        Date date2 = java.sql.Date.valueOf(endDate.getValue());
        Reservation res = new Reservation(date1, date2);
        res.setEmployee(SavedData.getLoggedEmployee());
        res.setClient(client);
        List<Room> rooms = freeRooms.getSelectionModel().getSelectedItems();
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
    public void getClient()
    {

        String personIdNumber = lookForClientField.getText().trim();
        List<Client> list = Dao.getClient(personIdNumber);
        if(list.size()==0)
        {
            clientNotFound.setVisible(true);
            client=null;
            setClientFields(false);
            beginDate.setDisable(true);
            endDate.setDisable(true);
            addReservation.setDisable(true);
            freeRooms.setDisable(true);
        }
        else
        {
            clientNotFound.setVisible(false);
            setClientFields(true);
            client = list.get(0);
            beginDate.setDisable(false);
            endDate.setDisable(false);
            addReservation.setDisable(false);
            freeRooms.setDisable(false);
        }

    }
    public void getListOfRooms() throws ParseException {
        if (beginDate.getValue() != null && endDate.getValue() != null)
        {
            freeRooms.setItems(getRooms());
        }
    }

    public void addClient()
    {
        String name = nameField.getText();
        String surname= surnameField.getText();
        String phoneNumber = phoneNumberField.getText();
        String emailAdress = emailAdressField.getText();
        String personalIdNumber = personalIdField.getText();
        client = new Client(name,surname,phoneNumber,emailAdress,personalIdNumber);
        Dao.insert(client);
        beginDate.setDisable(false);
        endDate.setDisable(false);
        addReservation.setDisable(false);
        freeRooms.setDisable(false);
        setClientFields(true);
        lookForClientField.setDisable(true);

    }

    public ObservableList<Room> getRooms() throws ParseException {
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
    private void setClientFields(boolean status)
    {
        nameField.setDisable(status);
        surnameField.setDisable(status);
        phoneNumberField.setDisable(status);
        emailAdressField.setDisable(status);
        personalIdField.setDisable(status);
        addClientButton.setDisable(status);
    }

}
