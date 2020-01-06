package sample.module.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import sample.module.entities.*;

public class Util
{
    private SessionFactory factory;
    public Util() {
        factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Client.class)
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Reservation.class)
                .addAnnotatedClass(Room.class)
                .addAnnotatedClass(Timetable.class)
                .buildSessionFactory();
    }
    public SessionFactory getSessionFactory(){return factory;}
}
