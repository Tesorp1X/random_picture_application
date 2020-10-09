package dbService.dataSets;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "IMAGES")
public class PicturesDataSet implements Serializable { // Serializable Important to Hibernate!
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Column(name = "link", unique = true, updatable = false, nullable = false)
    private String link;

    @Column(name = "tags", unique = false, updatable = true, nullable = true)
    private String tags;

    //Important to Hibernate!
    @SuppressWarnings("UnusedDeclaration")
    public PicturesDataSet() { }

    @SuppressWarnings("UnusedDeclaration")
    PicturesDataSet(long id, String link, String tags) {

        this.id = id;
        this.link = link;
        this.tags = tags;
    }

    @SuppressWarnings("UnusedDeclaration")
    public PicturesDataSet(String link, String tags) {

        this.link = link;
        this.tags = tags;
    }

    @SuppressWarnings("UnusedDeclaration")
    public PicturesDataSet(long id, String link) {

        this.setId(id);
        this.setLink(link);
    }

    public PicturesDataSet(String name) {

        this.setId(-1);
        this.setLink(name);
    }

    @SuppressWarnings("UnusedDeclaration")
    public String getTags() {

        return tags.toString();
    }

    public void setTags(String tags) {

        this.tags = tags;
    }

    @SuppressWarnings("UnusedDeclaration")
    public String getLink() {

        return link;
    }

    public void setLink(String name) {

        this.link = name;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {

        this.id = id;
    }

    @Override
    public String toString() {

        return "PictureDataSet{" +
                "id=" + id +
                ", name='" + link + '\'' +
                '}';
    }
}
