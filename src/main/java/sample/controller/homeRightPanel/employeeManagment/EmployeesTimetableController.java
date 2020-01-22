package sample.controller.homeRightPanel.employeeManagment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import sample.model.dao.Dao;
import sample.model.entities.Employee;
import sample.model.entities.Timetable;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.*;

public class EmployeesTimetableController
{
    @FXML TableView<TableClass> table;
    @FXML ComboBox<String> positionBox;
    @FXML DatePicker beginDate;
    @FXML TableColumn<TableClass, String> personColumn;
    @FXML TableColumn<TableClass, String> day1Column;
    @FXML TableColumn<TableClass, String> day2Column;
    @FXML TableColumn<TableClass, String> day3Column;
    @FXML ComboBox<String> datesBox;
    @FXML ComboBox<Employee> personBox;
    @FXML TextField startTimeField;
    @FXML TextField endTimeField;
    @FXML Text invalidData;

    private Date[] dates = new Date[3];
    private ArrayList<Employee> currentEmployees = new ArrayList<>();
    @FXML
    public void initialize()
    {
        positionBox.setItems(getPositions());
    }
    public void initializeListView() throws ParseException {
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

    public ObservableList<String> getPositions() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll("Administrator", "Manager", "Receptionist", "Cleaner", "Conservator");
        return list;
    }
    public ObservableList<TableClass> getTable() throws ParseException {
        currentEmployees = new ArrayList<>();
        ObservableList<TableClass> list = FXCollections.observableArrayList();
        Date beginDateOfTimetable =  java.sql.Date.valueOf(beginDate.getValue());
        String position = positionBox.getValue();
        List<Object> listOfEmployees = Dao.get(Employee.class);

        for(int i=0; i<listOfEmployees.size();i++)
        {
            Date iteratingDate = new Date(beginDateOfTimetable.getTime());
            Employee emp = (Employee)listOfEmployees.get(i);//single employee..
            if(!emp.getPosition().equals(position))//continue if bad position
                continue;
            currentEmployees.add(emp);
            String[] times = new String[3];//table of times of work
            for(int k=0; k<3;k++)
            {
                times[k] = getWorkHours(iteratingDate, emp);
                Calendar tempCalendar = Calendar.getInstance();
                tempCalendar.setTime(iteratingDate);
                tempCalendar.add(Calendar.DATE, 1);
                iteratingDate = tempCalendar.getTime();
            }
            list.add(new TableClass(emp.getName()+ " "+emp.getSurname(), times[0],times[1],times[2]));

        }

        return list;
    }

    private String getWorkHours(Date iteratingDate, Employee emp) throws ParseException {
        List<Timetable> timeTab = Dao.getTimetableOfEmployee(iteratingDate, iteratingDate, emp);
        if(timeTab.size()==0)
            return "";
        else {
            String time1 = timeTab.get(0).getStartTimeString();
            String time2 = timeTab.get(0).getEndTimeString();
            return time1+" "+time2;
        }
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
            Calendar calendarForIteration = Calendar.getInstance();
            calendarForIteration.setTime(date);
            calendarForIteration.add(Calendar.DATE, 1);
            dates[i-1]=date;
            date=calendarForIteration.getTime();
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
        Date inputDate = checkInputDate();
        if (inputDate == null) return;

        String startTime = startTimeField.getText().trim();
        String endTime = endTimeField.getText().trim();
        Employee empl = personBox.getValue();
        if(empl==null)
            return;
        List<Timetable> list =Dao.getTimetableOfEmployee(inputDate,inputDate,empl);

        if(startTime.equals("") && endTime.equals(""))//deleting
        {
            deleteWorkingHours(list);
        }
        else if(!startTime.equals("") && !endTime.equals(""))//updating or inserting
        {
            try{
                if(list.size()>0)
                    updateWorkingHours(startTime, endTime, list);
                else {
                    insertUpdatingHours(startTime, endTime, empl);
                }}
            catch(DateTimeParseException | ParseException e){
                invalidData.setVisible(true);
                return ;
            }
            initializeListView();
        }
    }

    private void insertUpdatingHours(String startTime, String endTime, Employee empl) throws ParseException {
        Timetable timetable = new Timetable(datesBox.getValue(), startTime, endTime);
        timetable.setEmployee(empl);
        Dao.insert(timetable);
    }

    private void updateWorkingHours(String startTime, String endTime, List<Timetable> list) throws ParseException {
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        list.get(0).setTime(new Timestamp(isoFormat.parse(datesBox.getValue()+" "+startTime).getTime())
                            ,new Timestamp(isoFormat.parse(datesBox.getValue()+" "+endTime).getTime()));
        Dao.update(list.get(0));
    }

    private void deleteWorkingHours(List<Timetable> list) throws ParseException {
        if(list.size()>0)
            Dao.delete(list.get(0));
        initializeListView();
    }

    private Date checkInputDate() throws ParseException {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        if(datesBox.getValue()==null || currentEmployees==null)
            return null;
        Date date = format1.parse(datesBox.getValue());
        Date currentDate = Dao.getDate();
        if(date.before(currentDate)) {//date cannot be before current date
            invalidData.setVisible(true);
            return null;
        }
        else
            invalidData.setVisible(false);
        return date;
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
