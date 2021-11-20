package fuellogg.model.view;

public class ModelViewModel {

    private Long id;
    private BrandViewModel name;

    public ModelViewModel() {

    }

    public Long getId() {
        return id;
    }

    public ModelViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public BrandViewModel getName() {
        return name;
    }

    public ModelViewModel setName(BrandViewModel name) {
        this.name = name;
        return this;
    }
}
