package sample.module.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.sql.Time;
import java.sql.Date;
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

    public void setEmployee(Employee employee)
    {
        this.employee = employee;
    }

    public Timetable(){}

    public Timetable(String date, String startTime, String endTime)
    {
        this.date = Date.valueOf(date);
        this.startTime=Time.valueOf(startTime);
        this.endTime=Time.valueOf(endTime);
    }



}
