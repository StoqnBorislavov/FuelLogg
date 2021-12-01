package fuellogg.web;

import fuellogg.model.binding.VehicleAddBindingModel;
import fuellogg.model.view.ExpensesStatisticViewModel;
import fuellogg.model.view.FuelStatisticViewModel;
import fuellogg.service.BrandService;
import fuellogg.service.StatisticsExpensesService;
import fuellogg.service.StatisticsService;
import fuellogg.service.VehicleService;
import fuellogg.service.impl.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
public class VehicleController {

    private final BrandService brandService;
    private final VehicleService vehicleService;
    private final StatisticsService statisticsService;
    private final StatisticsExpensesService statisticsExpensesService;

    @Autowired
    public VehicleController(BrandService brandService, VehicleService vehicleService, StatisticsService statisticsService, StatisticsExpensesService statisticsExpensesService) {
        this.brandService = brandService;
        this.vehicleService = vehicleService;
        this.statisticsService = statisticsService;
        this.statisticsExpensesService = statisticsExpensesService;
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
            return "redirect:/addVehicle";
        }


        this.vehicleService.addVehicle(vehicleAddBindingModel, user.getUserIdentifier());
        return "redirect:/home";
    }

    @GetMapping("/vehicle/{id}/fueling")
    private String showFueling(@PathVariable Long id, Model model){
        List<FuelStatisticViewModel> fuelStatistic = this.statisticsService.getAllStatisticsByVehicleId(id);
        model.addAttribute("make", this.vehicleService.findVehicleById(id));
        model.addAttribute("fuelings", fuelStatistic);
        return "vehicleFueling";
    }

    @GetMapping("/vehicle/{id}/expenses")
    private String showExpenses(@PathVariable Long id, Model model){
        List<ExpensesStatisticViewModel> expenses = this.statisticsExpensesService.getAllStatisticsByVehicleId(id);
        model.addAttribute("make", this.vehicleService.findVehicleById(id));
        model.addAttribute("expenses", expenses);
        return "vehicleExpenses";
    }



    @ModelAttribute("vehicleAddBindingModel")
    public VehicleAddBindingModel vehicleAddBindingModel() {
        return new VehicleAddBindingModel();
    }

}
