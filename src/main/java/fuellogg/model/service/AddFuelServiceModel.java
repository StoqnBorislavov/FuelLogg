package fuellogg.model.service;

import fuellogg.model.enums.RouteEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AddFuelServiceModel {
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

    public AddFuelServiceModel() {
    }

    public LocalDate getDate() {
        return date;
    }

    public AddFuelServiceModel setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public RouteEnum getRoute() {
        return route;
    }

    public AddFuelServiceModel setRoute(RouteEnum route) {
        this.route = route;
        return this;
    }

    public Long getId() {
        return id;
    }

    public AddFuelServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public AddFuelServiceModel setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
        return this;
    }

    public Integer getOdometer() {
        return odometer;
    }

    public AddFuelServiceModel setOdometer(Integer odometer) {
        this.odometer = odometer;
        return this;
    }

    public Integer getTripOdometer() {
        return tripOdometer;
    }

    public AddFuelServiceModel setTripOdometer(Integer tripOdometer) {
        this.tripOdometer = tripOdometer;
        return this;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public AddFuelServiceModel setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getFuelSort() {
        return fuelSort;
    }

    public AddFuelServiceModel setFuelSort(String fuelSort) {
        this.fuelSort = fuelSort;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public AddFuelServiceModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AddFuelServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }
}
