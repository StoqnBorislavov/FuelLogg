package fuellogg.model.entity;

import fuellogg.model.enums.RouteEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "statistics")
public class Statistic extends BaseEntity{

    private LocalDate date;
    private Integer odometer;
    private Integer trip;
    private BigDecimal fuelConsumption;
    private BigDecimal quantity;
    private BigDecimal price;
    private RouteEnum route;
    private String description;
    private Vehicle vehicle;

    public Statistic() {
    }
    @Column
    public Integer getTrip() {
        return trip;
    }

    public Statistic setTrip(Integer trip) {
        this.trip = trip;
        return this;
    }
    @Column
    public BigDecimal getFuelConsumption() {
        return fuelConsumption;
    }

    public Statistic setFuelConsumption(BigDecimal fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
        return this;
    }

    @Column(nullable = false)
    public LocalDate getDate() {
        return date;
    }

    public Statistic setDate(LocalDate date) {
        this.date = date;
        return this;
    }
    @Column(nullable = false)
    public Integer getOdometer() {
        return odometer;
    }

    public Statistic setOdometer(Integer odometer) {
        this.odometer = odometer;
        return this;
    }
    @Column(nullable = false)
    public BigDecimal getQuantity() {
        return quantity;
    }

    public Statistic setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }
    @Column(nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public Statistic setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public RouteEnum getRoute() {
        return route;
    }

    public Statistic setRoute(RouteEnum route) {
        this.route = route;
        return this;
    }
    @Column(columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public Statistic setDescription(String description) {
        this.description = description;
        return this;
    }
    @ManyToOne
    public Vehicle getVehicle() {
        return vehicle;
    }

    public Statistic setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        return this;
    }
}
