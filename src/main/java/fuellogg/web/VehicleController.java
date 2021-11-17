package fuellogg.web;

import fuellogg.model.binding.VehicleAddBindingModel;
import fuellogg.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class VehicleController {

    private final BrandService brandService;

    @Autowired
    public VehicleController(BrandService brandService) {
        this.brandService = brandService;
    }


    @GetMapping("/vehicle/add")
    private String addVehicle(Model model){
        model.addAttribute("brandsModels", this.brandService.getAllBrands());
        return "addVehicle";
    }

    @PostMapping("/vehicle/add")
    private String addVehicleConfirm(@Valid VehicleAddBindingModel vehicleAddBindingModel, BindingResult result,
                                     RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("vehicleAddBindingModel", vehicleAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.vehicleAddBindingModel", result);
            redirectAttributes.addFlashAttribute("brandsModels", brandService.getAllBrands());
            return "redirect:/vehicle/add";
        }

        return "redirect:/home";
    }


    @ModelAttribute("vehicleAddBindingModel")
    public VehicleAddBindingModel vehicleAddBindingModel() {
        return new VehicleAddBindingModel();
    }

}
