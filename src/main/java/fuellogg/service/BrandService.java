package fuellogg.service;

import fuellogg.model.view.BrandViewModel;

import java.util.List;

public interface BrandService {
    void initializeBrands();

    List<BrandViewModel> getAllBrands();
}
