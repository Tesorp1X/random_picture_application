package dbService.dao;

import dbService.dataSets.PicturesDataSet;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class PictureDAO {
    private Session session;

    public PictureDAO(Session session) {
        this.session = session;
    }

    public PicturesDataSet get() throws HibernateException {
        long id;

        return (PicturesDataSet) session.get(PicturesDataSet.class, id);
    }

    public long getPictureId(String name) throws HibernateException {

        Criteria criteria = session.createCriteria(PicturesDataSet.class);
        return ((PicturesDataSet) criteria.add(Restrictions.eq("name", name)).uniqueResult()).getId();
    }

    public long insertPicture(String link, String tags) throws HibernateException {
        return (Long) session.save(new PicturesDataSet(link, tags));
    }
}