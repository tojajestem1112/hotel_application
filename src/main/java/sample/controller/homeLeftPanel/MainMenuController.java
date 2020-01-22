package sample.controller.homeLeftPanel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import sample.model.SavedData;
import sample.view.ViewDetails;

import java.io.IOException;

public class MainMenuController
{
    @FXML
    AnchorPane logOut;
    @FXML
    AnchorPane mainPane;
    @FXML
    AnchorPane contentPane;
    @FXML
    AnchorPane myAccount;
    @FXML
    AnchorPane clientManagment;
    @FXML
    AnchorPane employeeManagment;
    @FXML
    AnchorPane roomManagment;

    private String position ="";

    public void initialize()
    {

        position = SavedData.getLoggedEmployee().getPosition();
        if(!position.equals("Administrator") && !position.equals("Manager"))
        {
            employeeManagment.setDisable(true);
            roomManagment.setDisable(true);
            employeeManagment.setOpacity(0.1);
            roomManagment.setOpacity(0.1);
        }
    }

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
    public void employeeManagment() throws IOException {
        showOrHideCentralPane("employeeManagment");
    }
    public void roomManagment() throws IOException {

        showOrHideCentralPane("roomManagment");
    }

    private void showOrHideCentralPane(String idOfPane) throws IOException {
        if(!ViewDetails.getShowedCentralPanel().equals(idOfPane)) {
            AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/HomeCentralPanel/"+idOfPane+".fxml"));
            contentPane.getChildren().setAll(tempPane);
            ViewDetails.setShowedCentralPanel(idOfPane);

        } else {
            AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/empty.fxml"));
            contentPane.getChildren().setAll(tempPane);
            ViewDetails.setShowedCentralPanel("");
        }
    }
}
