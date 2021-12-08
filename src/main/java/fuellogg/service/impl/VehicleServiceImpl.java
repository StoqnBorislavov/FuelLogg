package fuellogg.service.impl;

import fuellogg.model.binding.VehicleAddBindingModel;
import fuellogg.model.entity.*;
import fuellogg.model.service.VehicleAddServiceModel;
import fuellogg.model.view.VehicleViewModel;
import fuellogg.repository.ModelRepository;
import fuellogg.repository.PictureRepository;
import fuellogg.repository.StatisticsFuelingRepository;
import fuellogg.repository.VehicleRepository;
import fuellogg.service.*;
import javassist.tools.rmi.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ModelService modelService;
    private final ModelRepository modelRepository;
    private final CloudinaryService cloudinaryService;
    private final VehicleRepository vehicleRepository;
    private final PictureRepository pictureRepository;
    // TODO: not to use statistic repo
    private final StatisticsFuelingRepository statisticsFuelingRepository;

    @Autowired
    public VehicleServiceImpl(UserService userService, ModelMapper modelMapper, ModelService modelService, ModelRepository modelRepository, CloudinaryService cloudinaryService, VehicleRepository vehicleRepository, PictureRepository pictureRepository, StatisticsFuelingRepository statisticsFuelingRepository) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.modelService = modelService;
        this.modelRepository = modelRepository;
        this.cloudinaryService = cloudinaryService;
        this.vehicleRepository = vehicleRepository;
        this.pictureRepository = pictureRepository;
        this.statisticsFuelingRepository = statisticsFuelingRepository;
    }


    @Override
    @Transactional
    public void addVehicle(VehicleAddBindingModel vehicleAddBindingModel, String ownerId) throws IOException {
        User user = userService.findByUsername(ownerId);
        VehicleAddServiceModel vehicleAddServiceModel = modelMapper.map(vehicleAddBindingModel,
                VehicleAddServiceModel.class);
        Vehicle vehicle = modelMapper.map(vehicleAddServiceModel, Vehicle.class);
        vehicle.setCreated(Instant.now());
        vehicle.setOwner(user);
        Model model = this.modelRepository.findByName(vehicleAddBindingModel.getModelName()).orElseThrow(()-> new NoSuchElementException());
        Brand brand = model.getBrand();
        vehicle.setBrand(brand);
        Picture picture = this.createPicture(vehicleAddBindingModel.getPicture());
        vehicle.setPicture(this.pictureRepository.save(picture));

        this.vehicleRepository.save(vehicle);
    }

    @Override
    public List<VehicleViewModel> findMyVehicles(String username) {

        List<Vehicle> entities = this.vehicleRepository.findByOwner_Username(username).orElse(new ArrayList<>());
        return entities.stream().map(this::customMap).collect(Collectors.toList());
    }

    @Override
    public Integer lastOdometer(Long id) {
       return this.vehicleRepository.findById(id).map(Vehicle::getOdometer).orElseThrow(()-> new fuellogg.model.exception.ObjectNotFoundException("Vehicle not found!"));
    }

    @Override
    public void updateVehicle(Long vehicleId, Integer odometer){
        Vehicle vehicle = this.vehicleRepository.findById(vehicleId).orElseThrow(()-> new fuellogg.model.exception.ObjectNotFoundException("Vehicle not found!"));
        vehicle.setOdometer(odometer);
        this.vehicleRepository.save(vehicle);
    }

    @Override
    public String findVehicleById(Long id) {
        Vehicle vehicle = this.vehicleRepository.findById(id).orElseThrow(()-> new fuellogg.model.exception.ObjectNotFoundException("Vehicle not found!"));
        return vehicle.getBrand().getName() + " " + vehicle.getName();
    }

    @Override
    public boolean isOwner(Long id, String username) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
        if(vehicle.isEmpty()){
            return false;
        }
        return vehicle.get().getOwner().getUsername().equals(username);
    }

    @Override
    public VehicleViewModel findVehicleViewModelById(Long id) throws ObjectNotFoundException {
        VehicleViewModel vehicleViewModel = modelMapper.map(this.vehicleRepository.findById(id).orElseThrow(()->new ObjectNotFoundException("Vehicle not found!")), VehicleViewModel.class);
        return vehicleViewModel;
    }


    private VehicleViewModel customMap(Vehicle vehicle){
        return new VehicleViewModel()
                .setId(vehicle.getId())
                .setUrl(vehicle.getPicture().getUrl())
                .setOdometer(calculateTheMileage(vehicle))
                .setAverageConsumption(calculateAverageConsumption(vehicle))
                .setBrand(vehicle.getBrand().getName())
                .setName(vehicle.getName());
    }

    private Integer calculateTheMileage(Vehicle vehicle) {
        Integer latestFueling = this.statisticsFuelingRepository.findTopByVehicle_IdOrderByCreatedAsc(vehicle.getId()).map(StatisticFueling::getOdometer).orElse(0);
        Integer mostRecentFueling = this.statisticsFuelingRepository.findTopByVehicle_IdOrderByCreatedDesc(vehicle.getId()).map(StatisticFueling::getOdometer).orElse(0);
        if(latestFueling == 0 && mostRecentFueling == 0){
            return 0;
        } else if(latestFueling.equals(mostRecentFueling)) {
            return statisticsFuelingRepository.findTopByVehicle_IdOrderByCreatedDesc(vehicle.getId()).map(StatisticFueling::getTrip).orElse(0);
        }
        return mostRecentFueling + statisticsFuelingRepository.findTopByVehicle_IdOrderByCreatedDesc(vehicle.getId()).map(StatisticFueling::getTrip).orElse(0) - latestFueling ;
    }

    private BigDecimal calculateAverageConsumption(Vehicle vehicle) {
        Integer latestFueling = this.statisticsFuelingRepository.findTopByVehicle_IdOrderByCreatedAsc(vehicle.getId()).map(StatisticFueling::getOdometer).orElse(null);
        Integer mostRecentFueling = this.statisticsFuelingRepository.findTopByVehicle_IdOrderByCreatedDesc(vehicle.getId()).map(StatisticFueling::getOdometer).orElse(null);
        List<StatisticFueling> statisticFuelings = this.statisticsFuelingRepository.findAllByVehicle_IdOrderByCreatedDesc(vehicle.getId()).orElseThrow(() -> new fuellogg.model.exception.ObjectNotFoundException("Statistics not found!"));
        BigDecimal neededFuel = new BigDecimal(0);
        BigDecimal averageConsumption = new BigDecimal(0);
        if(latestFueling == null && mostRecentFueling == null){
            return new BigDecimal(0);
        } else if(latestFueling.equals(mostRecentFueling)){
            return this.statisticsFuelingRepository.findTopByVehicle_IdOrderByCreatedDesc(vehicle.getId()).get().getFuelConsumption();
        } else if(latestFueling != null && mostRecentFueling != null) {
            for (StatisticFueling statisticFueling : statisticFuelings) {
                neededFuel = neededFuel.add(statisticFueling.getQuantity());
            }
            averageConsumption = averageConsumption.add(neededFuel).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(mostRecentFueling - latestFueling), RoundingMode.CEILING);
            return averageConsumption;
        }
        return new BigDecimal(0);
    }

    private Picture createPicture(MultipartFile file) throws IOException {
        final CloudinaryImage uploaded = this.cloudinaryService.upload(file);

        return new Picture().
                setPublicId(uploaded.getPublicId()).
                setUrl(uploaded.getUrl());
    }
}
