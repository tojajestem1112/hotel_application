package sample.module.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import sample.module.SavedData;
import sample.module.entities.Employee;
import sample.module.entities.Timetable;
import sample.module.utils.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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



}
