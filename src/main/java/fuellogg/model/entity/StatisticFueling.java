package fuellogg.model.entity;

import fuellogg.model.enums.RouteEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "statistics_fueling")
public class StatisticFueling extends BaseEntity{

    private LocalDate date;
    private Integer odometer;
    private Integer trip;
    private BigDecimal fuelConsumption;
    private BigDecimal quantity;
    private BigDecimal price;
    private RouteEnum route;
    private String description;
    private Vehicle vehicle;

    public StatisticFueling() {
    }
    @Column
    public Integer getTrip() {
        return trip;
    }

    public StatisticFueling setTrip(Integer trip) {
        this.trip = trip;
        return this;
    }
    @Column
    public BigDecimal getFuelConsumption() {
        return fuelConsumption;
    }

    public StatisticFueling setFuelConsumption(BigDecimal fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
        return this;
    }

    @Column(nullable = false)
    public LocalDate getDate() {
        return date;
    }

    public StatisticFueling setDate(LocalDate date) {
        this.date = date;
        return this;
    }
    @Column(nullable = false)
    public Integer getOdometer() {
        return odometer;
    }

    public StatisticFueling setOdometer(Integer odometer) {
        this.odometer = odometer;
        return this;
    }
    @Column(nullable = false)
    public BigDecimal getQuantity() {
        return quantity;
    }

    public StatisticFueling setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }
    @Column(nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public StatisticFueling setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public RouteEnum getRoute() {
        return route;
    }

    public StatisticFueling setRoute(RouteEnum route) {
        this.route = route;
        return this;
    }
    @Lob
    public String getDescription() {
        return description;
    }

    public StatisticFueling setDescription(String description) {
        this.description = description;
        return this;
    }
    @ManyToOne
    public Vehicle getVehicle() {
        return vehicle;
    }

    public StatisticFueling setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        return this;
    }
}
