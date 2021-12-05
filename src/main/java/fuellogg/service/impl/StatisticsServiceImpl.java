package fuellogg.service.impl;

import fuellogg.model.entity.Statistic;
import fuellogg.model.exception.ObjectNotFoundException;
import fuellogg.model.service.AddExpensesServiceModel;
import fuellogg.model.service.AddFuelServiceModel;
import fuellogg.model.view.FuelStatisticViewModel;
import fuellogg.repository.StatisticsRepository;
import fuellogg.service.StatisticsService;
import fuellogg.service.VehicleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsRepository statisticsRepository;
    private final ModelMapper modelMapper;
    private final VehicleService vehicleService;

    public StatisticsServiceImpl(StatisticsRepository statisticsRepository, ModelMapper modelMapper, VehicleService vehicleService) {
        this.statisticsRepository = statisticsRepository;
        this.modelMapper = modelMapper;
        this.vehicleService = vehicleService;
    }


    @Override
    @Transactional
    public void addFuel(AddFuelServiceModel addFuelServiceModel) throws javassist.tools.rmi.ObjectNotFoundException {
        Statistic newStatistic = new Statistic();
        Statistic lastStatistic = this.statisticsRepository
                .findTopByVehicle_IdOrderByDateDesc(addFuelServiceModel.getVehicleId()).orElse(null);

        modelMapper.map(addFuelServiceModel, newStatistic);

        if(lastStatistic == null){
            newStatistic.setFuelConsumption(BigDecimal.valueOf(0));
            newStatistic.setFuelConsumption(BigDecimal.valueOf(0));
            newStatistic.setTrip(0);
        } else{
            newStatistic.setTrip(addFuelServiceModel.getOdometer() - lastStatistic.getOdometer());
            newStatistic.setFuelConsumption(checkFuelConsumption(addFuelServiceModel, lastStatistic));
        }
        newStatistic.setCreated(Instant.now());
        newStatistic.setId(null);
        this.statisticsRepository.save(newStatistic);
        this.vehicleService.updateVehicle(addFuelServiceModel.getVehicleId(), addFuelServiceModel.getOdometer());

    }

    @Override
    public List<FuelStatisticViewModel> getAllStatisticsByVehicleId(Long id) {
        return this.statisticsRepository.findAllByVehicle_IdOrderByDateDesc(id)
                .orElseThrow(()-> new ObjectNotFoundException("Statistics not found!"))
                .stream()
                .map(statistic -> modelMapper.map(statistic, FuelStatisticViewModel.class))
                .collect(Collectors.toList());
    }


    private BigDecimal checkFuelConsumption(AddFuelServiceModel addFuelServiceModel, Statistic lastStatistic) {
        BigDecimal consumption = new BigDecimal(0);
        consumption = consumption.add(addFuelServiceModel.getQuantity()).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(addFuelServiceModel.getOdometer() - lastStatistic.getOdometer()), RoundingMode.CEILING);
        return consumption;
    }
}
