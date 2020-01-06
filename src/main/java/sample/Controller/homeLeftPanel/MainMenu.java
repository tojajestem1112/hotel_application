package sample.Controller.homeLeftPanel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import sample.module.SavedData;
import sample.module.dao.Dao;
import sample.module.entities.Employee;

import java.io.IOException;

public class MainMenu
{
    @FXML
    AnchorPane logOut;
    @FXML
    AnchorPane mainPane;
    @FXML
    AnchorPane centralPane;
    @FXML
    AnchorPane myAccount;
    @FXML
    AnchorPane clientManagment;
    @FXML
    AnchorPane reservationManagment;
    @FXML
    AnchorPane employeeManagment;
    @FXML
    AnchorPane roomManagment;

    private String showedCentralPage = "";

    public void logOut() throws IOException {
        System.out.println("log out");
        SavedData.clear();
        AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/logging/logging.fxml"));
        mainPane.getChildren().setAll(tempPane);
    }
    public void myAccount() throws IOException {
        showOrHideCentralPane("myAccount");
    }
    public void clientManagment() throws IOException {
        showOrHideCentralPane("clientManagment");
    }
    public void reservationManagment() throws IOException {
        showOrHideCentralPane("reservationManagment");
    }
    public void employeeManagment() throws IOException {
        showOrHideCentralPane("employeeManagment");
    }
    public void roomManagment() throws IOException {
        showOrHideCentralPane("roomManagment");
    }

    private void showOrHideCentralPane(String idOfPane) throws IOException {
        if(!showedCentralPage.equals(idOfPane)) {
            AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/homeCentralPanel/"+idOfPane+".fxml"));
            centralPane.getChildren().setAll(tempPane);
            showedCentralPage=idOfPane;

        } else {
            AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/homeCentralPanel/empty.fxml"));
            centralPane.getChildren().setAll(tempPane);
            showedCentralPage="";
        }
    }
}
