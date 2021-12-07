package fuellogg.model.view;

import fuellogg.model.entity.Vehicle;
import fuellogg.model.enums.RouteEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DetailsViewOnFueling {

    private LocalDate date;
    private Integer odometer;
    private Integer trip;
    private BigDecimal fuelConsumption;
    private String fuelSort;
    private BigDecimal quantity;
    private BigDecimal price;
    private String route;
    private String description;
    private String vehicle;

    public String getFuelSort() {
        return fuelSort;
    }

    public DetailsViewOnFueling setFuelSort(String fuelSort) {
        this.fuelSort = fuelSort;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public DetailsViewOnFueling setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public Integer getOdometer() {
        return odometer;
    }

    public DetailsViewOnFueling setOdometer(Integer odometer) {
        this.odometer = odometer;
        return this;
    }

    public Integer getTrip() {
        return trip;
    }

    public DetailsViewOnFueling setTrip(Integer trip) {
        this.trip = trip;
        return this;
    }

    public BigDecimal getFuelConsumption() {
        return fuelConsumption;
    }

    public DetailsViewOnFueling setFuelConsumption(BigDecimal fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
        return this;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public DetailsViewOnFueling setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public DetailsViewOnFueling setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getRoute() {
        return route;
    }

    public DetailsViewOnFueling setRoute(String route) {
        this.route = route;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public DetailsViewOnFueling setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getVehicle() {
        return vehicle;
    }

    public DetailsViewOnFueling setVehicle(String vehicle) {
        this.vehicle = vehicle;
        return this;
    }
}
