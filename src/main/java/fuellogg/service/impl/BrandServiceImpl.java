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
        if(this.brandRepository.count() == 0) {
            Brand bmw = new Brand();
            bmw.setName("BMW");
            Brand mercedes = new Brand();
            mercedes.setName("Mercedes");
            Brand audi = new Brand();
            audi.setName("Audi");
            Brand vw = new Brand();
            vw.setName("Volkswagen");
            Brand opel = new Brand();
            opel.setName("Opel");
            Brand toyota = new Brand();
            toyota.setName("Toyota");
            Brand mazda = new Brand();
            mazda.setName("Mazda");
            Brand ford = new Brand();
            ford.setName("Ford");
            Brand fiat = new Brand();
            fiat.setName("Fiat");
            Brand alfaRomeo = new Brand();
            alfaRomeo.setName("Alfa Romeo");
            Brand chevrolet = new Brand();
            chevrolet.setName("Chevrolet");
            this.brandRepository
                    .saveAll(List.of(bmw, mercedes, audi, vw, opel, toyota, mazda, ford, fiat, alfaRomeo, chevrolet));
        }
    }

    @Override
    public List<BrandViewModel> getAllBrands() {
        return this.brandRepository.findAll()
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
        return this.modelMapper.map(modelEntity, ModelViewModel.class);
    }
}
