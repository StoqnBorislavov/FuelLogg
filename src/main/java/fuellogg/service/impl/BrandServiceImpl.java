package fuellogg.service.impl;

import fuellogg.model.entity.Brand;
import fuellogg.model.entity.Model;
import fuellogg.model.view.BrandViewModel;
import fuellogg.model.view.ModelViewModel;
import fuellogg.repository.BrandRepository;
import fuellogg.service.BrandService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository, ModelMapper modelMapper) {

        this.brandRepository = brandRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public void initializeBrands() {
        Brand bmw = new Brand();
        bmw.setName("BMW");
        Brand mercedes = new Brand();
        mercedes.setName("Mercedes");
        Brand audi = new Brand();
        audi.setName("Audi");

        this.brandRepository.saveAll(List.of(bmw, mercedes, audi));
    }

    @Override
    public List<BrandViewModel> getAllBrands() {
        return brandRepository.findAll()
                .stream()
                .map(brand -> {
                    BrandViewModel brandViewModel = new BrandViewModel().
                            setName(brand.getName());

                    brandViewModel.setModels(
                            brand.
                                    getModels().
                                    stream().
                                    map(this::map).
                                    collect(Collectors.toList()));
                    return brandViewModel;
                })
                .collect(Collectors.toList());
    }

    private ModelViewModel map(Model modelEntity) {
        return modelMapper.map(modelEntity, ModelViewModel.class);
    }
}
