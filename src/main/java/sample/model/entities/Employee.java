package sample.model.entities;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

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

    @OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}
            , mappedBy = "employee")
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

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public String getPosition() {
        return position;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public List<Timetable> getTimetable() {
        return timetable;
    }

    public boolean setName(String name) {
        Pattern pattern =Pattern.compile("[A-Za-z`\\- ']+");
        if(!pattern.matcher(name).matches())
            return false;
        this.name = name;
        return true;
    }

    public boolean setSurname(String surname) {
        Pattern pattern =Pattern.compile("[A-Za-z`\\- ']+");
        if(!pattern.matcher(surname).matches())
            return false;
        this.surname = surname;
        return true;
    }

    public boolean setPassword(String password) {
        Pattern pattern =Pattern.compile("[A-Za-z!@#$%^&*()\\-_=+{}|:,.]{3,}");
        if(!pattern.matcher(password).matches())
            return false;
        this.password = password;
        return true;
    }

    public boolean setPhoneNumber(String phoneNumber) {
        Pattern pattern =Pattern.compile("(\\+)?[0-9 -]{9,}");
        if(!pattern.matcher(phoneNumber).matches())
            return false;
        this.phoneNumber = phoneNumber.replace(" ", "").replace("-","");
        return true;
    }

    public boolean setEmailAdress(String emailAdress) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_.-]+@[a-z0-9.-]+\\.[a-z0-9]{1,6}");
        if(!pattern.matcher(emailAdress).matches())
            return false;
        this.emailAdress = emailAdress;
        return true;
    }

    public boolean setPosition(String position) {
        if(!getPositions().contains(position))
            return false;
        this.position = position;
        return true;
    }

    public void setData(String name, String surname, String phoneNumber, String emailAdress)
    {
        this.name = name;
        this.surname=surname;
        this.phoneNumber=phoneNumber.trim();
        this.emailAdress = emailAdress;
    }

    public static LinkedList<String> getPositions()
    {
        LinkedList<String> list = new LinkedList<>();
        list.add("Administrator");
        list.add("Manager");
        list.add("Receptionist");
        list.add("Cleaner");
        list.add("Conservator");
        return list;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void setTimetable(List<Timetable> timetable) {
        this.timetable = timetable;
    }

    @Override
    public String toString() {
        return name+ " "+ surname + " Tel.: "+phoneNumber + " Email "+ emailAdress;
    }
}
