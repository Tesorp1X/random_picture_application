package dbService;


import configurator.Configurator;

import dbService.dao.PictureDAO;
import dbService.dataSets.PicturesDataSet;

import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.LinkedList;
import java.util.List;



public class DBService {

    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "none";

    private final SessionFactory sessionFactory;

    public DBService() {
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



    public PicturesDataSet getRandomPicture() throws  EmptyTableException {

        Session session = sessionFactory.openSession();
        PictureDAO dao = new PictureDAO(session);

        return dao.getRandom();

    }

    public PicturesDataSet getPictureById(long id) throws NotFoundException, IndexOutOfBoundsException {

        if (id < 0) {
            throw new IndexOutOfBoundsException(String.valueOf(id));
        }

        try {

            Session session = sessionFactory.openSession();
            PictureDAO dao = new PictureDAO(session);

            return dao.getPictureById(id);

        } catch (HibernateException e) {
            throw new NotFoundException(String.valueOf(id));
        }
    }

    public long addPicture(String url, String tags) {

        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            PictureDAO dao = new PictureDAO(session);
            long id = dao.insertPicture(url, tags);
            transaction.commit();
            session.close();

            return id;

        } catch (HibernateException e) {
            return -1;
        }

    }

    public void deletePictureById(long id) throws NotFoundException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        PictureDAO dao = new PictureDAO(session);

        boolean is_deleted = dao.deletePictureById(id);
        transaction.commit();
        session.close();

        if (!is_deleted) {

            throw new NotFoundException(String.valueOf(id));
        }
    }

    public List<PicturesDataSet> getListOfPictures() {
        Session session = sessionFactory.openSession();
        PictureDAO dao = new PictureDAO(session);

        List<PicturesDataSet> result_list = dao.getListOfPictures();
        session.close();

        return new LinkedList<>(result_list);
    }

    public List<PicturesDataSet> getListOfPictures(int start_point, int max_result) {
        Session session = sessionFactory.openSession();
        PictureDAO dao = new PictureDAO(session);

        List<PicturesDataSet> result_list = dao.getListOfPictures(start_point, max_result);
        session.close();

        return new LinkedList<>(result_list);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();

        return configuration.buildSessionFactory(serviceRegistry);
    }
}