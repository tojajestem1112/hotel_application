package sample.Controller.homeRightPanel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import sample.module.SavedData;
import sample.module.dao.Dao;
import sample.module.entities.Employee;
import sample.view.ViewDetails;

import java.io.IOException;
import java.util.List;

public class DeleteEmployee {

    @FXML
    AnchorPane contentPane;
    @FXML
    ListView<Employee> listView;
    @FXML

    public void initialize()
    {
        listView.setItems(getEmployees());
    }


    public void deleteEmployee() throws IOException {
        Employee empl = listView.getFocusModel().getFocusedItem();
        Dao.delete(empl);
        AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/homeRightPanel/DeleteConfirmation.fxml"));
        contentPane.getChildren().setAll(tempPane);
        ViewDetails.setShowedRightPanel("");

    }
    public ObservableList<Employee> getEmployees()
    {
        ObservableList<Employee> list = FXCollections.observableArrayList();
        List<Object> employeeList = Dao.get(Employee.class);
        for(int i=0; i< employeeList.size(); i++)
        {
            if(SavedData.getLoggedEmployee().getId()==((Employee)employeeList.get(i)).getId())
                continue;
            list.add((Employee)employeeList.get(i));
        }
        return list;
    }
}
