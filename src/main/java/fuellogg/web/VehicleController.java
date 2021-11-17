package fuellogg.web;

import fuellogg.model.binding.VehicleAddBindingModel;
import fuellogg.service.BrandService;
import fuellogg.service.VehicleService;
import fuellogg.service.impl.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Controller
public class VehicleController {

    private final BrandService brandService;
    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(BrandService brandService, VehicleService vehicleService) {
        this.brandService = brandService;
        this.vehicleService = vehicleService;
    }


    @GetMapping("/vehicle/add")
    private String addVehicle(Model model){
        model.addAttribute("brandsModels", this.brandService.getAllBrands());
        return "addVehicle";
    }

    @PostMapping("/vehicle/add")
    private String addVehicleConfirm(@Valid VehicleAddBindingModel vehicleAddBindingModel, BindingResult result,
                                     RedirectAttributes redirectAttributes,
                                     @AuthenticationPrincipal MyUser user) throws IOException {
        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("vehicleAddBindingModel", vehicleAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.vehicleAddBindingModel", result);
            redirectAttributes.addFlashAttribute("brandsModels", brandService.getAllBrands());
            return "redirect:/vehicle/add";
        }

        this.vehicleService.addVehicle(vehicleAddBindingModel, user.getUserIdentifier());
        return "redirect:/home";
    }


    @ModelAttribute("vehicleAddBindingModel")
    public VehicleAddBindingModel vehicleAddBindingModel() {
        return new VehicleAddBindingModel();
    }

}
