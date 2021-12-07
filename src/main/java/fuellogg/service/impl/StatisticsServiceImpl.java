package fuellogg.service.impl;

import fuellogg.model.entity.Statistic;
import fuellogg.model.entity.Vehicle;
import fuellogg.model.exception.ObjectNotFoundException;
import fuellogg.model.service.AddFuelServiceModel;
import fuellogg.model.view.DetailsViewOnFueling;
import fuellogg.model.view.FuelStatisticViewModel;
import fuellogg.repository.StatisticsRepository;
import fuellogg.repository.VehicleRepository;
import fuellogg.service.StatisticsService;
import fuellogg.service.VehicleService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsRepository statisticsRepository;
    private final ModelMapper modelMapper;
    private final VehicleService vehicleService;
    private final VehicleRepository vehicleRepository;

    public StatisticsServiceImpl(StatisticsRepository statisticsRepository, ModelMapper modelMapper, VehicleService vehicleService, VehicleRepository vehicleRepository) {
        this.statisticsRepository = statisticsRepository;
        this.modelMapper = modelMapper;
        this.vehicleService = vehicleService;
        this.vehicleRepository = vehicleRepository;
    }


    @Override
    @Transactional
    public void addFuel(AddFuelServiceModel addFuelServiceModel) throws javassist.tools.rmi.ObjectNotFoundException {
        Statistic newStatistic = new Statistic();
        Statistic lastStatistic = this.statisticsRepository
                .findTopByVehicle_IdOrderByCreatedDesc(addFuelServiceModel.getVehicleId()).orElse(null);

        modelMapper.map(addFuelServiceModel, newStatistic);

        if(lastStatistic == null){
            newStatistic.setTrip(addFuelServiceModel.getOdometer() - this.vehicleService.findVehicleViewModelById(addFuelServiceModel.getVehicleId()).getOdometer());
            newStatistic.setFuelConsumption(checkFuelConsumption(addFuelServiceModel, new Statistic().setOdometer(this.vehicleService.findVehicleViewModelById(addFuelServiceModel.getVehicleId()).getOdometer())));
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
        return this.statisticsRepository.findAllByVehicle_IdOrderByCreatedDesc(id)
                .orElseThrow(()-> new ObjectNotFoundException("Statistics not found!"))
                .stream()
                .map(statistic -> modelMapper.map(statistic, FuelStatisticViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean canUseAddFunction(Long id, String username) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
        if(vehicle.isEmpty()){
            return false;
        }

        return vehicle.get().getOwner().getUsername().equals(username);
    }

    @Override
    public DetailsViewOnFueling getCurrentStatisticView(Long id) {
        Statistic stat = this.statisticsRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Statistic not found"));
        DetailsViewOnFueling dvof = modelMapper.map(stat, DetailsViewOnFueling.class);
        dvof.setFuelSort(stat.getVehicle().getEngine().name());
        return dvof;
    }


    private BigDecimal checkFuelConsumption(AddFuelServiceModel addFuelServiceModel, Statistic lastStatistic) {
        BigDecimal consumption = new BigDecimal(0);
        consumption = consumption.add(addFuelServiceModel.getQuantity()).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(addFuelServiceModel.getOdometer() - lastStatistic.getOdometer()), RoundingMode.CEILING);
        return consumption;
    }
}
