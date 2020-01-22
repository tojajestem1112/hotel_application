package sample.model.entities;

import sample.model.SavedData;
import sample.model.dao.Dao;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @ManyToMany(fetch=FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name="reservation_room",
            joinColumns = @JoinColumn(name = "id_reservation"),
            inverseJoinColumns = @JoinColumn(name="id_room")
    )
    private List<Room> rooms;

    @Column(name="realization_status")
    private String realizationStatus;

    public Reservation()
    {
    }

    public Reservation(Date reservationFrom, Date reservationUtil) {
        this.reservationFrom = reservationFrom;
        this.reservationUtil = reservationUtil;
        Date currentDate = Dao.getDate();
        if(reservationFrom.getTime()==currentDate.getTime())
            this.realizationStatus = "IN REALIZATION";
        else
            this.realizationStatus="NOT STARTED";

    }

    public Reservation(Timestamp dateOfReservation, Date reservationFrom, Date reservationUtil, BigDecimal totalCost, Employee employee, Client client) {
        this.dateOfReservation = dateOfReservation;
        this.reservationFrom = reservationFrom;
        this.reservationUtil = reservationUtil;
        this.totalCost = totalCost;
        this.employee = employee;
        this.client = client;
    }

    public void changeReservationStatus()
    {
        if(realizationStatus.equals("NOT STARTED"))
        {
            realizationStatus="IN REALIZATION";
        }
        else if(realizationStatus.equals("IN REALIZATION"))
        {
            realizationStatus="ENDED";
        }
    }


    public void calculateTotalCost()
    {

        long tempTimeInMillis =reservationUtil.getTime()-reservationFrom.getTime();
        int numberOfDays = (int) TimeUnit.DAYS.convert(tempTimeInMillis, TimeUnit.MILLISECONDS)+1;
        int numberOfPerson = 0;
        for(int i=0; i<rooms.size();i++)
        {
            numberOfPerson+=rooms.get(i).getNumberOfPeople();
        }
        System.out.println(numberOfDays + " "+ numberOfPerson + " "+ SavedData.getPrice());
        totalCost = new BigDecimal(numberOfDays*numberOfPerson* SavedData.getPrice());
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

    public String getRealizationStatus() {
        return realizationStatus;
    }

    public void setCheckedOut(String realizationStatus) {
        this.realizationStatus = realizationStatus;
    }

    public void addRoom(Room room)
    {
        if(rooms==null)
        {
            rooms = new ArrayList<Room>();
        }
        rooms.add(room);
    }
    public void setRealizationStatus(String status)
    {
        this.realizationStatus=status;
    }

    public String toString()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String returning =  "Od "+format.format(reservationFrom) + " Do "+ format.format(reservationUtil.getTime()) +" Status "+ realizationStatus +" Koszt: "+totalCost+ " Pokoje: ";
        for(int i=0; i<rooms.size(); i++)
        {
            returning += " "+rooms.get(i).getNumberOfRoom();
        }
        returning+= "Osoba " +client.getName()+" "+client.getSurname()+ client.getPersonalIdNumber()+ "  Tel "+client.getPhoneNumber();
        return returning;
    }
}
