package fuellogg.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "models")
public class Model extends BaseEntity{

    private String name;
    private Brand brand;

    public Model() {
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public Model setName(String name) {
        this.name = name;
        return this;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public Brand getBrand() {
        return brand;
    }

    public Model setBrand(Brand brand) {
        this.brand = brand;
        return this;
    }
}
