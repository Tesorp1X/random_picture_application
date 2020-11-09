package dbService.dao;

import dbService.EmptyTableException;
import dbService.dataSets.PicturesDataSet;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.List;
import java.util.Random;

public class PictureDAO {

    private final Session session;

    public PictureDAO(Session session) {
        this.session = session;
    }


    public PicturesDataSet getPictureById(long id) {

        return session.get(PicturesDataSet.class, id);
    }


    public PicturesDataSet getRandom() throws EmptyTableException {

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cqCount = cb.createQuery(Long.class);
        Root<PicturesDataSet> picturesCountRoot = cqCount.from(PicturesDataSet.class);

        cqCount.select(cb.count(picturesCountRoot));
        int count = session.createQuery(cqCount).getSingleResult().intValue();

        if (count != 0) {
            int index = new Random().nextInt(count);

            CriteriaQuery<PicturesDataSet> cqPicture = cb.createQuery(PicturesDataSet.class);
            Root<PicturesDataSet> picturesRoot = cqPicture.from(PicturesDataSet.class);
            CriteriaQuery<PicturesDataSet> random = cqPicture.select(picturesRoot);

            TypedQuery<PicturesDataSet> randomQuery = session.createQuery(random).setFirstResult(index).setMaxResults(1);

            return randomQuery.getSingleResult();

        } else {

            throw new EmptyTableException("No pictures were found in DB.");
        }

    }


    private List<PicturesDataSet> getListOfPictures_(int start_point, int max_result) {

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PicturesDataSet> cqPicture = cb.createQuery(PicturesDataSet.class);
        Root<PicturesDataSet> picturesRoot = cqPicture.from(PicturesDataSet.class);
        CriteriaQuery<PicturesDataSet> all = cqPicture.select(picturesRoot);
        TypedQuery<PicturesDataSet> allQuery = session.createQuery(all);

        if (max_result != -1) {
            allQuery.setFirstResult(start_point).setMaxResults(max_result);
        }

        return allQuery.getResultList();

    }

    public List<PicturesDataSet> getListOfPictures() {

        return getListOfPictures_(-1, -1);
    }

    public List<PicturesDataSet> getListOfPictures(int start_point, int max_result) {

        return getListOfPictures_(start_point, max_result);
    }

    public long insertPicture(String link, String tags) throws HibernateException {

        return (Long) session.save(new PicturesDataSet(link, tags));
    }

    public boolean deletePictureById(long id) {

        PicturesDataSet picture = getPictureById(id);

        if (picture != null) {
            session.delete(picture);
            return true;
        }

        return false;
    }

}
