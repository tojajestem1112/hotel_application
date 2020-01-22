package sample.controller.homeRightPanel.clientManagment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import sample.model.dao.Dao;
import sample.model.entities.Client;

import java.util.List;

public class ClientShowerController
{

    @FXML ListView<Client> listOfClients;

    public void initialize()
    {
        listOfClients.setItems(getClients());
    }


    
    public ObservableList<Client> getClients() {
        ObservableList<Client> list = FXCollections.observableArrayList();
        List<Object> tempList = Dao.get(Client.class);
        for(int i=0; i< tempList.size();i++)
        {
            list.add((Client)tempList.get(i));
        }
        return list;
    }

}
