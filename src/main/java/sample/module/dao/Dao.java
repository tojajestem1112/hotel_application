package sample.module.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import sample.module.SavedData;
import sample.module.entities.*;
import sample.module.utils.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Dao
{
    private static SessionFactory factory = null;
    private static LinkedList<Object> insertList = new LinkedList<>();
    public Dao(){
        if(factory==null) {
            Util util = new Util();
            factory = util.getSessionFactory();
        }
    }
    public static void insert(Object obj)
    {

        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.save(obj);
        session.getTransaction().commit();
    }
    public static void delete(Object obj)
    {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.delete(obj);
        session.getTransaction().commit();
    }

    public static void update(Object obj)
    {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.update(obj);
        session.getTransaction().commit();
    }
    public static List<Object> get(Class clazz)
    {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        String quer = "FROM "+clazz.getName();
        Query query = session.createQuery(quer);
        List<Object> list = query.getResultList();
        session.close();
        return list;
    }
    public static void logIn(String email, String password)
    {
        Session session=factory.getCurrentSession();
        session.beginTransaction();
        List<Employee> employee = session.createQuery("SELECT a FROM Employee a " +
                "WHERE a.emailAdress ='"+email+"' AND a.password ='"+password+"'"
                 , Employee.class).getResultList();
        session.close();
        if(employee.size()==1)
            SavedData.setLoggedEmployee(employee.get(0));
        session.close();
    }
    public static List<Timetable> getTimetableOfEmployee(Date date1, Date date2, Employee empl) throws ParseException {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        String hql = "FROM Timetable E WHERE E.date between :begin_date and :end_date and E.employee = :id ORDER BY E.date ASC";
        Query query = session.createQuery(hql);
        query.setParameter("begin_date", date1);
        query.setParameter("end_date", date2);
        query.setParameter("id", empl);

        List<Timetable> list = query.getResultList();
        session.close();
        return list;
    }
    public static List<Client> getClient(String personId)
    {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        String hql = "FROM Client E WHERE E.personalIdNumber = '"+personId+"'";;
        Query query = session.createQuery(hql);
        List<Client> list = query.getResultList();
        session.close();
        return list;
    }
    public static List<Reservation> getReservations(Client client)
    {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        String hql = "FROM Reservation E WHERE E.client = :client";;
        Query query = session.createQuery(hql);
        query.setParameter("client", client);
        List<Reservation> list = query.getResultList();
        session.close();
        return list;
    }
    public static Date getDate()
    {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        String hql = "SELECT CURRENT_DATE() from Client";
        Query query = session.createQuery(hql);
        List<Date> dates = query.getResultList();
        session.close();
        return dates.get(0);
    }
    public static List<Integer> getBusyRooms(Date date1, Date date2)
    {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        String hql = "SELECT distinct r FROM Room r left outer join r.reservations res "
                +" WHERE :date1 between res.reservationFrom and res.reservationUtil "
                +" OR :date2 between res.reservationFrom and res.reservationUtil "
                +" OR res.reservationFrom between :date1 and :date2 "
                +" OR res.reservationUtil between :date1 and :date2";
        Query query = session.createQuery(hql);
        query.setParameter("date1", date1);
        query.setParameter("date2", date2);
        List<Room> rooms = query.getResultList();
        List<Integer> returning = new ArrayList<>();
        for(int i=0; i<rooms.size(); i++)
        {
            returning.add(rooms.get(i).getNumberOfRoom());
        }

        session.close();
        return returning;
    }


}
