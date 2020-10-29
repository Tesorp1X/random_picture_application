package dbService.dao;

import dbService.dataSets.PicturesDataSet;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;

public class PictureDAO {
    private Session session;

    public PictureDAO(Session session) {
        this.session = session;
    }


    public PicturesDataSet get(long id) throws HibernateException {

        return (PicturesDataSet) session.get(PicturesDataSet.class, id);
    }


    public PicturesDataSet getRandom() throws HibernateException {

        Criteria criteria = session.createCriteria(PicturesDataSet.class);
        long leftLimit = 1L;
        long rightLimit = this.getLastPictureId();
        long row_id = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));

        return (PicturesDataSet) criteria
                                    .add(Restrictions
                                    .idEq(row_id))
                                    .uniqueResult();
    }

    //TODO: implement getList() and deleteById()
    @SuppressWarnings("UnusedDeclaration")
    public long getPictureId(String name) throws HibernateException {

        Criteria criteria = session.createCriteria(PicturesDataSet.class);

        return ((PicturesDataSet) criteria.add(Restrictions.eq("name", name)).uniqueResult()).getId();
    }

    //TODO::getList
    public ArrayList<PicturesDataSet> getList(long start_id, long amount) {

        ArrayList<PicturesDataSet> result_list = new ArrayList<>();

        
        return result_list;
    }


    public long insertPicture(String link, String tags) throws HibernateException {

        return (Long) session.save(new PicturesDataSet(link, tags));
    }


    protected Long getLastPictureId() {

        Criteria criteria = session.createCriteria(PicturesDataSet.class);


        Object _count = criteria.setProjection(Projections.count("id")).uniqueResult();

        return (Long) _count;
    }

}
