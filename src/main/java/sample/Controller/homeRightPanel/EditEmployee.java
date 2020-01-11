package sample.Controller.homeRightPanel;

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
import sample.module.dao.Dao;
import sample.module.entities.Employee;
import sample.view.ViewDetails;

import java.io.IOException;
import java.util.List;

public class EditEmployee {

    @FXML
    AnchorPane contentPane;

    @FXML
    ListView<Employee> listView;

    @FXML
    Text nameText;
    @FXML
    Text surnameText;
    @FXML
    Text phoneNumberText;
    @FXML
    Text emailAdressText;
    @FXML
    Text positionText;

    @FXML
    TextField nameField;
    @FXML
    TextField surnameField;
    @FXML
    TextField phoneNumberField;
    @FXML
    TextField emailAdressField;
    @FXML
    ComboBox<String> positionBox;

    @FXML
    Button submitButton;
    @FXML
    Button resetButton;

    private Employee updatedEmployee= null;
    public void unlockFields()
    {
        if(listView.getFocusModel().getFocusedItem()!=null)
        {
            nameField.setDisable(false);
            surnameField.setDisable(false);
            phoneNumberField.setDisable(false);
            emailAdressField.setDisable(false);
            positionBox.setDisable(false);
            submitButton.setDisable(false);
            resetButton.setDisable(false);
            updatedEmployee=listView.getFocusModel().getFocusedItem();
            nameText.setText(updatedEmployee.getName());
            surnameText.setText(updatedEmployee.getSurname());
            phoneNumberText.setText(updatedEmployee.getPhoneNumber());
            emailAdressText.setText(updatedEmployee.getEmailAdress());
            positionText.setText(updatedEmployee.getPosition());
        }
        else
        {
            nameField.setDisable(true);
            surnameField.setDisable(true);
            phoneNumberField.setDisable(true);
            emailAdressField.setDisable(true);
            positionBox.setDisable(true);
            submitButton.setDisable(true);
            resetButton.setDisable(true);
        }
    }
    public void resetPassword() throws IOException {
        updatedEmployee.setPassword("password");
        saveChanges();
    }
    public void editEmployee() throws IOException {
        String name = nameField.getText();
        if(!name.replace(" ", "").replace("\t", "").equals(""))
        {
            updatedEmployee.setName(name);
        }

        String surname=surnameField.getText();
        if(!surname.replace(" ", "").replace("\t", "").equals(""))
        {
            updatedEmployee.setSurname(surname);
        }

        String phoneNumber=phoneNumberField.getText();
        if(!phoneNumber.replace(" ", "").replace("\t", "").equals(""))
        {
            updatedEmployee.setPhoneNumber(phoneNumber);
        }

        String emailAdress=emailAdressField.getText();
        if(!emailAdress.replace(" ", "").replace("\t", "").equals(""))
        {
            updatedEmployee.setEmailAdress(emailAdress);
        }

        if(positionBox.getValue()!= null)
        {
            updatedEmployee.setPosition(positionBox.getValue());
        }
        saveChanges();
    }
    public void saveChanges() throws IOException {
        Dao.update(updatedEmployee);
        AnchorPane tempPane = FXMLLoader.load(getClass().getResource("/homeRightPanel/UpdateConfirmation.fxml"));
        contentPane.getChildren().setAll(tempPane);
        ViewDetails.setShowedRightPanel("");
    }
    public void initialize()
    {
        positionBox.setItems(getPositions());
        listView.setItems(getEmployees());
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
