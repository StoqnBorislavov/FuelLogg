package fuellogg.web;

import fuellogg.model.binding.AddExpensesBindingModel;
import fuellogg.model.binding.AddFuelBindingModel;
import fuellogg.model.service.AddExpensesServiceModel;
import fuellogg.model.service.AddFuelServiceModel;
import fuellogg.service.StatisticsExpensesService;
import fuellogg.service.StatisticsService;
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
    private final StatisticsService statisticsService;
    private final ModelMapper modelMapper;
    private final StatisticsExpensesService statisticsExpensesService;


    public StatisticsController(VehicleService vehicleService, StatisticsService statisticsService, ModelMapper modelMapper, StatisticsExpensesService statisticsExpensesService) {
        this.vehicleService = vehicleService;
        this.statisticsService = statisticsService;
        this.modelMapper = modelMapper;
        this.statisticsExpensesService = statisticsExpensesService;
    }

    @ModelAttribute
    public AddExpensesBindingModel addExpensesBindingModel(@PathVariable Long id) {
        return new AddExpensesBindingModel().setVehicleId(id).setOdometer(this.vehicleService.lastOdometer(id));
    }


    @ModelAttribute
    public AddFuelBindingModel addFuelBindingModel(@PathVariable Long id, Principal user) {
        return new AddFuelBindingModel().setVehicleId(id).setOdometer(this.vehicleService.lastOdometer(id));
    }

    @PreAuthorize("@statisticsServiceImpl.canUseAddFunction(#id, #user.name)")
    @GetMapping("/addFuel/{id}")
    public String addFuel(@PathVariable Long id, Principal user) {
        return "refuel";
    }

    @PreAuthorize("@statisticsServiceImpl.canUseAddFunction(#id, #user.name)")
    @PostMapping("/addFuel/{id}")
    public String addFuelConfirm(@PathVariable Long id,
                                 @Valid AddFuelBindingModel addFuelBindingModel,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes, Principal user)throws ObjectNotFoundException {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("addFuelBindingModel", addFuelBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addFuelBindingModel", result);
            Long vehicleId = addFuelBindingModel.getVehicleId();
            return "redirect:/addFuel/{vehicleId}";
        }

        this.statisticsService.addFuel(this.modelMapper.map(addFuelBindingModel, AddFuelServiceModel.class));

        return "redirect:/vehicle/{id}/fueling";
    }

    @PreAuthorize("@statisticsServiceImpl.canUseAddFunction(#id, #user.name)")
    @GetMapping("/addExpenses/{id}")
    public String addExpenses(@PathVariable Long id, Principal user) {
        return "addExpenses";
    }

    @PreAuthorize("@statisticsServiceImpl.canUseAddFunction(#id, #user.name)")
    @PostMapping("/addExpenses/{id}")
    public String addFuelConfirm(@PathVariable Long id,
                                 @Valid AddExpensesBindingModel addExpensesBindingModel,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes, Principal user) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("addExpensesBindingModel", addExpensesBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addExpensesBindingModel", result);
            Long vehicleId = addExpensesBindingModel.getVehicleId();
            return "redirect:/addExpenses/{vehicleId}";
        }

        this.statisticsExpensesService.addExpenses(this.modelMapper.map(addExpensesBindingModel, AddExpensesServiceModel.class));

        return "redirect:/vehicle/{id}/expenses";
    }

    @GetMapping("/details/{id}")
    public String detailsView(@PathVariable Long id, Model model) {
        model.addAttribute("stat", this.statisticsService.getCurrentStatisticView(id));
        return "detailsOnFueling";
    }
}
