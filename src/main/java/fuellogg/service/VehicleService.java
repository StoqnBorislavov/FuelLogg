package fuellogg.service;

import fuellogg.model.binding.VehicleAddBindingModel;
import fuellogg.model.service.VehicleAddServiceModel;
import fuellogg.model.view.VehicleViewModel;

import java.io.IOException;
import java.util.List;

public interface VehicleService {
    void addVehicle(VehicleAddBindingModel vehicleAddBindingModel, String ownerId) throws IOException;

    List<VehicleViewModel> findMyVehicles(String userIdentifier);

    Integer lastOdometer(Long id);
}
