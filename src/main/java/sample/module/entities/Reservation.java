package sample.module.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="reservation")
public class Reservation
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="date_of_reservation", insertable = false)
    private Timestamp dateOfReservation;

    @Column(name="reservation_from")
    private Date reservationFrom;

    @Column(name="reservation_util")
    private Date reservationUtil;

    @Column(name="total_cost")
    private BigDecimal totalCost;

    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE
            ,CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="id_employee")
    private Employee employee;

    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE
                ,CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="id_client")
    private Client client;

    @ManyToMany(fetch=FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name="reservation_room",
            joinColumns = @JoinColumn(name = "id_reservation"),
            inverseJoinColumns = @JoinColumn(name="id_room")
    )
    private List<Room> rooms;

    @Column(name="checked_out")
    private boolean checkedOut;

    public Reservation()
    {
    }

    public Reservation(Date reservationFrom, Date reservationUtil, boolean checkedOut) {
        this.reservationFrom = reservationFrom;
        this.reservationUtil = reservationUtil;
        this.checkedOut = checkedOut;

    }

    public Reservation(Timestamp dateOfReservation, Date reservationFrom, Date reservationUtil, BigDecimal totalCost, Employee employee, Client client) {
        this.dateOfReservation = dateOfReservation;
        this.reservationFrom = reservationFrom;
        this.reservationUtil = reservationUtil;
        this.totalCost = totalCost;
        this.employee = employee;
        this.client = client;
    }


    public void setClient(Client client)
    {
        this.client = client;
    }
    public void setEmployee (Employee employee) {this.employee=employee;}

    public Timestamp getDateOfReservation() {
        return dateOfReservation;
    }

    public void setDateOfReservation(Timestamp dateOfReservation) {
        this.dateOfReservation = dateOfReservation;
    }

    public Date getReservationFrom() {
        return reservationFrom;
    }

    public void setReservationFrom(Date reservationFrom) {
        this.reservationFrom = reservationFrom;
    }

    public Date getReservationUtil() {
        return reservationUtil;
    }

    public void setReservationUtil(Date reservationUtil) {
        this.reservationUtil = reservationUtil;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Client getClient() {
        return client;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    public void addRoom(Room room)
    {
        if(rooms==null)
        {
            rooms = new ArrayList<Room>();
        }
        rooms.add(room);
    }

}
