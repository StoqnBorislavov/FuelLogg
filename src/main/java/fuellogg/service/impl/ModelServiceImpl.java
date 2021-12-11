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

            Brand vw = this.brandRepository.findByName("Volkswagen").orElseThrow(()-> new ObjectNotFoundException("Brand not found!"));
            Model model9 = new Model();
            model9.setName("Golf").setBrand(vw);
            Model model10 = new Model();
            model10.setName("Passat").setBrand(vw);
            Model model11 = new Model();
            model11.setName("Polo").setBrand(vw);

            Brand opel = this.brandRepository.findByName("Opel").orElseThrow(()-> new ObjectNotFoundException("Brand not found!"));
            Model model12 = new Model();
            model12.setName("Astra").setBrand(opel);
            Model model13= new Model();
            model13.setName("Vectra").setBrand(opel);
            Model model14 = new Model();
            model14.setName("Insignia").setBrand(opel);

            Brand toyota = this.brandRepository.findByName("Toyota").orElseThrow(()-> new ObjectNotFoundException("Brand not found!"));
            Model model15 = new Model();
            model15.setName("Avensis").setBrand(toyota);
            Model model16= new Model();
            model16.setName("Corolla").setBrand(toyota);
            Model model17 = new Model();
            model17.setName("Aygo").setBrand(toyota);

            Brand mazda = this.brandRepository.findByName("Mazda").orElseThrow(()-> new ObjectNotFoundException("Brand not found!"));
            Model model18 = new Model();
            model18.setName("3").setBrand(mazda);
            Model model19= new Model();
            model19.setName("6").setBrand(mazda);
            Model model20 = new Model();
            model20.setName("CX").setBrand(mazda);

            Brand ford = this.brandRepository.findByName("Ford").orElseThrow(()-> new ObjectNotFoundException("Brand not found!"));
            Model model21 = new Model();
            model21.setName("Fiesta").setBrand(ford);
            Model model22= new Model();
            model22.setName("Focus").setBrand(ford);
            Model model23 = new Model();
            model23.setName("Escort").setBrand(ford);

            Brand fiat = this.brandRepository.findByName("Fiat").orElseThrow(()-> new ObjectNotFoundException("Brand not found!"));
            Model model24 = new Model();
            model24.setName("Punto").setBrand(fiat);
            Model model25= new Model();
            model25.setName("Grande Punto").setBrand(fiat);
            Model model26 = new Model();
            model26.setName("500").setBrand(fiat);

            Brand alfaRomeo = this.brandRepository.findByName("Alfa Romeo").orElseThrow(()-> new ObjectNotFoundException("Brand not found!"));
            Model model27 = new Model();
            model27.setName("159").setBrand(alfaRomeo);
            Model model28= new Model();
            model28.setName("156").setBrand(alfaRomeo);
            Model model29 = new Model();
            model29.setName("Gulia").setBrand(alfaRomeo);

            Brand chevrolet = this.brandRepository.findByName("Chevrolet").orElseThrow(()-> new ObjectNotFoundException("Brand not found!"));
            Model model30 = new Model();
            model30.setName("Corvette").setBrand(chevrolet);
            Model model31= new Model();
            model31.setName("Camaro").setBrand(chevrolet);
            Model model32 = new Model();
            model32.setName("Cruze").setBrand(chevrolet);

            this.modelRepository.saveAll(List.of(model, model1, model2, model3, model4, model5, model6, model7, model8, model9, model10, model11, model12, model13, model14, model15, model16, model17, model18, model19, model20, model21, model22, model23, model24, model25, model26, model27, model28, model29, model30, model31, model32 ));
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
