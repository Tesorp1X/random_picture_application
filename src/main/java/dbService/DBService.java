package dbService;


import configurator.Configurator;
import dbService.dao.PictureDAO;
import dbService.dataSets.PicturesDataSet;

import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.SQLException;



public class DBService {
    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "none";

    private final SessionFactory sessionFactory;

    public DBService() {
        //Configuration configuration = getH2Configuration();
        Configuration configuration = getMySqlConfiguration();
        sessionFactory = createSessionFactory(configuration);
    }

    private Configuration getMySqlConfiguration() {

        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(PicturesDataSet.class);

        Configurator configurator = new Configurator("mysql.conf");

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", configurator.getUrl());
        configuration.setProperty("hibernate.connection.username", configurator.getUser());
        configuration.setProperty("hibernate.connection.password", configurator.getPassword());
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);

        return configuration;
    }
    @SuppressWarnings("UnusedDeclaration")
    private Configuration getH2Configuration() {

        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(PicturesDataSet.class);

        Configurator configurator = new Configurator("h2.conf");

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", configurator.getUrl());
        configuration.setProperty("hibernate.connection.username", configurator.getUser());
        configuration.setProperty("hibernate.connection.password", configurator.getPassword());
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);

        return configuration;
    }


    public PicturesDataSet getRandomPicture() throws DBException {
        try {

            Session session = sessionFactory.openSession();
            PictureDAO dao = new PictureDAO(session);

            return dao.getRandom();

        } catch (HibernateException e) {

            throw new DBException(e);
        }
    }

    public PicturesDataSet getPictureById(long id) throws DBException {

        try {

            Session session = sessionFactory.openSession();
            PictureDAO dao = new PictureDAO(session);

            return dao.get(id);

        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public long addPicture(String url, String tags) throws DBException {

        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            PictureDAO dao = new PictureDAO(session);
            long id = dao.insertPicture(url, tags);
            transaction.commit();
            session.close();

            return id;

        } catch (HibernateException e) {
            throw new DBException(e);
        }

    }

    public void printConnectInfo() {
        try {

            SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sessionFactory;
            Connection connection = sessionFactoryImpl.getConnectionProvider().getConnection();
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();

        return configuration.buildSessionFactory(serviceRegistry);
    }
}