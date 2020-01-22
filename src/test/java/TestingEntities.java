import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import sample.model.entities.*;

public class TestingEntities
{
    public static void main(String [] args) {
        SessionFactory factory = new Configuration()
                .configure("sample/model/hibernate.cfg.xml")
                .addAnnotatedClass(Client.class)
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Reservation.class)
                .addAnnotatedClass(Room.class)
                .addAnnotatedClass(Timetable.class)
                .buildSessionFactory();
        Session session = factory.getCurrentSession();
        try{
            session.beginTransaction();
            addClients(session);
            //addEmployee(session);
            //addRoom(session);
            //addTimetable(session, 2);
            //
            addReservation(session);
        }
        finally{
            session.close();
            factory.close();
        }
    }
    public static void addClients(Session session) {
        /*Client temp1 = new Client("Dawid", "Jalowski");
        Client temp2 = new Client("Dawid", "Jalowski2", "+48 999 999 999", "jalowskidaw@gmail.com");
        session.save(temp1);
        session.save(temp2);
        session.getTransaction().commit();*/
    }
    public static void addEmployee(Session session) {
        Employee empl1 = new Employee("Dawid", "Jalowski1", "AlaMaKota", "Administrator");
        Employee empl2 = new Employee("Dawid", "Okoń", "Hasło", "+48 888 888 888","zuzajal@gmail.com", "Cleaner");
        session.save(empl1);
        session.save(empl2);
        session.getTransaction().commit();
    }
    public static void addRoom(Session session) {
        Room room1 = new Room(1,2);
        Room room2 = new Room(2,1);
        session.save(room1);
        session.save(room2);
        session.getTransaction().commit();
    }
    public static void addTimetable(Session session, int id) {
        /*Employee employee = session.get(Employee.class, id);
        Timetable tt = new Timetable("2017-11-16","16:30:00", "17:30:00");
        tt.setEmployee(employee);
        session.save(tt);
        session.getTransaction().commit();*/
    }
    public static void addReservation(Session session){
      /*  Date date1 = Date.valueOf("2019-10-30");
        Date date2 = Date.valueOf("2019-11-30");
        Reservation res = new Reservation(date1,date2,true);
        res.setClient(session.get(Client.class, 1));
        res.setEmployee(session.get(Employee.class, 1));
        res.addRoom(session.get(Room.class, 1));
        res.addRoom(session.get(Room.class,2));
        res.setTotalCost(new BigDecimal(20.20));
        session.save(res);
        session.getTransaction().commit();*/
    }
}
