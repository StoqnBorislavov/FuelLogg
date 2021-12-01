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
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
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
    public AddExpensesBindingModel addExpensesBindingModel(@PathVariable Long id){
        return new AddExpensesBindingModel().setVehicleId(id).setOdometer(this.vehicleService.lastOdometer(id));
    }


    @ModelAttribute
    public AddFuelBindingModel addFuelBindingModel(@PathVariable Long id){
        return new AddFuelBindingModel().setVehicleId(id).setOdometer(this.vehicleService.lastOdometer(id));
    }

    @GetMapping("/addFuel/{id}")
    public String addFuel(@PathVariable Long id){
        return "refuel";
    }

    @PostMapping("/addFuel/{id}")
    public String addFuelConfirm(@PathVariable Long id,
                                 @Valid AddFuelBindingModel addFuelBindingModel,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) throws ObjectNotFoundException {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("addFuelBindingModel", addFuelBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addFuelBindingModel", result);
            Long vehicleId = addFuelBindingModel.getVehicleId();
            return "redirect:/addFuel/{vehicleId}";
        }

        this.statisticsService.addFuel(this.modelMapper.map(addFuelBindingModel, AddFuelServiceModel.class));

        return "redirect:/vehicle/{id}/fueling";
    }

    @GetMapping("/addExpenses/{id}")
    public String addExpenses(@PathVariable Long id){
        return "addExpenses";
    }

    @PostMapping("/addExpenses/{id}")
    public String addFuelConfirm(@PathVariable Long id,
                                 @Valid AddExpensesBindingModel addExpensesBindingModel,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes){
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("addExpensesBindingModel", addExpensesBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addExpensesBindingModel", result);
            Long vehicleId = addExpensesBindingModel.getVehicleId();
            return "redirect:/addExpenses/{vehicleId}";
        }

        this.statisticsExpensesService.addExpenses(this.modelMapper.map(addExpensesBindingModel, AddExpensesServiceModel.class));

        return "redirect:/vehicle/{id}/expenses";
    }
}