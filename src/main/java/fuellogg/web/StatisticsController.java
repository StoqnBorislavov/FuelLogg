package fuellogg.web;

import fuellogg.model.binding.AddExpensesBindingModel;
import fuellogg.model.binding.AddFuelBindingModel;
import fuellogg.model.service.AddExpensesServiceModel;
import fuellogg.model.service.AddFuelServiceModel;
import fuellogg.service.StatisticsExpensesService;
import fuellogg.service.StatisticsFuelingService;
import fuellogg.service.VehicleService;
import javassist.tools.rmi.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequestMapping("/statistics")
public class StatisticsController {

    private final VehicleService vehicleService;
    private final StatisticsFuelingService statisticsFuelingService;
    private final ModelMapper modelMapper;
    private final StatisticsExpensesService statisticsExpensesService;


    public StatisticsController(VehicleService vehicleService, StatisticsFuelingService statisticsFuelingService, ModelMapper modelMapper, StatisticsExpensesService statisticsExpensesService) {
        this.vehicleService = vehicleService;
        this.statisticsFuelingService = statisticsFuelingService;
        this.modelMapper = modelMapper;
        this.statisticsExpensesService = statisticsExpensesService;
    }

    @ModelAttribute
    public AddExpensesBindingModel addExpensesBindingModel(@PathVariable Long id) {
        return new AddExpensesBindingModel().setVehicleId(id);
    }


    @ModelAttribute
    public AddFuelBindingModel addFuelBindingModel(@PathVariable Long id, Principal user) {
        return new AddFuelBindingModel().setVehicleId(id);
//        setOdometer(this.vehicleService.lastOdometer(id));
    }

    @PreAuthorize("@statisticsFuelingServiceImpl.canUseAddFunction(#id, #user.name)")
    @GetMapping("/addFuel/{id}")
    public String addFuel(@PathVariable Long id, Principal user) {
        return "refuel";
    }

    @PreAuthorize("@statisticsFuelingServiceImpl.canUseAddFunction(#id, #user.name)")
    @PostMapping("/addFuel/{id}")
    public String addFuelConfirm(@PathVariable Long id,
                                 @Valid AddFuelBindingModel addFuelBindingModel,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes, Principal user)throws ObjectNotFoundException {
        if (result.hasErrors()  || addFuelBindingModel.getOdometer() <= this.vehicleService.lastOdometer(id)) {
            redirectAttributes.addFlashAttribute("addFuelBindingModel", addFuelBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addFuelBindingModel", result);
            if(addFuelBindingModel.getOdometer() <= this.vehicleService.lastOdometer(id)){
                redirectAttributes.addFlashAttribute("wrongMileage", true);
            }
            return "redirect:/statistics/addFuel/{id}";
        }

        this.statisticsFuelingService.addFuel(this.modelMapper.map(addFuelBindingModel, AddFuelServiceModel.class));

        return "redirect:/vehicle/{id}/fueling";
    }

    @PreAuthorize("@statisticsFuelingServiceImpl.canUseAddFunction(#id, #user.name)")
    @GetMapping("/addExpenses/{id}")
    public String addExpenses(@PathVariable Long id, Principal user) {
        return "add-expenses";
    }

    @PreAuthorize("@statisticsFuelingServiceImpl.canUseAddFunction(#id, #user.name)")
    @PostMapping("/addExpenses/{id}")
    public String addFuelConfirm(@PathVariable Long id,
                                 @Valid AddExpensesBindingModel addExpensesBindingModel,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes, Principal user) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("addExpensesBindingModel", addExpensesBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addExpensesBindingModel", result);
            return "redirect:/statistics/addExpenses/{id}";
        }

        this.statisticsExpensesService.addExpenses(this.modelMapper.map(addExpensesBindingModel, AddExpensesServiceModel.class));

        return "redirect:/vehicle/{id}/expenses";
    }

    @GetMapping("/fueling/{id}/details")
    public String detailsViewForFuelings(@PathVariable Long id, Model model) {
        model.addAttribute("stat", this.statisticsFuelingService.getCurrentStatisticView(id));
        return "details-on-fueling";
    }

    @GetMapping("/expenses/{id}/details")
    public String detailsViewForExpenses(@PathVariable Long id, Model model) {
        model.addAttribute("stat", this.statisticsExpensesService.getCurrentStatisticView(id));
        return "details-on-expenses";
    }
}
