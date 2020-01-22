package sample.controller.homeRightPanel.clientManagment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.model.dao.Dao;
import sample.model.entities.Client;
import sample.view.ViewDetails;
import java.io.IOException;
import java.util.List;

public class ClientRemoverController {

    @FXML AnchorPane contentPane;
    @FXML TextField personalID1Field;
    @FXML TextField personalID2Field;
    @FXML Text personalIdConfirmationError;
    @FXML Text clientNotFoundText;
    @FXML Text deleteError;

    public void findClient() throws IOException {
        String personalId1=personalID1Field.getText();
        String personalId2=personalID2Field.getText();

        if(personalId1.equals(personalId2)) {

            List<Client> client = Dao.getClient(personalId1);
            deleteClient(client);
        }
        else {
            clientNotFoundText.setVisible(false);
            personalIdConfirmationError.setVisible(true);
        }

    }

    private void deleteClient(List<Client> client) throws IOException {
        personalIdConfirmationError.setVisible(false);
        deleteError.setVisible(false);
        clientNotFoundText.setVisible(false);
        if(client.size()==0) {//client not found
            clientNotFoundText.setVisible(true);
        }
        else {
            boolean hasBeenDeleted = Dao.delete(client.get(0));
            if(hasBeenDeleted) {
                AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/homeRightPanel/DeleteConfirmation.fxml"));
                contentPane.getChildren().setAll(tempPane);
                ViewDetails.setShowedRightPanel("");
            }
            else
            {
                deleteError.setVisible(true);
            }
        }
    }
}
