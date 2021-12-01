package fuellogg.service.impl;

import fuellogg.model.entity.StatisticExpenses;
import fuellogg.model.service.AddExpensesServiceModel;
import fuellogg.model.view.ExpensesStatisticViewModel;
import fuellogg.model.view.FuelStatisticViewModel;
import fuellogg.repository.StatisticsExpensesRepository;
import fuellogg.service.StatisticsExpensesService;
import fuellogg.service.VehicleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticsExpensesServiceImpl implements StatisticsExpensesService {


    private final StatisticsExpensesRepository statisticsExpensesRepository;
    private final ModelMapper modelMapper;
    private final VehicleService vehicleService;

    public StatisticsExpensesServiceImpl(StatisticsExpensesRepository statisticsExpensesRepository, ModelMapper modelMapper, VehicleService vehicleService) {
        this.statisticsExpensesRepository = statisticsExpensesRepository;
        this.modelMapper = modelMapper;
        this.vehicleService = vehicleService;
    }


    @Override
    public void addExpenses(AddExpensesServiceModel addExpensesServiceModel) {
        this.statisticsExpensesRepository.save(this.modelMapper.map(addExpensesServiceModel, StatisticExpenses.class));
    }

    @Override
    public List<ExpensesStatisticViewModel> getAllStatisticsByVehicleId(Long vehicleId) {
        return this.statisticsExpensesRepository.findAllByVehicle_IdOrderByDateDesc(vehicleId)
                .orElseThrow(UnsupportedOperationException::new)
                .stream()
                .map(statistic -> modelMapper.map(statistic, ExpensesStatisticViewModel.class))
                .collect(Collectors.toList());

    }
}
