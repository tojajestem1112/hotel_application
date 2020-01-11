package sample.Controller.homeRightPanel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.module.dao.Dao;
import sample.module.entities.Employee;
import sample.module.entities.Timetable;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class EmployeesTimetable
{
    @FXML
    TableView<TableClass> table;
    @FXML
    ComboBox<String> positionBox;
    @FXML
    DatePicker beginDate;
    @FXML
    TableColumn<TableClass, String> personColumn;
    @FXML
    TableColumn<TableClass, String> day1Column;
    @FXML
    TableColumn<TableClass, String> day2Column;
    @FXML
    TableColumn<TableClass, String> day3Column;
    @FXML
    ComboBox<String> datesBox;
    @FXML
    ComboBox<Employee> personBox;
    @FXML
    TextField startTimeField;
    @FXML
    TextField endTimeField;

    private Date[] dates = new Date[3];
    private ArrayList<Employee> currentEmployees = new ArrayList<>();
    @FXML
    public void initialize()
    {
        positionBox.setItems(getPositions());
    }
    public void initializeListView() throws ParseException
    {
        if(positionBox.getValue()!= null && beginDate.getValue() != null) {
            personColumn.setCellValueFactory(new PropertyValueFactory<TableClass, String>("employeeName"));
            day1Column.setCellValueFactory(new PropertyValueFactory<TableClass, String>("time1"));
            day2Column.setCellValueFactory(new PropertyValueFactory<TableClass, String>("time2"));
            day3Column.setCellValueFactory(new PropertyValueFactory<TableClass, String>("time3"));
            table.setItems(getTable());
            updateTableHeadings();
            datesBox.setItems(getDates());
            personBox.setItems(getEmployees());
        }
    }

    public ObservableList<String> getPositions()
    {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll("Wszyscy","Administrator", "Manager", "Receptionist", "Cleaner", "Conservator");
        return list;
    }
    public ObservableList<TableClass> getTable() throws ParseException {
        currentEmployees = new ArrayList<>();
        ObservableList<TableClass> list = FXCollections.observableArrayList();
        Date date =  java.sql.Date.valueOf(beginDate.getValue());
        String position = positionBox.getValue();
        List<Object> listOfEmployees = Dao.get(Employee.class);

        for(int i=0; i<listOfEmployees.size();i++)
        {
            Date tempDate = new Date(date.getTime());
            Employee temp = (Employee)listOfEmployees.get(i);
            if(!temp.getPosition().equals(position))
                continue;
            currentEmployees.add(temp);
            String[] times = new String[3];
            for(int k=0; k<3;k++)
            {
                List<Timetable> timeTab = Dao.getTimetableOfEmployee(tempDate, tempDate, temp);
                if(timeTab.size()==0) times[k]="";
                else
                {

                    String time1 = timeTab.get(0).getStartTimeString();
                    String time2 = timeTab.get(0).getEndTimeString();
                    times[k]=time1+" "+time2;
                    System.out.println("TIME "+times[k]);
                }

                Calendar c = Calendar.getInstance();
                c.setTime(tempDate);
                c.add(Calendar.DATE, 1);
                tempDate = c.getTime();
            }
            list.add(new TableClass(temp.getName()+ " "+temp.getSurname(), times[0],times[1],times[2]));

        }

        return list;
    }
    public ObservableList<String> getDates()
    {
        ObservableList<String> list = FXCollections.observableArrayList();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for(int i=0;i<3;i++) {
            list.add(dateFormat.format(dates[i]));
        }
        return list;
    }

    public void updateTableHeadings()
    {
        Date date = java.sql.Date.valueOf(beginDate.getValue());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for(int i=1; i<4;i++) {
            table.getColumns().get(i).setText(dateFormat.format(date));
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, 1);
            dates[i-1]=date;
            date=c.getTime();
        }
    }
    public ObservableList<Employee> getEmployees()
    {
        ObservableList<Employee> list = FXCollections.observableArrayList();
        for(int i=0; i<currentEmployees.size();i++)
        {
            list.add(currentEmployees.get(i));
        }
        return list;
    }
    public void saveChanges() throws ParseException {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("hh:mm");
        String startTime = startTimeField.getText().trim();
        String endTime = endTimeField.getText().trim();
        if(startTime.equals("") && endTime.equals(""))
        {
            Date date = format1.parse(datesBox.getValue());
            Employee empl = personBox.getValue();
            List<Timetable> list =Dao.getTimetableOfEmployee(date,date,empl);
            if(list.size()>0)
                Dao.delete(list.get(0));
            initializeListView();
        }
        else if(!startTime.equals("") && !endTime.equals(""))
        {
            Date date = format1.parse(datesBox.getValue());
            Employee empl = personBox.getValue();
            List<Timetable> list =Dao.getTimetableOfEmployee(date,date,empl);
            if(list.size()>0)
            {
                list.get(0).setStartTime(new Time(format2.parse(startTime).getTime()));
                list.get(0).setEndTime(new Time(format2.parse(endTime).getTime()));
                Dao.update(list.get(0));
            }else
            {
                Timetable timetable = new Timetable(datesBox.getValue(), startTime, endTime);
                timetable.setEmployee(empl);
                Dao.insert(timetable);
            }
            initializeListView();
        }
    }
    public class TableClass{
        private String employeeName;
        private String time1;
        private String time2;
        private String time3;

        public TableClass(String date, String time1, String time2, String time3) {
            this.employeeName = date;
            this.time1 = time1;
            this.time2 = time2;
            this.time3 = time3;
        }

        public String getEmployeeName() {
            return employeeName;
        }

        public String getTime1() {
            return time1;
        }

        public String getTime2() {
            return time2;
        }

        public String getTime3() {
            return time3;
        }
    }

}
