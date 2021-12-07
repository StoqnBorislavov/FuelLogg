package fuellogg.service;

import fuellogg.model.binding.VehicleAddBindingModel;
import fuellogg.model.view.VehicleViewModel;
import javassist.tools.rmi.ObjectNotFoundException;

import java.io.IOException;
import java.util.List;

public interface VehicleService {
    void addVehicle(VehicleAddBindingModel vehicleAddBindingModel, String ownerId) throws IOException;

    List<VehicleViewModel> findMyVehicles(String userIdentifier);

    Integer lastOdometer(Long id);

    void updateVehicle(Long vehicleId, Integer odometer) throws ObjectNotFoundException;

    String findVehicleById(Long id);

    boolean isOwner(Long id, String username);

    VehicleViewModel findVehicleViewModelById(Long id) throws ObjectNotFoundException;

}
