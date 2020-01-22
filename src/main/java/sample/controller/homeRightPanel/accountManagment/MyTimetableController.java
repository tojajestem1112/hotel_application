package sample.controller.homeRightPanel.accountManagment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.model.SavedData;
import sample.model.dao.Dao;
import sample.model.entities.Timetable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyTimetableController
{
    @FXML TableView<Timetable> timetable;
    @FXML TableColumn<Timetable, String> dataColumn;
    @FXML TableColumn<Timetable, String> begginingTime;
    @FXML TableColumn<Timetable, String> endingTime;
    @FXML DatePicker beginDate;

    private List<Timetable> listOfHours = new LinkedList<>();

    public void showTimetable() throws ParseException {
        Date startDate = java.sql.Date.valueOf(beginDate.getValue());
        generateAnwser(startDate);
    }

    private void generateAnwser(Date startDate) throws ParseException {
        if(beginDate.getValue()!=null) {
            Calendar calendarForIteration = Calendar.getInstance();
            calendarForIteration.setTime(startDate);
            calendarForIteration.add(Calendar.DATE, 10);
            Date endingDate = calendarForIteration.getTime();
            listOfHours = Dao.getTimetableOfEmployee(startDate, endingDate, SavedData.getLoggedEmployee());
            dataColumn.setCellValueFactory(new PropertyValueFactory<Timetable, String>("dateString"));
            begginingTime.setCellValueFactory(new PropertyValueFactory<Timetable, String>("startTimeString"));
            endingTime.setCellValueFactory(new PropertyValueFactory<Timetable, String>("endTimeString"));
            timetable.setItems(getTimetable(startDate));
        }

    }
    public ObservableList<Timetable> getTimetable(Date startingDate)
    {
        ObservableList<Timetable> list = FXCollections.observableArrayList();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(startingDate);
        int workingDayIndex =0;//position of downloaded list of Timetables (listHour)
        for(int i=0; i<10; i++)//iterating 10 days
        {
            workingDayIndex = isWorkingDay(list, dateFormat, cal, workingDayIndex);
        }
        return list;

    }

    private int isWorkingDay(ObservableList<Timetable> list, SimpleDateFormat dateFormat, Calendar cal, int indexOfReadTimetable) {
        String day = dateFormat.format(cal.getTime());//getting day
        if(indexOfReadTimetable<listOfHours.size())
        {
            String dayOnList = dateFormat.format(listOfHours.get(indexOfReadTimetable).getDate());//adding day on list
            if(day.equals(dayOnList))   //if that day exists(for that employee) in timetables
            {
                list.add(listOfHours.get(indexOfReadTimetable));
                indexOfReadTimetable++;//We will look at next day in ork
            }
            else//if that date doesnt exist for employee
            {
                list.add(new Timetable(cal.getTime()));
            }

            cal.add(Calendar.DATE, 1);
        }else//index is bigger than size of list so we can add only emtpty days...
        {
            list.add(new Timetable(cal.getTime()));
            cal.add(Calendar.DATE, 1);
        }
        return indexOfReadTimetable;
    }

}
