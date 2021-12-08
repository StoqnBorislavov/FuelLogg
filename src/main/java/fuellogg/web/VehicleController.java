package fuellogg.web;

import fuellogg.model.binding.VehicleAddBindingModel;
import fuellogg.model.view.ExpensesStatisticViewModel;
import fuellogg.model.view.FuelStatisticViewModel;
import fuellogg.service.BrandService;
import fuellogg.service.StatisticsExpensesService;
import fuellogg.service.StatisticsFuelingService;
import fuellogg.service.VehicleService;
import fuellogg.service.impl.MyUser;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
import java.security.Principal;
import java.util.List;

@Controller
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class VehicleController {

    private final BrandService brandService;
    private final VehicleService vehicleService;
    private final StatisticsFuelingService statisticsFuelingService;
    private final StatisticsExpensesService statisticsExpensesService;

    @Autowired
    public VehicleController(BrandService brandService, VehicleService vehicleService, StatisticsFuelingService statisticsFuelingService, StatisticsExpensesService statisticsExpensesService) {
        this.brandService = brandService;
        this.vehicleService = vehicleService;
        this.statisticsFuelingService = statisticsFuelingService;
        this.statisticsExpensesService = statisticsExpensesService;
    }


    @GetMapping("/vehicle/add")
    private String addVehicle(Model model) {
        model.addAttribute("brandsModels", this.brandService.getAllBrands());
        return "add-vehicle";
    }

    @PostMapping("/vehicle/add")
    private String addVehicleConfirm(@Valid VehicleAddBindingModel vehicleAddBindingModel, BindingResult result,
                                     RedirectAttributes redirectAttributes,
                                     @AuthenticationPrincipal MyUser user) throws IOException {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("vehicleAddBindingModel", vehicleAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.vehicleAddBindingModel", result);
            redirectAttributes.addFlashAttribute("brandsModels", brandService.getAllBrands());
            return "redirect:/vehicle/add";
        }


        this.vehicleService.addVehicle(vehicleAddBindingModel, user.getUserIdentifier());
        return "redirect:/home";
    }

    //    @PreAuthorize("@vehicleServiceImpl.isOwner(#id, #user.name)")
    @GetMapping("/vehicle/{id}/fueling")
    public String showFueling(@PathVariable Long id, Model model, Principal user) {
        if (vehicleService.isOwner(id, user.getName())) {
            List<FuelStatisticViewModel> fuelStatistic = this.statisticsFuelingService.getAllStatisticsByVehicleId(id);
            model.addAttribute("make", this.vehicleService.findVehicleById(id));
            model.addAttribute("fuelings", fuelStatistic);
        } else {
            throw new AccessDeniedException("Access is denied");
        }
        return "vehicle-fueling-history";
    }

    //    @PreAuthorize("@vehicleServiceImpl.isOwner(#id, #user.name)")
    //    If I leave the preauthorize annotation spring IoC can't handle my beans for some reason
    @GetMapping("/vehicle/{id}/expenses")
    public String showExpenses(@PathVariable Long id, Principal user, Model model) throws NotFoundException {
        if (vehicleService.isOwner(id, user.getName())) {
        List<ExpensesStatisticViewModel> expenses = this.statisticsExpensesService.getAllStatisticsByVehicleId(id);
        model.addAttribute("make", this.vehicleService.findVehicleById(id));
        model.addAttribute("expenses", expenses);
        } else {
            throw new AccessDeniedException("Access is denied");
        }
        return"vehicle-expenses-history";
}


    @ModelAttribute("vehicleAddBindingModel")
    public VehicleAddBindingModel vehicleAddBindingModel() {
        return new VehicleAddBindingModel();
    }

}
