package sample.controller.homeRightPanel.employeeManagment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.model.SavedData;
import sample.model.dao.Dao;
import sample.model.entities.Employee;
import sample.view.ViewDetails;

import java.io.IOException;
import java.util.List;

public class EmployeeRemoverController {

    @FXML AnchorPane contentPane;
    @FXML ListView<Employee> listView;
    @FXML Text deleteError;

    public void initialize()
    {
        listView.setItems(getEmployees());
    }


    public void deleteEmployee() throws IOException {
        deleteError.setVisible(false);
        Employee empl = listView.getFocusModel().getFocusedItem();
        boolean hasBeenDeleted = Dao.delete(empl);
        if(hasBeenDeleted) {
            AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/homeRightPanel/DeleteConfirmation.fxml"));
            contentPane.getChildren().setAll(tempPane);
            ViewDetails.setShowedRightPanel("");
        }else
            deleteError.setVisible(true);

    }
    public ObservableList<Employee> getEmployees() {
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
