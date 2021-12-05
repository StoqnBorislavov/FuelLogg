package fuellogg.service.impl;

import fuellogg.model.entity.Brand;
import fuellogg.model.entity.Model;
import fuellogg.model.exception.ObjectNotFoundException;
import fuellogg.model.view.ModelViewModel;
import fuellogg.repository.BrandRepository;
import fuellogg.repository.ModelRepository;
import fuellogg.service.ModelService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModelServiceImpl implements ModelService {

    private final BrandRepository brandRepository;
    private final ModelRepository modelRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ModelServiceImpl(BrandRepository brandRepository, ModelRepository modelRepository, ModelMapper modelMapper) {
        this.brandRepository = brandRepository;
        this.modelRepository = modelRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initializeModels() {
        if (modelRepository.count() == 0) {

            Brand mercedes = this.brandRepository.findByName("Mercedes").orElseThrow(()-> new ObjectNotFoundException("Brand not found!"));
            Model model = new Model();
            model.setName("E class").setBrand(mercedes);
            Model model1 = new Model();
            model1.setName("S class").setBrand(mercedes);
            Model model2 = new Model();
            model2.setName("C class").setBrand(mercedes);

            Brand bmw = this.brandRepository.findByName("BMW").orElseThrow(()-> new ObjectNotFoundException("Brand not found!"));
            Model model3 = new Model();
            model3.setName("3 series").setBrand(bmw);
            Model model4 = new Model();
            model4.setName("5 series").setBrand(bmw);
            Model model5 = new Model();
            model5.setName("6 series").setBrand(bmw);

            Brand audi = this.brandRepository.findByName("Audi").orElseThrow(()-> new ObjectNotFoundException("Brand not found!"));
            Model model6 = new Model();
            model6.setName("A3").setBrand(audi);
            Model model7 = new Model();
            model7.setName("A5").setBrand(audi);
            Model model8 = new Model();
            model8.setName("A7").setBrand(audi);

            this.modelRepository.saveAll(List.of(model, model1, model2, model3, model4, model5, model6, model7, model8));
        }
    }

    @Override
    public Model findById(Long modelId) {
        return this.modelRepository.findById(modelId).orElseThrow(()-> new ObjectNotFoundException("Model not found!"));
    }

    @Override
    public List<ModelViewModel> getAllByBrandName(String brandName) {
        Brand brandEntity = brandRepository.findByName(brandName)
                .orElseThrow(()-> new ObjectNotFoundException("Brand not found!"));
        List<Model> models = brandEntity.getModels();
        for (Model model : models) {
            ModelViewModel mvm = modelMapper.map(model, ModelViewModel.class);
        }
        return brandEntity.getModels().stream().map(m -> modelMapper.map(m, ModelViewModel.class)).collect(Collectors.toList());
    }

    @Override
    public ModelViewModel findByName(String modelName) {
        return this.modelRepository.findByName(modelName)
                .map(model -> modelMapper.map(model, ModelViewModel.class))
                .orElseThrow(()-> new ObjectNotFoundException("Model not found!"));
    }


}
