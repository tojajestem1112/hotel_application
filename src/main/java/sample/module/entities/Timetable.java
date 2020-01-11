package sample.module.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Entity
@Table(name = "timetable")
public class Timetable implements Serializable
{
    @Id
    @Column(name="date")
    private Date date;

    @Column(name="start_time")
    private Time startTime;

    @Column(name="end_time")
    private Time endTime;

    @Id
    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE
            ,CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="id_employee")
    private Employee employee;

    @Transient
    private String dateString ="";

    @Transient
    private String startTimeString ="";

    @Transient
    private String endTimeString ="";

    public void setEmployee(Employee employee)
    {
        this.employee = employee;
    }

    public Timetable(){}

    public Timetable(String date, String startTime, String endTime) throws ParseException {
        this.date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        SimpleDateFormat isoFormat = new SimpleDateFormat("HH:mm");
        this.startTime=new Time(isoFormat.parse(startTime).getTime());

        this.endTime=new Time(isoFormat.parse(endTime).getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateString = dateFormat.format(date);
    }
    public Timetable(Date date)
    {
        this.date = date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateString = dateFormat.format(date);
        this.endTime = null;
        this.startTime=null;
    }
    public Date getDate()
    {
        return date;
    }
    public void setDate(Date date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateString = dateFormat.format(date);
        this.date = date;
    }

    public Time getStartTime() {
        System.out.println("STARTTIME: "+startTime.getHours());
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getDateString()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateString = dateFormat.format(date);
        return dateString;
    }
    public String getStartTimeString(){
        if(endTime!=null) {
            SimpleDateFormat isoFormat = new SimpleDateFormat("HH:mm");
            startTimeString = isoFormat.format(startTime);
        }
        return startTimeString;
    }
    public String getEndTimeString(){
        if(endTime!= null) {
            SimpleDateFormat isoFormat = new SimpleDateFormat("HH:mm");
            endTimeString = isoFormat.format(endTime);
        }
        return endTimeString;
    }
}
