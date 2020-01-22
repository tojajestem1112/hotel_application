package sample.model.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="client")
public class Client {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="email_adress")
    private String emailAdress;

    @OneToMany(mappedBy = "client")
    private List<Reservation> reservations;

    @Column(name="personal_id_number")
    private String personalIdNumber;

    public Client(){}

    public Client(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = "EMPTY";
        this.emailAdress="EMPTY";
    }

    public Client(String name, String surname, String phoneNumber, String email_adress, String personalIdNumber ) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.emailAdress = email_adress;
        this.personalIdNumber = personalIdNumber;

    }

    public void addReservation(Reservation tempRes)
    {
        if(reservations == null)
        {
            reservations = new ArrayList<Reservation>();
        }
        reservations.add(tempRes);
        tempRes.setClient(this);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmailAdress(){ return emailAdress;}

    public List<Reservation> getReservations() {
        return reservations;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPersonalIdNumber() {
        return personalIdNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber.replace(" ", "").replace("-","");
    }

    public void setEmailAdress(String emailAdress) {


        this.emailAdress = emailAdress;
    }

    public void setPersonalIdNumber(String personalIdNumber) {

        this.personalIdNumber = personalIdNumber;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void setData(String name, String surname, String phoneNumber, String emailAdress, String personalIdNumber)
    {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.emailAdress = emailAdress;
        this.personalIdNumber=personalIdNumber;
    }
    @Override
    public String toString() {
        return name+" "+ surname +" TEL: "+ phoneNumber + " EMAIL: "+emailAdress+" PESEL "+personalIdNumber;
    }


}
