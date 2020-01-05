package sample.module.entities;

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

    public Client(){}

    public Client(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = "EMPTY";
        this.emailAdress="EMPTY";
    }

    public Client(String name, String surname, String phoneNumber, String email_adress) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.emailAdress = email_adress;

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

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email_adress='" + emailAdress + '\'' +
                '}';
    }
}
