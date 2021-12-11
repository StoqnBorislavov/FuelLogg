package fuellogg.service.impl;

import fuellogg.model.entity.StatisticFueling;
import fuellogg.model.entity.Vehicle;
import fuellogg.model.exception.ObjectNotFoundException;
import fuellogg.model.service.AddFuelServiceModel;
import fuellogg.model.view.DetailsViewOnFueling;
import fuellogg.model.view.FuelStatisticViewModel;
import fuellogg.repository.StatisticsFuelingRepository;
import fuellogg.repository.VehicleRepository;
import fuellogg.service.StatisticsFuelingService;
import fuellogg.service.VehicleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StatisticsFuelingServiceImpl implements StatisticsFuelingService {

    private final StatisticsFuelingRepository statisticsFuelingRepository;
    private final ModelMapper modelMapper;
    private final VehicleService vehicleService;
    private final VehicleRepository vehicleRepository;

    @Autowired
    public StatisticsFuelingServiceImpl(StatisticsFuelingRepository statisticsFuelingRepository, ModelMapper modelMapper, VehicleService vehicleService, VehicleRepository vehicleRepository) {
        this.statisticsFuelingRepository = statisticsFuelingRepository;
        this.modelMapper = modelMapper;
        this.vehicleService = vehicleService;
        this.vehicleRepository = vehicleRepository;
    }


    @Override
    @Transactional
    public void addFuel(AddFuelServiceModel addFuelServiceModel) throws javassist.tools.rmi.ObjectNotFoundException {
        StatisticFueling newStatisticFueling = new StatisticFueling();
        StatisticFueling lastStatisticFueling = this.statisticsFuelingRepository
                .findTopByVehicle_IdOrderByCreatedDesc(addFuelServiceModel.getVehicleId()).orElse(null);

        this.modelMapper.map(addFuelServiceModel, newStatisticFueling);
        if(lastStatisticFueling == null){
            newStatisticFueling.setTrip(addFuelServiceModel.getOdometer() - this.vehicleService.findVehicleViewModelById(addFuelServiceModel.getVehicleId()).getOdometer());
            newStatisticFueling.setFuelConsumption(checkFuelConsumption(addFuelServiceModel, new StatisticFueling().setOdometer(this.vehicleService.findVehicleViewModelById(addFuelServiceModel.getVehicleId()).getOdometer())));
        } else{
            newStatisticFueling.setTrip(addFuelServiceModel.getOdometer() - lastStatisticFueling.getOdometer());
            newStatisticFueling.setFuelConsumption(checkFuelConsumption(addFuelServiceModel, lastStatisticFueling));
        }
        newStatisticFueling.setCreated(Instant.now());
        newStatisticFueling.setId(null);
        this.statisticsFuelingRepository.save(newStatisticFueling);
        this.vehicleService.updateVehicle(addFuelServiceModel.getVehicleId(), addFuelServiceModel.getOdometer());

    }

    @Override
    public List<FuelStatisticViewModel> getAllStatisticsByVehicleId(Long id) {
        return this.statisticsFuelingRepository.findAllByVehicle_IdOrderByCreatedDesc(id)
                .orElseThrow(()-> new ObjectNotFoundException("Statistics not found!"))
                .stream()
                .map(statistic -> modelMapper.map(statistic, FuelStatisticViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean canUseAddFunction(Long id, String username) {
        Optional<Vehicle> vehicle = this.vehicleRepository.findById(id);
        if(vehicle.isEmpty()){
            return false;
        }

        return vehicle.get().getOwner().getUsername().equals(username);
    }

    @Override
    public DetailsViewOnFueling getCurrentStatisticView(Long id) {
        StatisticFueling stat = this.statisticsFuelingRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Statistic not found"));
        DetailsViewOnFueling dvof = this.modelMapper.map(stat, DetailsViewOnFueling.class);
        dvof.setFuelSort(stat.getVehicle().getEngine().name());
        dvof.setVehicleId(stat.getVehicle().getId());
        return dvof;
    }


    private BigDecimal checkFuelConsumption(AddFuelServiceModel addFuelServiceModel, StatisticFueling lastStatisticFueling) {
        BigDecimal consumption = new BigDecimal(0);
        consumption = consumption.add(addFuelServiceModel.getQuantity()).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(addFuelServiceModel.getOdometer() - lastStatisticFueling.getOdometer()), RoundingMode.CEILING);
        return consumption;
    }
}
