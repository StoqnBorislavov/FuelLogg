package fuellogg.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity {

    private String url;
    private String publicId;

    public Picture() {
    }

    public String getUrl() {
        return url;
    }

    public Picture setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getPublicId() {
        return publicId;
    }

    public Picture setPublicId(String publicId) {
        this.publicId = publicId;
        return this;
    }
}
