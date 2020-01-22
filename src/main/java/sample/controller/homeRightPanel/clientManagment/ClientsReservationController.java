package sample.controller.homeRightPanel.clientManagment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sample.model.dao.Dao;
import sample.model.entities.Client;
import sample.model.entities.Reservation;

import java.util.Date;
import java.util.List;

public class ClientsReservationController
{
    @FXML TextField personIdField;
    @FXML ListView<Reservation> listViewOldReservations;
    @FXML ListView<Reservation> listViewCurrentReservations;
    @FXML ListView<Reservation> listViewFutureReservations;
    @FXML Text clientNotFoundText;


    private Client client = null;

    public void showReservations()
    {
        String personalID = personIdField.getText();
        List<Client> list = Dao.getClient(personalID.trim());
        if(list.size()==0)
        {
            clientNotFoundText.setVisible(true);
        }
        else
        {
            client = list.get(0);
            clientNotFoundText.setVisible(false);
            listViewCurrentReservations.setItems(getReservations(0));
            listViewFutureReservations.setItems(getReservations(1));
            listViewOldReservations.setItems(getReservations(-1));
        }

    }

    public void deleteOldReservation() {
        Reservation res = listViewOldReservations.getSelectionModel().getSelectedItem();
        if(res!=null) {
            Dao.delete(res);
            showReservations();
        }
    }
    public void cancelReservation() {
        Reservation res = listViewFutureReservations.getSelectionModel().getSelectedItem();
        if(res!=null) {
            Dao.delete(res);
            showReservations();
        }
    }
    public void changeReservationStatus() {
        Reservation res = listViewCurrentReservations.getSelectionModel().getSelectedItem();
        if(res!=null) {
            res.changeReservationStatus();
            Dao.update(res);
            showReservations();
        }
    }
    public ObservableList<Reservation> getReservations(int type){
        ObservableList<Reservation> list = FXCollections.observableArrayList();
        List<Reservation> reservations = Dao.getReservations(client);
        Date currentDate = Dao.getDate();

        for(int i=0; i<reservations.size();i++) {
            Reservation singleReservation = reservations.get(i);
            addSingleReservationToList(type, list, currentDate, singleReservation);
        }


        return list;
    }

    private void addSingleReservationToList(int type, ObservableList<Reservation> list, Date currentDate, Reservation singleReservation) {
        if(singleReservation.getRealizationStatus().equals("ENDED") && type == -1)
            list.add(singleReservation);

        else if(singleReservation.getRealizationStatus().equals("NOT STARTED")) {
            if(currentDate.after(singleReservation.getReservationUtil())){
                if(type == -1)
                    list.add(singleReservation);
            }
            else if(currentDate.before(singleReservation.getReservationFrom())) {
                if(type==1) {
                    list.add(singleReservation);
                }
            }
            else if(type==0){
                list.add(singleReservation);
            }
        }
        else if(singleReservation.getRealizationStatus().equals("IN REALIZATION") && type ==0) {
            list.add(singleReservation);
        }
    }
}
