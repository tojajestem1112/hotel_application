package sample.controller.homeRightPanel.employeeManagment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import sample.model.dao.Dao;
import sample.model.entities.Employee;

import java.util.List;

public class EmployeeShowerController
{

    @FXML ComboBox<String> choosePosition;
    @FXML ListView<Employee> listOfEmployees;

    public void initialize()
    {
        choosePosition.setItems(getPositions());
    }
    public void showEmployees()
    {
        listOfEmployees.setItems(getEmployees());
    }
    public ObservableList<String> getPositions() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll("Wszyscy","Administrator", "Manager", "Receptionist", "Cleaner", "Conservator");
        return list;
    }
    public ObservableList<Employee> getEmployees()
    {
        ObservableList<Employee> list = FXCollections.observableArrayList();
        List<Object> listOfEmployees = Dao.get(Employee.class);
        for(int i=0; i<listOfEmployees.size();i++)
        {
            Employee currentEmpl = (Employee)listOfEmployees.get(i);
            addEmployeeOnList(list, listOfEmployees, i, currentEmpl);
        }
        return list;
    }

    private void addEmployeeOnList(ObservableList<Employee> list, List<Object> listOfEmployees, int i, Employee currentEmpl) {
        if(choosePosition.getValue().equals("Wszyscy")) {
            list.add(currentEmpl);
        }
        else {
            if(choosePosition.getValue().equals(((Employee)listOfEmployees.get(i)).getPosition())) {
                list.add(currentEmpl);
            }
        }
    }

}
