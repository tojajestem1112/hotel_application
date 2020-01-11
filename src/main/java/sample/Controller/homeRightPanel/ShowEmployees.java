package sample.Controller.homeRightPanel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import sample.module.dao.Dao;
import sample.module.entities.Employee;
import sample.module.entities.Timetable;

import java.util.Date;
import java.util.List;

public class ShowEmployees
{
    @FXML
    ComboBox<String> choosePosition;
    @FXML
    ListView<Employee> listOfEmployees;
    public void initialize()
    {
        choosePosition.setItems(getPositions());
    }
    public void showEmployees()
    {
        listOfEmployees.setItems(getEmployees());
    }
    public ObservableList<String> getPositions()
    {
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
            if(choosePosition.getValue().equals("Wszyscy"))
            {
                list.add((Employee)listOfEmployees.get(i));
            }
            else
            {
                if(choosePosition.getValue().equals(((Employee)listOfEmployees.get(i)).getPosition()))
                {
                    list.add((Employee)listOfEmployees.get(i));
                }
            }
        }
        return list;
    }

}
