package fuellogg.service;

import fuellogg.model.entity.Model;
import fuellogg.model.view.ModelViewModel;

import java.util.List;
import java.util.Optional;

public interface ModelService {
    void initializeModels();

    Model findById(Long modelId);

    List<ModelViewModel> getAllByBrandName(String brandName);

}

