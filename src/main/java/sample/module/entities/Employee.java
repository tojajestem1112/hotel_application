package sample.module.entities;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="employee")
public class Employee
{
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;

    @Column(name="password")
    private String password;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="email_adress")
    private String emailAdress;

    @Column(name = "position")
    private String position;

    @OneToMany(mappedBy = "employee")
    private List<Reservation> reservations;

    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "id_employee")
    private List<Timetable> timetable;

    public Employee(){}

    public Employee(String name, String surname, String password, String position) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.position = position;
        this.phoneNumber ="EMPTY";
        this.emailAdress="EMPTY";
    }

    public Employee(String name, String surname, String password, String phoneNumber, String emailAdress, String position) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.emailAdress = emailAdress;
        this.position = position;
    }


    public void addReservation(Reservation tempRes)
    {
        if(reservations == null)
        {
            reservations = new ArrayList<Reservation>();
        }
        reservations.add(tempRes);
        tempRes.setEmployee(this);
    }

    public void addTimetable(Timetable work)
    {
        if(timetable == null)
        {
            timetable = new ArrayList<Timetable>();
        }
        timetable.add(work);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Timetable> getTimetable() {
        return timetable;
    }

    public void setTimetable(List<Timetable> timetable) {
        this.timetable = timetable;
    }

    @Override
    public String toString() {
        return name+ " "+ surname + " Tel.: "+phoneNumber + " Email "+ emailAdress;
    }
}
