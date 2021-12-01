package fuellogg.service;

import fuellogg.model.service.AddExpensesServiceModel;
import fuellogg.model.view.ExpensesStatisticViewModel;

import java.util.List;

public interface StatisticsExpensesService {

    void addExpenses(AddExpensesServiceModel addExpensesServiceModel);

    List<ExpensesStatisticViewModel> getAllStatisticsByVehicleId(Long vehicleId);
}
