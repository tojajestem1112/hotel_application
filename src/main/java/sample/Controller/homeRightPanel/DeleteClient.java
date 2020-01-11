package sample.Controller.homeRightPanel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.module.dao.Dao;
import sample.module.entities.Client;
import sample.view.ViewDetails;

import java.io.IOException;
import java.util.List;

public class DeleteClient {
    @FXML
    AnchorPane contentPane;
    @FXML
    TextField personalID1Field;
    @FXML
    TextField personalID2Field;
    @FXML
    Text personalIdConfirmationError;
    @FXML
    Text clientNotFoundText;


    public void deleteClient() throws IOException {
        String personalId1=personalID1Field.getText().trim();
        String personalId2=personalID2Field.getText().trim();
        if(personalId1.equals(personalId2))
        {
            List<Client> client = Dao.getClient(personalId1);
            if(client.size()==0)
            {
                clientNotFoundText.setVisible(true);
                personalIdConfirmationError.setVisible(false);
            }
            else
            {
                Dao.delete(client.get(0));
                AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/homeRightPanel/DeleteConfirmation.fxml"));
                contentPane.getChildren().setAll(tempPane);
                ViewDetails.setShowedRightPanel("");
            }
        }
        else {
            clientNotFoundText.setVisible(false);
            personalIdConfirmationError.setVisible(true);
        }

    }
}
