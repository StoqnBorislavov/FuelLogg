package fuellogg.service;

import fuellogg.model.entity.Model;

import java.util.Optional;

public interface ModelService {
    void initializeModels();

    Model findById(Long modelId);
}

