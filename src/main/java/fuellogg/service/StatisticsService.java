package fuellogg.service;

import fuellogg.model.binding.AddFuelBindingModel;
import fuellogg.model.service.AddExpensesServiceModel;
import fuellogg.model.service.AddFuelServiceModel;
import fuellogg.model.view.FuelStatisticViewModel;
import javassist.tools.rmi.ObjectNotFoundException;

import java.util.List;

public interface StatisticsService {

    void addFuel(AddFuelServiceModel addFuelServiceModel) throws ObjectNotFoundException;

    List<FuelStatisticViewModel> getAllStatisticsByVehicleId(Long vehicleId);

}

