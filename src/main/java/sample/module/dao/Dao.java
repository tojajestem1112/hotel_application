package sample.module.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import sample.module.SavedData;
import sample.module.entities.Employee;
import sample.module.utils.Util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
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
    }




}
