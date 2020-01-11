package sample.Controller.homeRightPanel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sample.module.dao.Dao;
import sample.module.entities.Employee;
import sample.module.entities.Timetable;
import sample.view.ViewDetails;

import java.io.IOException;
import java.util.Date;

public class AddEmployee
{
    @FXML
    AnchorPane contentPane;
    @FXML
    ComboBox<String> positionBox;
    @FXML
    TextField nameField;
    @FXML
    TextField surnameField;
    @FXML
    TextField phoneNumberField;
    @FXML
    TextField emailAdressField;

    public void addEmployee() throws IOException {
        Employee newEmployee = new Employee(nameField.getText()
            ,surnameField.getText(),"password",phoneNumberField.getText()
            ,emailAdressField.getText(),positionBox.getValue());
        Dao.insert(newEmployee);
        AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/homeRightPanel/AddConfirmation.fxml"));
        contentPane.getChildren().setAll(tempPane);
        ViewDetails.setShowedRightPanel("");
    }

    public void initialize()
    {
        positionBox.setItems(getPositions());
    }

    public ObservableList<String> getPositions() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll("Administrator", "Manager", "Receptionist", "Cleaner", "Conservator");
        return list;
    }

}
