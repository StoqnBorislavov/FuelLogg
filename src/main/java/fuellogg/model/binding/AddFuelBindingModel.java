package fuellogg.model.binding;

import fuellogg.model.enums.RouteEnum;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public class AddFuelBindingModel {

    private Long id;
    private Long vehicleId;
    private LocalDate date;
    private Integer odometer;
    private Integer tripOdometer;
    private BigDecimal quantity;
    private String fuelSort;
    private RouteEnum route;
    private BigDecimal price;
    private String description;

    public AddFuelBindingModel() {
    }

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getDate() {
        return date;
    }

    public AddFuelBindingModel setDate(LocalDate date) {
        this.date = date;
        return this;
    }
    @NotNull
    public RouteEnum getRoute() {
        return route;
    }

    public AddFuelBindingModel setRoute(RouteEnum route) {
        this.route = route;
        return this;
    }
    @NotNull
    public Long getVehicleId() {
        return vehicleId;
    }

    public AddFuelBindingModel setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
        return this;
    }

    @NotNull
    public Integer getOdometer() {
        return odometer;
    }

    public AddFuelBindingModel setOdometer(Integer odometer) {
        this.odometer = odometer;
        return this;
    }

    @Nullable
    public Integer getTripOdometer() {
        return tripOdometer;
    }

    public AddFuelBindingModel setTripOdometer(Integer tripOdometer) {
        this.tripOdometer = tripOdometer;
        return this;
    }
    @NotNull
    public BigDecimal getQuantity() {
        return quantity;
    }

    public AddFuelBindingModel setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }
    @NotNull
    public String getFuelSort() {
        return fuelSort;
    }

    public AddFuelBindingModel setFuelSort(String fuelSort) {
        this.fuelSort = fuelSort;
        return this;
    }
    @NotNull
    @Positive
    public BigDecimal getPrice() {
        return price;
    }

    public AddFuelBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
    @Nullable
    public String getDescription() {
        return description;
    }

    public AddFuelBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }
}
