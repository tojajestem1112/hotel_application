package sample.controller.homeRightPanel.clientManagment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import sample.model.dao.Dao;
import sample.model.entities.Reservation;

import java.util.Date;
import java.util.List;

public class ClientCheckOutController
{
    @FXML ListView<Reservation> checkingOut;

    public void initialize()
    {
        checkingOut.setItems(getCheckingOut());
    }

    ObservableList<Reservation> getCheckingOut() {
        ObservableList<Reservation> list = FXCollections.observableArrayList();
        List<Object> reservations = Dao.get(Reservation.class);
        Date currentDate = Dao.getDate();
        for(int i=0; i<reservations.size(); i++)
        {
            Reservation res = (Reservation) reservations.get(i);
            if(!res.getReservationUtil().after(currentDate) && res.getRealizationStatus().equals("IN REALIZATION"))
            {
                list.add(res);
            }
        }
        return list;
    }
}
