package fuellogg.model.view;

public class ModelViewModel {

    private Long id;
    private String name;

    public ModelViewModel() {

    }

    public String getName() {
        return name;
    }

    public ModelViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public Long getId() {
        return id;
    }

    public ModelViewModel setId(Long id) {
        this.id = id;
        return this;
    }

}
