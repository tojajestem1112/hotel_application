package sample.controller.homeRightPanel.employeeManagment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.controller.homeRightPanel.personManagment.PersonMailer;
import sample.model.dao.Dao;
import sample.model.entities.Employee;
import sample.view.ViewDetails;

import java.io.IOException;

public class EmployeeInserterController extends PersonMailer
{
    @FXML ComboBox<String> positionBox;
    @FXML Text insertError;

    public void initialize()
    {
        positionBox.setItems(getPositions());
    }

    public void addEmployee() throws IOException {
        boolean permissionToAdd = true;
        insertError.setVisible(false);
        invalidDataText.setVisible(false);

        if(!validateNameAndSurname(true)) {
            permissionToAdd = false;
            setFieldStatus(false,nameField);
        }
        else
            setFieldStatus(true, nameField);

        if(!validateNameAndSurname(false)) {
            permissionToAdd = false;
            setFieldStatus(false, surnameField);
        }else
            setFieldStatus(true, surnameField);

        if(!validateEmailAdress()) {
            permissionToAdd = false;
            setFieldStatus(false, emailAdressField);
        }else
            setFieldStatus(true, emailAdressField);

        if(!validatePhoneNumber()) {
            permissionToAdd = false;
            setFieldStatus(false,phoneNumberField);
        }else
            setFieldStatus(true,phoneNumberField);

        if(positionBox.getValue()==null) {
            permissionToAdd=false;
            positionBox.setStyle("-fx-border-color: red;");
        }
        else
            positionBox.setStyle("-fx-border-color: black;");

        if(permissionToAdd) {
            Employee newEmployee = new Employee(nameField.getText()
                    ,surnameField.getText(),"password",phoneNumberField.getText()
                    ,emailAdressField.getText(),positionBox.getValue());

            boolean hasBeenAdded = Dao.insert(newEmployee);
            if(hasBeenAdded) {
                AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/homeRightPanel/AddConfirmation.fxml"));
                contentPane.getChildren().setAll(tempPane);
                ViewDetails.setShowedRightPanel("");
            }
            else
            {
                insertError.setVisible(true);
            }
        }
        else
            invalidDataText.setVisible(true);
    }

    public ObservableList<String> getPositions() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll("Administrator", "Manager", "Receptionist", "Cleaner", "Conservator");
        return list;
    }

}
