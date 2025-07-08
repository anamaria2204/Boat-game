package joc.persistence.hibernate;

import joc.model.Configuratie;
import joc.model.Joc;
import joc.model.Jucator;
import joc.model.Mutare;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class HibernateUtils {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(){
        if ((sessionFactory==null)||(sessionFactory.isClosed()))
            sessionFactory=createNewSessionFactory();
        return sessionFactory;
    }

    private static  SessionFactory createNewSessionFactory(){
        try {
            var inputStream = HibernateUtils.class.getClassLoader()
                    .getResourceAsStream("hibernate.properties");

            Properties properties = new Properties();
            properties.load(inputStream);

            Configuration sessionFactory = new Configuration()
                    .addProperties(properties)
                    .addAnnotatedClass(Jucator.class)
                    .addAnnotatedClass(Joc.class)
                    .addAnnotatedClass(Configuratie.class)
                    .addAnnotatedClass(Mutare.class);

            return sessionFactory.buildSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create SessionFactory", e);
        }
    }

    public static  void closeSessionFactory(){
        if (sessionFactory!=null)
            sessionFactory.close();
    }
}
