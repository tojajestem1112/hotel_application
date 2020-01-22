package sample.model.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;

@Entity
@Table(name = "timetable")
public class Timetable implements Serializable
{
    @Id
    @Column(name="date")
    private Date date;

    @Column(name="start_time")
    private Timestamp startTime;

    @Column(name="end_time")
    private Timestamp endTime;

    @Id
    @ManyToOne(fetch = FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.MERGE
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
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        this.startTime = new Timestamp(isoFormat.parse(date + " "+startTime).getTime());
        this.endTime=new Timestamp(isoFormat.parse(date + " "+endTime).getTime());
        if(this.endTime.getTime()<=this.startTime.getTime())
            throw new DateTimeParseException("end data is before start data", "xx", 1);
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
    public void setTime(Timestamp startTime, Timestamp endTime)
    {
        this.startTime = startTime;
        this.endTime= endTime;
        if(this.endTime.getTime()<=this.startTime.getTime())
            throw new DateTimeParseException("end data is before start data", "xx", 1);
    }

    public Timestamp getStartTime() {
        System.out.println("STARTTIME: "+startTime.getHours());
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
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
