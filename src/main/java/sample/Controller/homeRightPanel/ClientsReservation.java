package sample.Controller.homeRightPanel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sample.module.dao.Dao;
import sample.module.entities.Client;
import sample.module.entities.Reservation;

import java.util.Date;
import java.util.List;

public class ClientsReservation
{
    @FXML
    TextField personIdField;
    @FXML
    ListView<Reservation> listViewOldReservations;
    @FXML
    ListView<Reservation> listViewCurrentReservations;
    @FXML
    ListView<Reservation> listViewFutureReservations;
    @FXML
    Text clientNotFoundText;


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
            listViewCurrentReservations.setItems(getReservation(0));
            listViewFutureReservations.setItems(getReservation(1));
            listViewOldReservations.setItems(getReservation(-1));
        }

    }

    public void deleteOldReservation()
    {
        Reservation res = listViewOldReservations.getFocusModel().getFocusedItem();
        Dao.delete(res);
        showReservations();
    }
    public void cancelReservation()
    {
        Reservation res = listViewFutureReservations.getFocusModel().getFocusedItem();
        Dao.delete(res);
        showReservations();
    }
    public void changeReservationStatus()
    {
        Reservation res = listViewCurrentReservations.getFocusModel().getFocusedItem();
        res.changeReservationStatus();
        Dao.update(res);
        showReservations();
    }
    public ObservableList<Reservation> getReservation(int type){
        ObservableList<Reservation> list = FXCollections.observableArrayList();
        List<Reservation> reservations = Dao.getReservations(client);
        for(int i=0; i<reservations.size();i++)
        {
            if(reservations.get(i).getRealizationStatus().equals("ENDED") && type == -1)
            {
                list.add(reservations.get(i));
            }
            else if(reservations.get(i).getRealizationStatus().equals("NOT STARTED"))
            {
                Date currentDate = Dao.getDate();
                if(currentDate.after(reservations.get(i).getReservationUtil())){
                    if(type == -1)
                        list.add(reservations.get(i));
                }
                else if(currentDate.before(reservations.get(i).getReservationFrom()))
                {
                    if(type==1)
                    {
                        list.add(reservations.get(i));
                    }
                }
                else if(type==0){
                    list.add(reservations.get(i));
                }
            }
            else if(reservations.get(i).getRealizationStatus().equals("IN REALIZATION") && type ==0)
            {
                list.add(reservations.get(i));
            }
        }


        return list;
    }
}
