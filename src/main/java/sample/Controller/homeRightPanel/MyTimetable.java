package sample.Controller.homeRightPanel;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import sample.module.SavedData;
import sample.module.dao.Dao;
import sample.module.entities.Timetable;


import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyTimetable
{
    @FXML
    TableView<Timetable> timetable;
    @FXML
    TableColumn<Timetable, String> dataColumn;
    @FXML
    TableColumn<Timetable, Time> begginingTime;
    @FXML
    TableColumn<Timetable, Time> endingTime;

    @FXML
    DatePicker beginDate;

    private List<Timetable> listOfHours = new LinkedList<>();

    public void showTimetable() throws ParseException {
        Date startDate = java.sql.Date.valueOf(beginDate.getValue());
        generateAnwser(startDate);
    }

    public void initialize() throws ParseException {
        Date currentDate = new Date();
        generateAnwser(currentDate);


    }


    private void generateAnwser(Date startDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.DATE, 10);
        Date endingDate = c.getTime();
        listOfHours = Dao.getTimetableOfEmployee(startDate, endingDate, SavedData.getLoggedEmployee());
        dataColumn.setCellValueFactory(new PropertyValueFactory<Timetable, String>("dateString"));
        begginingTime.setCellValueFactory(new PropertyValueFactory<Timetable, Time>("startTime"));
        endingTime.setCellValueFactory(new PropertyValueFactory<Timetable, Time>("endTime" ));
        timetable.setItems(getTimetable(startDate));
        //dataColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeature>);

    }
    public ObservableList<Timetable> getTimetable(Date startingDate)
    {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(startingDate);
        ObservableList<Timetable> list = FXCollections.observableArrayList();
        int index =0;
        for(int i=0; i<10; i++)
        {
            String day = dateFormat.format(cal.getTime());
            if(index<listOfHours.size())
            {
                String dayOnList = dateFormat.format(listOfHours.get(index).getDate());
                if(day.equals(dayOnList))
                {
                    list.add(listOfHours.get(index));index++;
                }
                else
                {
                    list.add(new Timetable(cal.getTime()));
                }

                cal.add(Calendar.DATE, 1);
            }else
            {
                list.add(new Timetable(cal.getTime()));
            }
        }
        return list;

    }

}
