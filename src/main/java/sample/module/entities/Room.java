package sample.module.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="room")
public class Room
{
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name="number_of_room")
    int numberOfRoom;

    @Column(name="number_of_people")
    int numberOfPeople;

    @ManyToMany(fetch=FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name="reservation_room",
            joinColumns = @JoinColumn(name = "id_room"),
            inverseJoinColumns = @JoinColumn(name="id_reservation")
    )
    private List<Reservation> reservations;

    public Room(){}

    public Room(int numberOfRoom, int numberOfPeople) {
        this.numberOfRoom = numberOfRoom;
        this.numberOfPeople = numberOfPeople;
    }

    public void addReservation(Reservation reservation)
    {
        if(reservation == null)
        {
            reservations = new ArrayList<Reservation>();
        }
        reservations.add(reservation);
    }


    public int getNumberOfRoom() {
        return numberOfRoom;
    }

    public void setNumberOfRoom(int numberOfRoom) {
        this.numberOfRoom = numberOfRoom;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", numberOfRoom=" + numberOfRoom +
                ", numberOfPeople=" + numberOfPeople +
                '}';
    }
}
