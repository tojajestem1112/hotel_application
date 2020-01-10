package sample.module.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public void setEmployee(Employee employee)
    {
        this.employee = employee;
    }

    public Timetable(){}

    public Timetable(String date, String startTime, String endTime) throws ParseException {
        this.date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        this.startTime=Time.valueOf(startTime);
        this.endTime=Time.valueOf(endTime);
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
}
