package sample.controller.homeRightPanel.employeeManagment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.controller.homeRightPanel.personManagment.PersonMailer;
import sample.model.dao.Dao;
import sample.model.entities.Employee;
import sample.view.ViewDetails;

import java.io.IOException;
import java.util.List;

public class EmployeeChangerController extends PersonMailer {

    @FXML ListView<Employee> listView;
    @FXML Text nameText;
    @FXML Text surnameText;
    @FXML Text phoneNumberText;
    @FXML Text emailAdressText;
    @FXML Text positionText;
    @FXML ComboBox<String> positionBox;
    @FXML Button submitButton;
    @FXML Button resetButton;
    @FXML Text editError;

    private Employee updatedEmployee= null;
    private boolean permissionToUpdate= false;
    public void initialize() {
        positionBox.setItems(getPositions());
        listView.setItems(getEmployees());
    }
    public void setEmployee()
    {
        if(listView.getFocusModel().getFocusedItem()!=null)
        {
            setFieldsDisabled(false);
            updatedEmployee=listView.getFocusModel().getFocusedItem();
            nameText.setText(updatedEmployee.getName());
            surnameText.setText(updatedEmployee.getSurname());
            phoneNumberText.setText(updatedEmployee.getPhoneNumber());
            emailAdressText.setText(updatedEmployee.getEmailAdress());
            positionText.setText(updatedEmployee.getPosition());
        }
        else
        {
            setFieldsDisabled(true);
        }
    }
    public void resetPassword() throws IOException {
        updatedEmployee.setPassword("password");
        saveChanges("");
    }
    public void editEmployee() throws IOException {
        permissionToUpdate = true;
        editError.setVisible(false);
        invalidDataText.setVisible(false);
        String name = getAttributeForUpdatedEmployee(nameField, validateNameAndSurname(true), updatedEmployee.getName());
        String surname = getAttributeForUpdatedEmployee(surnameField, validateNameAndSurname(false), updatedEmployee.getSurname());
        String phoneNumber = getAttributeForUpdatedEmployee(phoneNumberField, validatePhoneNumber(), updatedEmployee.getPhoneNumber());
        String emailAdress = getAttributeForUpdatedEmployee(emailAdressField, validateEmailAdress(), updatedEmployee.getEmailAdress());

        if(positionBox.getValue()!= null && permissionToUpdate) {
            updatedEmployee.setPosition(positionBox.getValue());
        }

        if(permissionToUpdate)
        {
            String oldEmail = updatedEmployee.getEmailAdress();
            updatedEmployee.setData(name,surname,phoneNumber, emailAdress);
            saveChanges(oldEmail);
        }
        else
            invalidDataText.setVisible(true);
    }

    private String getAttributeForUpdatedEmployee(TextField field, boolean isCorrect, String oldAttribute) {
        String attribute;
        if (!field.getText().replace(" ", "").equals("")) {
            attribute = field.getText();
            if (!isCorrect) {
                permissionToUpdate = false;
                setFieldStatus(false,field);
            }
            else
                setFieldStatus(true,field);
        } else {
            attribute = oldAttribute;
            setFieldStatus(true,field);
        }
        return attribute;

    }


    public void saveChanges(String oldValue) throws IOException {
        boolean hasBeenChanged = Dao.update(updatedEmployee);
        if(hasBeenChanged) {
            AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/homeRightPanel/UpdateConfirmation.fxml"));
            contentPane.getChildren().setAll(tempPane);
            ViewDetails.setShowedRightPanel("");
        }else
            updatedEmployee.setEmailAdress(oldValue);
            editError.setVisible(true);
    }

    public void setFieldsDisabled(boolean status)
    {
        nameField.setDisable(status);
        surnameField.setDisable(status);
        phoneNumberField.setDisable(status);
        emailAdressField.setDisable(status);
        positionBox.setDisable(status);
        submitButton.setDisable(status);
        resetButton.setDisable(status);
    }

    public ObservableList<Employee> getEmployees() {
        ObservableList<Employee> list = FXCollections.observableArrayList();
        List<Object> listOfEmployees = Dao.get(Employee.class);
        for(int i=0; i<listOfEmployees.size();i++)
        {
            list.add((Employee)listOfEmployees.get(i));
        }
        return list;
    }
    public ObservableList<String> getPositions()
    {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll("Administrator", "Manager", "Receptionist", "Cleaner", "Conservator");
        return list;
    }

}
