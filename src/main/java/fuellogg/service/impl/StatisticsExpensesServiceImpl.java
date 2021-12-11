package fuellogg.service.impl;

import fuellogg.model.entity.StatisticExpenses;
import fuellogg.model.entity.StatisticFueling;
import fuellogg.model.exception.ObjectNotFoundException;
import fuellogg.model.service.AddExpensesServiceModel;
import fuellogg.model.view.DetailsViewOnExpenses;
import fuellogg.model.view.DetailsViewOnFueling;
import fuellogg.model.view.ExpensesStatisticViewModel;
import fuellogg.repository.StatisticsExpensesRepository;
import fuellogg.service.StatisticsExpensesService;
import fuellogg.service.VehicleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
        StatisticExpenses stat = this.modelMapper.map(addExpensesServiceModel, StatisticExpenses.class);
        stat.setCreated(Instant.now());
        this.statisticsExpensesRepository.save(stat);
    }

    @Override
    public List<ExpensesStatisticViewModel> getAllStatisticsByVehicleId(Long vehicleId) {
        return this.statisticsExpensesRepository.findAllByVehicle_IdOrderByDateDesc(vehicleId)
                .orElseThrow(() -> new ObjectNotFoundException("Statistics not found!"))
                .stream()
                .map(statistic -> this.modelMapper.map(statistic, ExpensesStatisticViewModel.class))
                .collect(Collectors.toList());

    }

    @Override
    public DetailsViewOnExpenses getCurrentStatisticView(Long id) {
        StatisticExpenses stat = this.statisticsExpensesRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Statistic not found"));
        DetailsViewOnExpenses dvof = this.modelMapper.map(stat, DetailsViewOnExpenses.class);
        dvof.setType(stat.getType().name());
        dvof.setVehicleId(stat.getVehicle().getId());
        return dvof;

    }
}
